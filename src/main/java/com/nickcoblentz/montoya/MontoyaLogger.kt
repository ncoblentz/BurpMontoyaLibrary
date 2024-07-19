package com.nickcoblentz.montoya

import burp.api.montoya.MontoyaApi
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicReference
import java.util.stream.Stream

enum class LogLevel {
    DEBUG,
    WARN,
    ERROR
}

class MontoyaLogger {
    private val _api: MontoyaApi
    private var _currentLogLevel = LogLevel.ERROR

    constructor(api: MontoyaApi) {
        this._api = api

        //_api.logging().logToOutput("got here 1");
    }

    constructor(api: MontoyaApi, logLevel: LogLevel) {
        this._api = api
        _currentLogLevel = logLevel
        //.logging().logToOutput("got here 2: "+logLevel);
    }

    private fun classAndMethodName(): String
    {
        val walker = StackWalker.getInstance()
        val className = AtomicReference("")
        val methodName = AtomicReference("")
        val sf = walker.walk { s: Stream<StackWalker.StackFrame?> ->
            s.skip(
                3
            ).findFirst().get()
        }
        className.set(sf.className)
        methodName.set(sf.methodName)
        return className.get() + "::" + methodName.get()
    }

    fun debugLog(message: String?) {
        log(LogLevel.DEBUG, message)
    }

    fun debugLog(className: String?, message: String?) {
        log(LogLevel.DEBUG, className, message)
    }

    fun warnLog(message: String?) {
        log(LogLevel.WARN, message)
    }

    fun warnLog(className: String?, message: String?) {
        log(LogLevel.WARN, className, message)
    }

    fun errorLog(message: String?) {
        log(LogLevel.ERROR, message)
    }

    fun errorLog(className: String?, message: String?) {
        log(LogLevel.ERROR, className, message)
    }

    fun log(logLevel: LogLevel, message: String?) {
        log(logLevel, classAndMethodName(), message)
    }

    fun log(logLevel: LogLevel, className: String?, message: String?) {
        if (!shouldLog(logLevel)) return

        val logBuilder = StringBuilder()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = Date()
        logBuilder.append(String.format("%s\t|%s\t|%s - %s", logLevel, dateFormat.format(date), className, message))

        //        _api.logging().logToOutput("got here 5?: "+logLevel);
        when(logLevel) {
            LogLevel.ERROR -> _api.logging().logToError(logBuilder.toString())
            else ->_api.logging().logToOutput(logBuilder.toString())
        }
    }

    private fun shouldLog(logLevel: LogLevel): Boolean {
//        _api.logging().logToOutput("got here log?: "+this._selectedLogLevels.contains(logLevel));
        return when(logLevel) {
            _currentLogLevel -> true
            LogLevel.ERROR -> true
            LogLevel.WARN -> _currentLogLevel==LogLevel.DEBUG
            else -> false
        }
    }

}