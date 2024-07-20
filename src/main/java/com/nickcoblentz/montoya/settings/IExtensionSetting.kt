package com.nickcoblentz.montoya.settings

interface IExtensionSetting {
    var currentValue : String
    val defaultSaveLocation: ExtensionSettingSaveLocation
    fun saveToProject()

    fun loadFromProject()

    fun saveToPreferences()

    fun loadFromPreferences()

    fun save()
    fun load()

    val name: String

    val storageKey: String

    fun reset(): String?
}