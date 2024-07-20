package com.nickcoblentz.montoya.settings

import burp.api.montoya.MontoyaApi

class LongExtensionSetting(
    api: MontoyaApi,
    name: String,
    storageKey: String,
    defaultValue: Long,
    defaultSaveLocation: ExtensionSettingSaveLocation
) :
    GenericExtensionSetting<Long>(api, name, storageKey, defaultValue, defaultSaveLocation) {
    override fun loadFromProject() {
        currentValue = api.persistence().extensionData().getLong(storageKey)
    }

    override fun loadFromPreferences() {
        currentValue = api.persistence().preferences().getLong(storageKey)
    }

    override fun parseString(value: String): Long {
        return value.toLong()
    }

    override fun setCurrentValue(value: String?) {
        if (value != null) currentValue = parseString(value)
    }

    override fun saveToProject() {
        api.persistence().extensionData().setLong(storageKey, currentValue)
    }

    override fun saveToPreferences() {
        api.persistence().preferences().setLong(storageKey, currentValue)
    }
}