package com.nickcoblentz.montoya;

import burp.api.montoya.MontoyaApi;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MontoyaLogger {

    private final MontoyaApi _api;
    private String _LogLevel;
    public final static String DebugLogLevel="DEBUG";
    public final static String WarnLogLevel="WARN";
    public final static String ErrorLogLevel="ERROR";
    public final static List<String> LogLevelOptions = List.of(DebugLogLevel,WarnLogLevel,ErrorLogLevel);
    private List<String> _selectedLogLevels = new LinkedList<String>();


    public MontoyaLogger(MontoyaApi api) {
        this._api = api;
        setDefaultLogLevel();
        //_api.logging().logToOutput("got here 1");

    }

    public MontoyaLogger(MontoyaApi api, String logLevel) {
        this._api = api;
        setLogLevel(logLevel);
        //.logging().logToOutput("got here 2: "+logLevel);
    }

    public void setDefaultLogLevel()
    {
        //_api.logging().logToOutput("got here 3: ");
        setLogLevel(this.ErrorLogLevel);
    }

    public void setLogLevel(String logLevel)
    {
//        _api.logging().logToOutput("got here 4: "+logLevel);
        _selectedLogLevels.clear();
        _selectedLogLevels.add(this.ErrorLogLevel);

        this._LogLevel = switch(logLevel) {
        case DebugLogLevel:
//            _api.logging().logToOutput("got here 4: debug");
            _selectedLogLevels.add(this.DebugLogLevel);
        case WarnLogLevel:
//            _api.logging().logToOutput("got here 4: warn");
            _selectedLogLevels.add(this.WarnLogLevel);
            yield logLevel;
        default:
//            _api.logging().logToOutput("got here 4: default");
            yield this.ErrorLogLevel;
        };

//        _api.logging().logToOutput("got here 4.5: "+_LogLevel);
    }

    public String getLogLevel()
    {
        return _LogLevel;
    }

    public String getClassAndMethodName()
    {
        StackWalker walker = StackWalker.getInstance();
        AtomicReference<String> className= new AtomicReference<>("");
        AtomicReference<String> methodName= new AtomicReference<>("");
        StackWalker.StackFrame sf = walker.walk((s) ->{
            return s.skip(3).findFirst().get();
        });
        className.set(sf.getClassName());
        methodName.set(sf.getMethodName());
        return className.get()+"::"+methodName.get();
    }
    public void debugLog(String message)
    {
        log(DebugLogLevel,message);
    }
    public void debugLog(String className, String message)
    {
        log(DebugLogLevel,className, message);
    }

    public void warnLog(String message)
    {
        log(WarnLogLevel,message);
    }
    public void warnLog(String className, String message)
    {
        log(WarnLogLevel,className,message);
    }
    public void errorLog(String message)
    {
        log(ErrorLogLevel,message);
    }

    public void errorLog(String className, String message)
    {
        log(ErrorLogLevel,className,message);
    }

    public void log(String logLevel,String message)
    {
        log(logLevel,getClassAndMethodName(),message);
    }
    public void log(String logLevel, String className, String message)
    {
        if(!shouldLog(logLevel))
            return;

        StringBuilder logBuilder = new StringBuilder();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        logBuilder.append(String.format("%s\t|%s\t|%s - %s",logLevel,dateFormat.format(date),className,message));

//        _api.logging().logToOutput("got here 5?: "+logLevel);

        if(logLevel.equals(this.ErrorLogLevel))
        {
            _api.logging().logToError(logBuilder.toString());
        }
        else
        {
            _api.logging().logToOutput(logBuilder.toString());
        }
    }

    public boolean shouldLog(String logLevel)
    {
//        _api.logging().logToOutput("got here log?: "+this._selectedLogLevels.contains(logLevel));
        return this._selectedLogLevels.contains(logLevel);
    }
    
}
