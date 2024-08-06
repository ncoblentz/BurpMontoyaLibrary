package com.nickcoblentz.montoya

import burp.api.montoya.http.Http
import burp.api.montoya.http.HttpMode
import burp.api.montoya.http.RequestOptions
import burp.api.montoya.http.message.HttpRequestResponse
import burp.api.montoya.http.message.requests.HttpRequest

public fun Http.sendRequestWithUpdatedContentLength(httpRequest : HttpRequest, addContentLengthHeaderIfNotPresent : Boolean = false) : HttpRequestResponse {
    return this.sendRequest(httpRequest.withUpdatedContentLength(addContentLengthHeaderIfNotPresent))
}

public fun Http.sendRequestWithUpdatedContentLength(httpRequest : HttpRequest, httpMode : HttpMode, addContentLengthHeaderIfNotPresent : Boolean = false) : HttpRequestResponse {
    return this.sendRequest(httpRequest.withUpdatedContentLength(addContentLengthHeaderIfNotPresent),httpMode)
}

public fun Http.sendRequestWithUpdatedContentLength(httpRequest : HttpRequest, httpRequestOptions : RequestOptions, addContentLengthHeaderIfNotPresent : Boolean = false) : HttpRequestResponse {
    return this.sendRequest(httpRequest.withUpdatedContentLength(addContentLengthHeaderIfNotPresent),httpRequestOptions)
}

public fun Http.sendRequestWithUpdatedContentLength(httpRequest : HttpRequest, httpMode : HttpMode, connectionId : String, addContentLengthHeaderIfNotPresent : Boolean = false) : HttpRequestResponse {
    return this.sendRequest(httpRequest.withUpdatedContentLength(addContentLengthHeaderIfNotPresent),httpMode, connectionId)
}