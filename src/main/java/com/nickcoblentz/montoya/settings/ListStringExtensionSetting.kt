package com.nickcoblentz.montoya.settings

import burp.api.montoya.MontoyaApi

class ListStringExtensionSetting(
    api: MontoyaApi,
    name: String,
    storageKey: String,
    defaultValue: List<String>,
    defaultSaveLocation: ExtensionSettingSaveLocation
) :
    GenericExtensionSetting<List<String>>(
        api,
        name, storageKey, defaultValue, defaultSaveLocation
    ) {
    override fun loadFromProject() {
        currentValue = parseStringAsList(api.persistence().extensionData().getString(storageKey))
    }

    override fun loadFromPreferences() {
        currentValue = parseStringAsList(api.persistence().preferences().getString(storageKey))
    }

    override fun saveToProject() {
        api.persistence().extensionData().setString(storageKey, joinListAsString(currentValue))
    }

    override fun saveToPreferences() {
        api.persistence().preferences().setString(storageKey, joinListAsString(currentValue))
    }

    fun parseStringAsList(values: String): List<String> {
        if (values.isNotEmpty()) {
            return values.split("\n")
        }
        return emptyList()
    }

    fun joinListAsString(values: List<String>): String {
        return java.lang.String.join("\n", values)
    }

    override fun setCurrentValue(value: String?) {
        currentValue = if(value!=null) parseStringAsList(value) else currentValue
    }

    override fun parseString(value: String): List<String> {
        return parseStringAsList(value)
    }

    fun getCurrentValueAsString(): String {
        return joinListAsString(currentValue)
    }
}