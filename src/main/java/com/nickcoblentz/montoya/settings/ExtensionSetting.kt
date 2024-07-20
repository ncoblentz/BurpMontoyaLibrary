package com.nickcoblentz.montoya.settings

import burp.api.montoya.MontoyaApi
import com.nickcoblentz.montoya.MontoyaLogger

class ExtensionSetting(private val api: MontoyaApi, override val name: String,override val storageKey: String, public val defaultValue: String, public override val defaultSaveLocation: ExtensionSettingSaveLocation) : IExtensionSetting {
    private val Logger = MontoyaLogger(api)

    override var currentValue: String = defaultValue
        get() {
            return if (field == null || field.isEmpty()) defaultValue else field
        }
        set(value) {
            if (value != null) field = value
        }

    init {
        load()
    }

    override fun saveToProject() {
        api.persistence().extensionData().setString(storageKey, currentValue)
    }

    override fun loadFromProject() {
        currentValue = api.persistence().extensionData().getString(storageKey)
    }

    override fun saveToPreferences() {
        api.persistence().preferences().setString(storageKey, currentValue)
    }

    override fun loadFromPreferences() {
        currentValue = api.persistence().preferences().getString(storageKey)
    }

    override fun save() {
        when (defaultSaveLocation) {
            ExtensionSettingSaveLocation.PREFERENCE -> saveToPreferences()
            ExtensionSettingSaveLocation.PROJECT -> saveToProject()
            else -> {}
        }
    }

    override fun load() {
        when (defaultSaveLocation) {
            ExtensionSettingSaveLocation.PREFERENCE -> loadFromPreferences()
            ExtensionSettingSaveLocation.PROJECT -> loadFromProject()
            else -> {}
        }
    }

    override fun reset(): String? {
        currentValue = defaultValue
        return currentValue
    }
}