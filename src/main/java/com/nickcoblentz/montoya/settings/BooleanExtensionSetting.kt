package com.nickcoblentz.montoya.settings

import burp.api.montoya.MontoyaApi

class BooleanExtensionSetting(
    api: MontoyaApi,
    name: String,
    storageKey: String,
    defaultValue: Boolean,
    defaultSaveLocation: ExtensionSettingSaveLocation
) :
    GenericExtensionSetting<Boolean>(api, name, storageKey, defaultValue, defaultSaveLocation) {
    override fun loadFromProject() {
        currentValue = api.persistence().extensionData().getBoolean(storageKey)
    }

    override fun loadFromPreferences() {
        currentValue = api.persistence().preferences().getBoolean(storageKey)
    }

    override fun saveToProject() {
        api.persistence().extensionData().setBoolean(storageKey, currentValue)
    }

    override fun saveToPreferences() {
        api.persistence().preferences().setBoolean(storageKey, currentValue)
    }

    override fun parseString(value: String): Boolean {
        return value.toBoolean()
    }

    override fun setCurrentValue(value: String?) {
        if (value != null) currentValue = parseString(value)
    }
}