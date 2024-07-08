package com.nickcoblentz.montoya.settings;

import burp.api.montoya.MontoyaApi;

public class LongExtensionSetting extends GenericExtensionSetting<Long> {

    public LongExtensionSetting(MontoyaApi api, String name, String storageKey, Long defaultValue, ExtensionSettingSaveLocation defaultSaveLocation) {
        super(api, name, storageKey, defaultValue, defaultSaveLocation);
    }

    @Override
    public void loadFromProject() {
        setCurrentValue(getApi().persistence().extensionData().getLong(getStorageKey()));
    }

    @Override
    public void loadFromPreferences() {
        setCurrentValue(getApi().persistence().preferences().getLong(getStorageKey()));
    }

    @Override
    public Long parseString(String value) {
        return Long.valueOf(value);
    }

    public void setCurrentValue(String value) {
        if(value!=null)
            setCurrentValue(parseString(value));
    }
}
