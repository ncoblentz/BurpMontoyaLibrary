package com.nickcoblentz.montoya.settings;

import burp.api.montoya.MontoyaApi;
import com.nickcoblentz.montoya.MontoyaLogger;

import java.util.List;

public abstract class GenericExtensionSetting<T> {

    private final T DefaultValue;
    private final String StorageKey;
    private final String Name;

    private final MontoyaApi Api;
    private final ExtensionSettingSaveLocation DefaultSaveLocation;
    private final MontoyaLogger Logger;
    private T CurrentValue;
    public final List<Class> SupportedClasses = List.of(String.class,Integer.class, Long.class, Byte.class, Short.class, Boolean.class);

    public GenericExtensionSetting(MontoyaApi api, String name, String storageKey, T defaultValue, ExtensionSettingSaveLocation defaultSaveLocation) {
        Logger = new MontoyaLogger(api,MontoyaLogger.DebugLogLevel);
        Api=api;
        Name=name;
        StorageKey=storageKey;
        DefaultValue=defaultValue;
        DefaultSaveLocation=defaultSaveLocation;
        load();
    }


    public T getCurrentValue() {
        if(CurrentValue !=null)
        {
            if(CurrentValue instanceof String sCurrentValue)
            {
                if(!sCurrentValue.isEmpty())
                    return CurrentValue;
            }
            else {
                return CurrentValue;
            }
        }

        return DefaultValue;
    }


    public void setCurrentValue(T currentValue) {
        if(currentValue!=null)
            CurrentValue = currentValue;
    }

    public abstract void setCurrentValue(String value);


    public void saveToProject() {
        if(getCurrentValue() instanceof String sValue)
            Api.persistence().extensionData().setString(StorageKey,sValue);
        else if (getCurrentValue() instanceof Boolean bValue)
            Api.persistence().extensionData().setBoolean(StorageKey,bValue.booleanValue());
        else if (getCurrentValue() instanceof Byte bValue)
                Api.persistence().extensionData().setByte(StorageKey,bValue.byteValue());
        else if (getCurrentValue() instanceof Integer iValue)
                Api.persistence().extensionData().setInteger(StorageKey,iValue.intValue());
        else if (getCurrentValue() instanceof Long lValue)
            Api.persistence().extensionData().setLong(StorageKey,lValue.longValue());
        else if (getCurrentValue() instanceof Short sValue)
            Api.persistence().extensionData().setShort(StorageKey,sValue.shortValue());
    }


    public abstract void loadFromProject();/*
    {
        setCurrentValue(Api.persistence().extensionData().getString(StorageKey));
    }*/


    public void saveToPreferences()
    {
        if(getCurrentValue() instanceof String sValue)
            Api.persistence().preferences().setString(StorageKey,sValue);
        else if (getCurrentValue() instanceof Boolean bValue)
            Api.persistence().preferences().setBoolean(StorageKey,bValue.booleanValue());
        else if (getCurrentValue() instanceof Byte bValue)
            Api.persistence().preferences().setByte(StorageKey,bValue.byteValue());
        else if (getCurrentValue() instanceof Integer iValue)
            Api.persistence().preferences().setInteger(StorageKey,iValue.intValue());
        else if (getCurrentValue() instanceof Long lValue)
            Api.persistence().preferences().setLong(StorageKey,lValue.longValue());
        else if (getCurrentValue() instanceof Short sValue)
            Api.persistence().preferences().setShort(StorageKey,sValue.shortValue());
    }


    public abstract void loadFromPreferences();/*
    {
        if(getCurrentValue() instanceof String sValue)
            setCurrentValue((T)Api.persistence().preferences().getString(StorageKey));
        else if (getCurrentValue() instanceof Boolean bValue)
            setCurrentValue((T)Boolean.valueOf((boolean)Api.persistence().preferences().getBoolean(StorageKey)));
        else if (getCurrentValue() instanceof Byte bValue)
            setCurrentValue((T)Api.persistence().preferences().getByte(StorageKey));
        else if (getCurrentValue() instanceof Integer iValue)
            Api.persistence().preferences().setInteger(StorageKey,iValue.intValue());
        else if (getCurrentValue() instanceof Long lValue)
            Api.persistence().preferences().setLong(StorageKey,lValue.longValue());
        else if (getCurrentValue() instanceof Short sValue)
            Api.persistence().preferences().setShort(StorageKey,sValue.shortValue());

        Api.persistence().preferences().getString(StorageKey));
    }*/


    public void save() {
        switch(DefaultSaveLocation) {
            case ExtensionSettingSaveLocation.PREFERENCE -> saveToPreferences();
            case ExtensionSettingSaveLocation.PROJECT -> saveToProject();
            default -> {}
        }
    }


    public void load() {
        switch(DefaultSaveLocation) {
            case ExtensionSettingSaveLocation.PREFERENCE -> loadFromPreferences();
            case ExtensionSettingSaveLocation.PROJECT -> loadFromProject();
            default -> {}
        }
    }


    public String getName() {
        return Name;
    }


    public String getStorageKey() {
        return StorageKey;
    }


    public T reset()
    {
        setCurrentValue(DefaultValue);
        return getCurrentValue();
    }

    public MontoyaApi getApi() {
        return Api;
    }

    public abstract T parseString(String value);
}
