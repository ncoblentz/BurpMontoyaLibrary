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
    public final MontoyaLogger Logger;
    private T CurrentValue;

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


    public abstract void saveToProject();


    public abstract void loadFromProject();/*
    {
        setCurrentValue(Api.persistence().extensionData().getString(StorageKey));
    }*/


    public abstract void saveToPreferences();


    public abstract void loadFromPreferences();


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

    public T getDefaultValue() {
        return DefaultValue;
    }
}
