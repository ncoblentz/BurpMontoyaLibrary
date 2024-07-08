package com.nickcoblentz.montoya.settings;

import burp.api.montoya.MontoyaApi;

public class ByteExtensionSetting extends GenericExtensionSetting<Byte> {

    public ByteExtensionSetting(MontoyaApi api, String name, String storageKey, Byte defaultValue, ExtensionSettingSaveLocation defaultSaveLocation) {
        super(api, name, storageKey, defaultValue, defaultSaveLocation);
    }

    @Override
    public void loadFromProject() {
        setCurrentValue(getApi().persistence().extensionData().getByte(getStorageKey()));
    }

    @Override
    public void loadFromPreferences() {
        setCurrentValue(getApi().persistence().preferences().getByte(getStorageKey()));
    }

    @Override
    public String toString() {
        return String.format("%02X",getCurrentValue());
    }

    @Override
    public Byte parseString(String value) {
        return Byte.valueOf(value);
    }

    public void setCurrentValue(String value) {
        if(value!=null)
            setCurrentValue(parseString(value));
    }
}
