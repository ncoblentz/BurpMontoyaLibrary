package com.nickcoblentz.montoya

import burp.api.montoya.http.message.params.ParsedHttpParameter
import burp.api.montoya.http.message.requests.HttpRequest


public fun HttpRequest.withUpdatedContentLength(addContentLengthHeaderIfNotPresent : Boolean = false) : HttpRequest {
    if(this.hasHeader("Content-Length"))
        return this.withUpdatedHeader("Content-Length",this.body().length().toString())
    else if(addContentLengthHeaderIfNotPresent)
        return this.withAddedHeader("Content-Length",this.body().length().toString())
    return this
}

enum class PayloadUpdateMode {
    REPLACE,
    PREPEND,
    INSERT_MIDDLE,
    APPEND
}

public fun HttpRequest.withUpdatedParsedParameterValue(parsedParameter : ParsedHttpParameter, encodedValue : String, payloadUpdateMode : PayloadUpdateMode = PayloadUpdateMode.REPLACE) : HttpRequest
{
    var updatedParsedParam = this.parameters().find { it.name()==parsedParameter.name() && it.type() == parsedParameter.type() && it.value()==parsedParameter.value() }

    if(updatedParsedParam!=null) {
        val requestAsString = this.toString()

        when (payloadUpdateMode) {
            PayloadUpdateMode.PREPEND -> {
                val part1 = requestAsString.substring(0, updatedParsedParam.valueOffsets().startIndexInclusive())
                val part2 = requestAsString.substring(updatedParsedParam.valueOffsets().startIndexInclusive() + 1)
                return HttpRequest.httpRequest(this.httpService(), part1 + encodedValue + part2)
            }

            PayloadUpdateMode.INSERT_MIDDLE -> {
                val middleIndexDiff =
                    (updatedParsedParam.valueOffsets().endIndexExclusive() - updatedParsedParam.valueOffsets()
                        .startIndexInclusive()) / 2
                if (middleIndexDiff > 1) {
                    val part1 = requestAsString.substring(
                        0,
                        updatedParsedParam.valueOffsets().startIndexInclusive() + middleIndexDiff
                    )
                    val part2 = requestAsString.substring(
                        updatedParsedParam.valueOffsets().startIndexInclusive() + middleIndexDiff + 1
                    )
                    return HttpRequest.httpRequest(this.httpService(), part1 + encodedValue + part2)
                }
                val part1 = requestAsString.substring(0, updatedParsedParam.valueOffsets().startIndexInclusive())
                val part2 = requestAsString.substring(updatedParsedParam.valueOffsets().startIndexInclusive() + 1)
                return HttpRequest.httpRequest(this.httpService(), part1 + encodedValue + part2)
            }

            PayloadUpdateMode.APPEND -> {
                val part1 = requestAsString.substring(0, updatedParsedParam.valueOffsets().endIndexExclusive())
                val part2 = requestAsString.substring(updatedParsedParam.valueOffsets().endIndexExclusive())
                return HttpRequest.httpRequest(this.httpService(), part1 + encodedValue + part2)
            }

            else -> {
                val part1 = requestAsString.substring(0, updatedParsedParam.valueOffsets().startIndexInclusive())
                val part2 = requestAsString.substring(updatedParsedParam.valueOffsets().endIndexExclusive())
                return HttpRequest.httpRequest(this.httpService(), part1 + encodedValue + part2)
            }
        }
    }
    return this
}