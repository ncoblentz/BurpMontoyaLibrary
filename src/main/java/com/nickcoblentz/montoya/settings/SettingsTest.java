package com.nickcoblentz.montoya.settings;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import com.nickcoblentz.montoya.MontoyaLogger;
import de.milchreis.uibooster.model.Form;
import de.milchreis.uibooster.model.FormBuilder;

import java.util.List;

public class SettingsTest  implements BurpExtension {
    private MontoyaApi Api;

    private static final String PluginName = "Test Settings";
    @Override
    public void initialize(MontoyaApi api) {
        Api=api;
        api.extension().setName(PluginName);
        MontoyaLogger logger = new MontoyaLogger(api,MontoyaLogger.DebugLogLevel);
        ExtensionSetting testSetting1 = new ExtensionSetting(api,"Test1","montoyalibrary.test1","the value here!", ExtensionSettingSaveLocation.PROJECT);
        ExtensionSetting testSetting2 = new ExtensionSetting(api,"Test2","montoyalibrary.test2","another value here!", ExtensionSettingSaveLocation.PREFERENCE);
        List<IExtensionSetting> extensionSetting = List.of(testSetting1,testSetting2);
        ExtensionSettingsFormGenerator gen = new ExtensionSettingsFormGenerator(extensionSetting,PluginName);
        FormBuilder settingsFormBuilder = gen.getSettingsFormBuilder();
        Form settingsForm = settingsFormBuilder.run();

        api.userInterface().registerContextMenuItemsProvider(new ExtensionSettingsContextMenuProvider(api,settingsForm));


        logger.debugLog("Loading Plugin");



    }
}
