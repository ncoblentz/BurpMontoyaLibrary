package com.nickcoblentz.montoya.settings

import burp.api.montoya.MontoyaApi

class StringExtensionSetting(
    api: MontoyaApi,
    name: String,
    storageKey: String,
    defaultValue: String,
    defaultSaveLocation: ExtensionSettingSaveLocation
) :
    GenericExtensionSetting<String>(api, name!!, storageKey, defaultValue, defaultSaveLocation) {
    override fun loadFromProject() {
        currentValue = api.persistence().extensionData().getString(storageKey)
    }

    override fun loadFromPreferences() {
        currentValue = api.persistence().preferences().getString(storageKey)
    }

    override fun parseString(value: String): String {
        return value
    }

    override fun setCurrentValue(value: String?) {
        if(value!=null)
            currentValue=value
    }

    override fun saveToProject() {
        api.persistence().extensionData().setString(storageKey, currentValue)
    }

    override fun saveToPreferences() {
        api.persistence().preferences().setString(storageKey, currentValue)
    }
}