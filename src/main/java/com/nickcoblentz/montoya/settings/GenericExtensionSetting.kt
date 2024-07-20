package com.nickcoblentz.montoya.settings

import burp.api.montoya.MontoyaApi
import com.nickcoblentz.montoya.LogLevel
import com.nickcoblentz.montoya.MontoyaLogger

abstract class GenericExtensionSetting<T>(
    val api: MontoyaApi,
    val name: String,
    val storageKey: String,
    val defaultValue: T,
    private val defaultSaveLocation: ExtensionSettingSaveLocation
) {


    //Logger = new MontoyaLogger(api,MontoyaLogger.DebugLogLevel);
    val Logger: MontoyaLogger = MontoyaLogger(api, LogLevel.DEBUG)
    var currentValue = defaultValue
        get() {
            return when(field) {
                null -> defaultValue
                else -> field
            }
        }
        set(value) {
            if (value != null) field = value
        }

    init {
        load()
    }

    abstract fun setCurrentValue(value: String?)


    abstract fun saveToProject()


    abstract fun loadFromProject() /*
    {
        setCurrentValue(Api.persistence().extensionData().getString(StorageKey));
    }*/


    abstract fun saveToPreferences()


    abstract fun loadFromPreferences()


    fun save() {
        when (defaultSaveLocation) {
            ExtensionSettingSaveLocation.PREFERENCE -> saveToPreferences()
            ExtensionSettingSaveLocation.PROJECT -> saveToProject()
            else -> {}
        }
    }


    fun load() {
        when (defaultSaveLocation) {
            ExtensionSettingSaveLocation.PREFERENCE -> loadFromPreferences()
            ExtensionSettingSaveLocation.PROJECT -> loadFromProject()
            else -> {}
        }
    }


    fun reset(): T {
        currentValue=defaultValue
        return currentValue
    }

    abstract fun parseString(value: String): T
}