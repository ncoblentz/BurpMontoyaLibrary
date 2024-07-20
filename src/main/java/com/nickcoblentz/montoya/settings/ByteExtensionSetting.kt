package com.nickcoblentz.montoya.settings

import burp.api.montoya.MontoyaApi

class ByteExtensionSetting(
    api: MontoyaApi,
    name: String,
    storageKey: String,
    defaultValue: Byte,
    defaultSaveLocation: ExtensionSettingSaveLocation
) :
    GenericExtensionSetting<Byte>(api, name, storageKey, defaultValue, defaultSaveLocation) {
    override fun loadFromProject() {
        currentValue = api.persistence().extensionData().getByte(storageKey)
    }

    override fun loadFromPreferences() {
        currentValue = api.persistence().preferences().getByte(storageKey)
    }

    override fun toString(): String {
        return String.format("%02X", currentValue)
    }

    override fun saveToProject() {
        api.persistence().extensionData().setByte(storageKey, currentValue)
    }

    override fun saveToPreferences() {
        api.persistence().preferences().setByte(storageKey, currentValue)
    }

    override fun parseString(value: String): Byte {
        return value.toByte()
    }

    override fun setCurrentValue(value: String?) {
        if (value != null) currentValue = parseString(value)
    }
}