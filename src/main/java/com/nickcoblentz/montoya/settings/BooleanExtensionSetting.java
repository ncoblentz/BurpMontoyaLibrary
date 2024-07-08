package com.nickcoblentz.montoya.settings;

import burp.api.montoya.MontoyaApi;

public class BooleanExtensionSetting extends GenericExtensionSetting<Boolean> {

    public BooleanExtensionSetting(MontoyaApi api, String name, String storageKey, Boolean defaultValue, ExtensionSettingSaveLocation defaultSaveLocation) {
        super(api, name, storageKey, defaultValue, defaultSaveLocation);
    }

    @Override
    public void loadFromProject() {
        setCurrentValue(getApi().persistence().extensionData().getBoolean(getStorageKey()));
    }

    @Override
    public void loadFromPreferences() {
        setCurrentValue(getApi().persistence().preferences().getBoolean(getStorageKey()));
    }

    @Override
    public Boolean parseString(String value) {
        return Boolean.valueOf(value);
    }

    public void setCurrentValue(String value) {
        if(value!=null)
            setCurrentValue(parseString(value));
    }
}
