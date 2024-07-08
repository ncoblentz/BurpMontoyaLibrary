package com.nickcoblentz.montoya.settings;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.persistence.PersistedList;
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
        MontoyaLogger logger = new MontoyaLogger(api,MontoyaLogger.DebugLogLevel);
        logger.debugLog("Loading Plugin");
        api.extension().setName(PluginName);
        //ExtensionSetting testSetting1 = new ExtensionSetting(api,"Test1","montoyalibrary.test1","the value here!", ExtensionSettingSaveLocation.PROJECT);
        //ExtensionSetting testSetting2 = new ExtensionSetting(api,"Test2","montoyalibrary.test2","another value here!", ExtensionSettingSaveLocation.PREFERENCE);
        StringExtensionSetting testSetting1 = new StringExtensionSetting(api,"String Test1","montoyalibrary.test1","the value here!", ExtensionSettingSaveLocation.PROJECT);
        StringExtensionSetting testSetting2 = new StringExtensionSetting(api,"String Test2","montoyalibrary.test2","another value here!", ExtensionSettingSaveLocation.PREFERENCE);
        ByteExtensionSetting testSetting3 = new ByteExtensionSetting(api,"Byte Test","montoyalibrary.test3",Byte.valueOf((byte)36), ExtensionSettingSaveLocation.PREFERENCE);
        BooleanExtensionSetting testSetting4 = new BooleanExtensionSetting(api,"Boolean Test","montoyalibrary.test4",Boolean.valueOf(true),ExtensionSettingSaveLocation.PROJECT);
        IntegerExtensionSetting testSetting5 = new IntegerExtensionSetting(api,"Integer Test","montoyalibrary.test5",Integer.valueOf(3),ExtensionSettingSaveLocation.PROJECT);
        LongExtensionSetting testSetting6 = new LongExtensionSetting(api,"Long Test","montoyalibrary.test6",Long.valueOf("9223372036854775807"),ExtensionSettingSaveLocation.PROJECT);
        ShortExtensionSetting testSetting7 = new ShortExtensionSetting(api,"Short Test","montoyalibrary.test7",Short.valueOf((short)123),ExtensionSettingSaveLocation.PROJECT);
        ListStringExtensionSetting testSetting8 = new ListStringExtensionSetting(api,"List String Test","montoyalibrary.test8",List.of("Test 1","test 2", "test 3"),ExtensionSettingSaveLocation.PREFERENCE);

        List<GenericExtensionSetting> extensionSetting = List.of(testSetting1,testSetting2,testSetting3,testSetting4,testSetting5,testSetting6,testSetting7,testSetting8);
        GenericExtensionSettingsFormGenerator gen = new GenericExtensionSettingsFormGenerator(extensionSetting,PluginName);
        FormBuilder settingsFormBuilder = gen.getSettingsFormBuilder();
        Form settingsForm = settingsFormBuilder.run();
        api.userInterface().registerContextMenuItemsProvider(new ExtensionSettingsContextMenuProvider(api,settingsForm));
        logger.debugLog("Finished");




    }
}
