package com.nickcoblentz.montoya.settings;

import burp.api.montoya.MontoyaApi;
import com.nickcoblentz.montoya.MontoyaLogger;

public class ExtensionSetting implements IExtensionSetting {
    private final String DefaultValue;
    private final String StorageKey;
    private final String Name;
    private final MontoyaApi Api;
    private final ExtensionSettingSaveLocation DefaultSaveLocation;
    private final MontoyaLogger Logger;
    private String CurrentValue;



    public ExtensionSetting(MontoyaApi api, String name, String storageKey, String defaultValue, ExtensionSettingSaveLocation defaultSaveLocation) {
        Logger = new MontoyaLogger(api);
        Api=api;
        Name=name;
        StorageKey=storageKey;
        DefaultValue=defaultValue;
        DefaultSaveLocation=defaultSaveLocation;
        load();

    }

    @Override
    public String getCurrentValue() {
        return CurrentValue==null || CurrentValue.isEmpty() ? DefaultValue : CurrentValue;
    }

    @Override
    public void setCurrentValue(String currentValue) {
        if(currentValue!=null)
            CurrentValue = currentValue;
    }

    @Override
    public void saveToProject()
    {
        Api.persistence().extensionData().setString(StorageKey,getCurrentValue());
    }

    @Override
    public void loadFromProject()
    {
        setCurrentValue(Api.persistence().extensionData().getString(StorageKey));
    }

    @Override
    public void saveToPreferences()
    {
        Api.persistence().preferences().setString(StorageKey,getCurrentValue());
    }

    @Override
    public void loadFromPreferences()
    {
        setCurrentValue(Api.persistence().preferences().getString(StorageKey));
    }

    @Override
    public void save() {
        switch(DefaultSaveLocation) {
            case ExtensionSettingSaveLocation.PREFERENCE -> saveToPreferences();
            case ExtensionSettingSaveLocation.PROJECT -> saveToProject();
            default -> {}
        }
    }

    @Override
    public void load() {
        switch(DefaultSaveLocation) {
            case ExtensionSettingSaveLocation.PREFERENCE -> loadFromPreferences();
            case ExtensionSettingSaveLocation.PROJECT -> loadFromProject();
            default -> {}
        }
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public String getStorageKey() {
        return StorageKey;
    }

    @Override
    public String reset()
    {
        setCurrentValue(DefaultValue);
        return getCurrentValue();
    }
}
