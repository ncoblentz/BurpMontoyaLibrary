package com.nickcoblentz.montoya.settings;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import com.nickcoblentz.montoya.MontoyaLogger;
import org.httprpc.sierra.ScrollingKeyboardFocusManager;

import java.util.Arrays;
import java.util.List;

public class SettingsTest  implements BurpExtension {
    private MontoyaApi Api;
    private SettingsStore store;
    @Override
    public void initialize(MontoyaApi api) {
        Api=api;
        api.extension().setName("Test Settings");
        MontoyaLogger logger = new MontoyaLogger(api,MontoyaLogger.DebugLogLevel);
        logger.debugLog(this.getClass().getName(),"Loading Plugin");

        store = new SettingsStore(api);

        /*api.persistence().preferences().stringKeys().forEach(
            item -> api.persistence().preferences().deleteString(item));*/


        ValueSetting<String> setting1 = store.addStringValueSettingFromBuilder(builder -> {
                return builder
                        .withDefault("Default here")
                        .withDescription("Descripfdskjfdjkdfsdfsdsfdsfjtion here")
                        .withDisplayName("Display name herefdsjkldfsjfdsjkfdsjkdfsjlkdfsjkldsfjlkfdsjklfdsljkfdslkjdsflkjdsfljkdfs")
                        .withValue("Value here")
                        .withPersistIn(PersistInOption.NONE)
                        .withViewInUI(true)
                        .withSettingStorageKey("group.sub.name")
                        .withValueIgnoreNull(store.retrieveSetting("group.sub.name"))
                        .build();
            });



        store.persistSetting(setting1);

        CollectionSetting<String> settingList = store.addCollectionOfStringSettingFromBuilder(builder -> {
            List<String> options = Arrays.asList("Option 1","Option 2fdsdfskldfsdfsjkldfsjkldfsjkldfsjkldfsjkldfsjkldfsjkldfsjkldfsjklfsdjklfsdljksfdjlksdfljksdfljksdfljksdfljkDefault", "Option 3");
            return builder
                    .withOptions(options)
                    .withDefault(List.of(options.getFirst()))
                    .withValue(List.of(options.getLast()))
                    .withDescription("Description here")
                    .withDisplayName("Dhere")
                    .withPersistIn(PersistInOption.NONE)
                    .withViewInUI(true)
                    .withSettingStorageKey("group.sub.name2")
                    .withValueIgnoreNull(store.retriveCollectionSetting("group.sub.name2"))
                    .withMultiSelect(false)
                    .build();

        });

        store.persistCollectionSetting(settingList);

        CollectionSetting<String> multiList = store.addCollectionOfStringSettingFromBuilder(builder -> {
            List<String> options = Arrays.asList("Option 1","Option 2fdsdfskldfsdfsjkldfsjkldfsjkldfsjkldfsjkldfsjkldfsjkldfsjkldfsjklfsdjklfsdljksfdjlksdfljksdfljksdfljksdfljkDefault", "Option 3");

            return builder
                    .withOptions(options)
                    .withDefault(List.of(options.getFirst()))
                    .withValue(List.of(options.getFirst(),options.getLast()))
                    .withDescription("Description here")
                    .withDisplayName("Display name here")
                    .withPersistIn(PersistInOption.NONE)
                    .withViewInUI(true)
                    .withMultiSelect(true)
                    .withSettingStorageKey("group.sub.name3")
                    .withValueIgnoreNull(store.retriveCollectionSetting("group.sub.name3"))
                    .build();

        });

        store.persistCollectionSetting(multiList);

        SettingsPanel panel = new SettingsPanel(store);

        api.userInterface().registerSuiteTab("Settings Test", panel.getPane());

        logger.debugLog(this.getClass().getName(),"Finished Loading Plugin");

        api.persistence().preferences().stringKeys().forEach(
                item -> logger.debugLog(this.getClass().getName(),String.format("%s: %s",item,api.persistence().preferences().getString(item))));

    }
}
