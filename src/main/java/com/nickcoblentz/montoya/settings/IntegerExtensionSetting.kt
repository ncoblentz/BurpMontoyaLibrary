package com.nickcoblentz.montoya.settings

import burp.api.montoya.MontoyaApi

class IntegerExtensionSetting(
    api: MontoyaApi,
    name: String,
    storageKey: String,
    defaultValue: Int,
    defaultSaveLocation: ExtensionSettingSaveLocation
) :
    GenericExtensionSetting<Int>(api, name, storageKey, defaultValue, defaultSaveLocation) {
    override fun loadFromProject() {
        currentValue = api.persistence().extensionData().getInteger(storageKey)
    }

    override fun loadFromPreferences() {
        currentValue = api.persistence().preferences().getInteger(storageKey)
    }

    override fun parseString(value: String): Int {
        return value.toInt()
    }

    override fun setCurrentValue(value: String?) {
        if (value != null) currentValue = parseString(value)
    }

    override fun saveToProject() {
        api.persistence().extensionData().setInteger(storageKey, currentValue)
    }

    override fun saveToPreferences() {
        api.persistence().preferences().setInteger(storageKey, currentValue)
    }
}