package com.nickcoblentz.montoya.settings

import burp.api.montoya.MontoyaApi

class ShortExtensionSetting(
    api: MontoyaApi,
    name: String,
    storageKey: String,
    defaultValue: Short,
    defaultSaveLocation: ExtensionSettingSaveLocation
) :
    GenericExtensionSetting<Short>(api, name, storageKey, defaultValue, defaultSaveLocation) {
    override fun loadFromProject() {
        currentValue = api.persistence().extensionData().getShort(storageKey)
    }

    override fun loadFromPreferences() {
        currentValue = api.persistence().preferences().getShort(storageKey)
    }

    override fun parseString(value: String): Short {
        return value.toShort()
    }

    override fun setCurrentValue(value: String?) {
        if (value != null) currentValue = parseString(value)
    }

    override fun saveToProject() {
        api.persistence().extensionData().setShort(storageKey, currentValue)
    }

    override fun saveToPreferences() {
        api.persistence().preferences().setShort(storageKey, currentValue)
    }
}