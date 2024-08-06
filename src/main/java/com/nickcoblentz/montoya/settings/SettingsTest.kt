/*
package com.nickcoblentz.montoya.settings

import burp.api.montoya.BurpExtension
import burp.api.montoya.MontoyaApi
import com.nickcoblentz.montoya.LogLevel
import com.nickcoblentz.montoya.MontoyaLogger

class SettingsTest : BurpExtension {
    private var _api: MontoyaApi? = null
    private val _pluginName = "Test Settings"

    override fun initialize(api: MontoyaApi) {
        _api = api
        //MontoyaLogger logger = new MontoyaLogger(api,MontoyaLogger.DebugLogLevel);
        val logger = MontoyaLogger(api, LogLevel.DEBUG)
        logger.debugLog("Loading Plugin")
        api.extension().setName(_pluginName)

        val testSetting1 = StringExtensionSetting(
            api,
            "String Test1",
            "montoyalibrary.test1",
            "the value here!",
            ExtensionSettingSaveLocation.PROJECT
        )
        val testSetting2 = StringExtensionSetting(
            api,
            "String Test2",
            "montoyalibrary.test2",
            "another value here!",
            ExtensionSettingSaveLocation.PREFERENCE
        )
        val testSetting3 = ByteExtensionSetting(
            api,
            "Byte Test",
            "montoyalibrary.test3",
            36.toByte(),
            ExtensionSettingSaveLocation.PREFERENCE
        )
        val testSetting4 = BooleanExtensionSetting(
            api,
            "Boolean Test",
            "montoyalibrary.test4",
            true,
            ExtensionSettingSaveLocation.PROJECT
        )
        val testSetting5 = IntegerExtensionSetting(
            api,
            "Integer Test",
            "montoyalibrary.test5",
            3,
            ExtensionSettingSaveLocation.PROJECT
        )
        val testSetting6 = LongExtensionSetting(
            api,
            "Long Test",
            "montoyalibrary.test6",
            "9223372036854775807".toLong(),
            ExtensionSettingSaveLocation.PROJECT
        )
        val testSetting7 = ShortExtensionSetting(
            api,
            "Short Test",
            "montoyalibrary.test7",
            123.toShort(),
            ExtensionSettingSaveLocation.PROJECT
        )
        val testSetting8 = ListStringExtensionSetting(
            api,
            "List String Test",
            "montoyalibrary.test8",
            listOf("Test 1", "test 2", "test 3"),
            ExtensionSettingSaveLocation.PREFERENCE
        )

        val extensionSetting = listOf(
            testSetting1,
            testSetting2,
            testSetting3,
            testSetting4,
            testSetting5,
            testSetting6,
            testSetting7,
            testSetting8
        )
        val gen = GenericExtensionSettingsFormGenerator(extensionSetting, _pluginName)
        val settingsFormBuilder = gen.getSettingsFormBuilder()
        val settingsForm = settingsFormBuilder.run()
        api.userInterface().registerContextMenuItemsProvider(ExtensionSettingsContextMenuProvider(api, settingsForm))
        logger.debugLog("Finished")
    }
}*/