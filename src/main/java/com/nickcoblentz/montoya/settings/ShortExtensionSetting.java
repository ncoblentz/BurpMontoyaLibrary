package com.nickcoblentz.montoya.settings;

import burp.api.montoya.MontoyaApi;

public class ShortExtensionSetting extends GenericExtensionSetting<Short> {

    public ShortExtensionSetting(MontoyaApi api, String name, String storageKey, Short defaultValue, ExtensionSettingSaveLocation defaultSaveLocation) {
        super(api, name, storageKey, defaultValue, defaultSaveLocation);
    }

    @Override
    public void loadFromProject() {
        setCurrentValue(getApi().persistence().extensionData().getShort(getStorageKey()));
    }

    @Override
    public void loadFromPreferences() {
        setCurrentValue(getApi().persistence().preferences().getShort(getStorageKey()));
    }

    @Override
    public Short parseString(String value) {
        return Short.valueOf(value);
    }

    public void setCurrentValue(String value) {
        if(value!=null)
            setCurrentValue(parseString(value));
    }
}
