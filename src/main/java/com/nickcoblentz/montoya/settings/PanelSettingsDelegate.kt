package com.nickcoblentz.montoya.settings

import burp.api.montoya.ui.settings.SettingsPanelBuilder
import burp.api.montoya.ui.settings.SettingsPanelSetting
import burp.api.montoya.ui.settings.SettingsPanelWithData
import kotlin.properties.ReadOnlyProperty

class PanelSettingsDelegate(private var settingsPanelBuilder : SettingsPanelBuilder) {

    var settingsPanel : SettingsPanelWithData? = null

    fun stringSetting(name: String, defaultValue: String): ReadOnlyProperty<Any, String> {
        settingsPanelBuilder.withSetting(SettingsPanelSetting.stringSetting(name, defaultValue))
        return ReadOnlyProperty { _, _ -> settingsPanel?.getString(name) ?: "" }
    }

    fun integerSetting(name: String, defaultValue: Int): ReadOnlyProperty<Any, Int> {
        settingsPanelBuilder.withSetting(SettingsPanelSetting.integerSetting(name, defaultValue))
        return ReadOnlyProperty { _, _ -> settingsPanel?.getInteger(name) ?: 0}
    }

    fun booleanSetting(name: String, defaultValue: Boolean): ReadOnlyProperty<Any, Boolean> {
        settingsPanelBuilder.withSetting(SettingsPanelSetting.booleanSetting(name, defaultValue))
        return ReadOnlyProperty { _, _ -> settingsPanel?.getBoolean(name) ?: false}
    }

    fun listSetting(name: String, options: MutableList<String>,defaultValue: String): ReadOnlyProperty<Any, String> {
        settingsPanelBuilder.withSetting(SettingsPanelSetting.listSetting(name, options, defaultValue))
        return ReadOnlyProperty { _, _ -> settingsPanel?.getString(name) ?: defaultValue}
    }



    fun buildSettingsPanel() : SettingsPanelWithData {
        val settingsPanelTemp = settingsPanelBuilder.build()
        settingsPanel = settingsPanelTemp
        return settingsPanelTemp
    }
}
