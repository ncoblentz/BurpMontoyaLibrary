package com.nickcoblentz.montoya.settings;

import burp.api.montoya.MontoyaApi;

public class StringExtensionSetting extends GenericExtensionSetting<String> {

    public StringExtensionSetting(MontoyaApi api, String name, String storageKey, String defaultValue, ExtensionSettingSaveLocation defaultSaveLocation) {
        super(api, name, storageKey, defaultValue, defaultSaveLocation);
    }

    @Override
    public void loadFromProject() {
      setCurrentValue(getApi().persistence().extensionData().getString(getStorageKey()));
    }

    @Override
    public void loadFromPreferences() {
        setCurrentValue(getApi().persistence().preferences().getString(getStorageKey()));
    }

    @Override
    public String parseString(String value) {
        return value;
    }

    @Override
    public void saveToProject() {
        getApi().persistence().extensionData().setString(getStorageKey(),getCurrentValue());
    }

    @Override
    public void saveToPreferences() {
        getApi().persistence().preferences().setString(getStorageKey(),getCurrentValue());
    }
}
