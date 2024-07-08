package com.nickcoblentz.montoya.settings;

import burp.api.montoya.MontoyaApi;
import com.nickcoblentz.montoya.MontoyaLogger;

public class IntegerExtensionSetting extends GenericExtensionSetting<Integer> {

    public IntegerExtensionSetting(MontoyaApi api, String name, String storageKey, Integer defaultValue, ExtensionSettingSaveLocation defaultSaveLocation) {
        super(api, name, storageKey, defaultValue, defaultSaveLocation);
    }

    @Override
    public void loadFromProject() {
        setCurrentValue(getApi().persistence().extensionData().getInteger(getStorageKey()));
    }

    @Override
    public void loadFromPreferences() {
        setCurrentValue(getApi().persistence().preferences().getInteger(getStorageKey()));
    }

    @Override
    public Integer parseString(String value) {
        return Integer.valueOf(value);
    }

    public void setCurrentValue(String value) {
        if(value!=null)
            setCurrentValue(parseString(value));
    }

    @Override
    public void saveToProject() {
        getApi().persistence().extensionData().setInteger(getStorageKey(),getCurrentValue().intValue());
    }

    @Override
    public void saveToPreferences() {
        getApi().persistence().preferences().setInteger(getStorageKey(),getCurrentValue().intValue());
    }
}
