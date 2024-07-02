package com.nickcoblentz.montoya.settings;

import java.util.*;
import java.util.function.Function;



//import com.formdev.flatlaf.FlatLightLaf;
import burp.api.montoya.MontoyaApi;
import com.google.gson.Gson;
import org.httprpc.sierra.ScrollingKeyboardFocusManager;

import javax.swing.*;
import java.awt.KeyboardFocusManager;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class SettingsStore {

    public List<ISetting> allSettings = new LinkedList<ISetting>();

    public MontoyaApi Api;

    private Gson gson = new Gson();

    public SettingsStore()
    {

    }

    public SettingsStore(MontoyaApi api)
    {
        super();
        Api=api;
    }

    public MontoyaApi getApi() {
        return Api;
    }

    public void setApi(MontoyaApi api) {
        Api = api;
    }


    public void persistSetting(ValueSetting setting)
    {
        if(Api!=null)
        {
            Api.persistence().preferences().setString(setting.getSettingStorageKey(),setting.getValue().toString());
        }
    }

    public void persistCollectionSetting(CollectionSetting setting)
    {
        if(Api!=null)
        {
            Api.persistence().preferences().setString(setting.getSettingStorageKey(),gson.toJson(setting.getValue()));
        }
    }

    public String retrieveSetting(String storageKey)
    {
        if(Api!=null)
        {
            if(Api.persistence().preferences().stringKeys().contains(storageKey))
            {
                return Api.persistence().preferences().getString(storageKey);
            }
        }
        return null;
    }

    public Collection<String> retriveCollectionSetting(String storageKey)
    {
        if(Api!=null)
        {
            if(Api.persistence().preferences().stringKeys().contains(storageKey))
            {
                return gson.fromJson(Api.persistence().preferences().getString(storageKey),Collection.class);
            }
        }
        return null;
    }

    public List<ISetting> getAllSettings() {
        return allSettings;
    }

    public ValueSetting<String> addStringValueSettingFromBuilder(Function<ValueSettingBuilder<String>,ValueSetting<String>> function)
    {
        ValueSettingBuilder<String> builder = ValueSettingBuilder.aValueSetting();
        ValueSetting<String> setting = function.apply(builder);
        this.add(setting);

        return setting;
    }

    public CollectionSetting<String> addCollectionOfStringSettingFromBuilder(Function<CollectionSettingBuilder<String>,CollectionSetting<String>> function)
    {
        CollectionSettingBuilder<String> builder = CollectionSettingBuilder.aCollectionSetting();
        CollectionSetting<String> setting = function.apply(builder);
        this.add(setting);
        return setting;
    }

    private void add(ISetting setting)
    {
        allSettings.add(setting);
    }

    public static void main(String[] args) throws Exception
    {
       /* SettingsStore store = new SettingsStore();
        ValueSetting<String> vs1 = store.addStringValueSettingFromBuilder(builder -> {
            return builder
                    .withDefault("Default here")
                    .withDescription("Descripfdskjfdjkdfsdfsdsfdsfjtion here")
                    .withDisplayName("Display name herefdsjkldfsjfdsjkfdsjkdfsjlkdfsjkldsfjlkfdsjklfdsljkfdslkjdsflkjdsfljkdfs")
                    .withValue("Value here")
                    .withPersistIn(PersistInOption.NONE)
                    .withViewInUI(true)
                    .withSettingStorageKey("group.sub.name")
                    .build();
        });

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
                   .withMultiSelect(false)
                   .build();

        });

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
                    .build();

        });





        KeyboardFocusManager.setCurrentKeyboardFocusManager(new ScrollingKeyboardFocusManager());
        JFrame frame = new JFrame();
        SettingsPanel panel = new SettingsPanel(store);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setContentPane(panel.getPane());
        frame.setSize(480, 360);
        frame.setVisible(true);*/





        /*
        String[] items = new String[3];
        items[0]="first item";
        items[1]="jsfkdlfsdfdjksldfksjlfdskjlfdskjlfdskjlfdskjlfdskjlfsdkjlfsdjklfsdjklsfdjklfsdljkfsdkjsfdlkjfsdjk";
        items[2]="ssfddfssdf";
        JList<String> jList = new JList<String>(items);
        jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        var panel = PanelMatic.begin()
                .addHeader( PanelBuilder.HeaderLevel.H1, "Song Details")
                .add("Nfsdfdsjfdsjkfdskjlfdslkjdfskjlfdskjlfdskljfdsjklfdsjklfdsjkldfsjklsdfjklfdsjlkfdsjkldfsjfdklsfldjksfldjksfldsjklfdsjkfdsjkfdslkjfdsljkfdsljkdsffdsfdsfdsfdsame", new JTextField("test1"))
                .add("Album",new JTextField("testsdffdsdfslfdsjldfsljkdfsljdfsjlkdflskjfjdklsljkdfsljkdfsjklfdsjklfdsjklfdsjklfdsjlkfdsljkfdslkjfdsljkdfsljkdfslkjfdslkjfdsljkfdsljkdfs2"))
                .add("Artist",new JTextField("test3"))
                .add("Combo",jList)
	            .add( new JButton("test"), L_END)
            .get();
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setSize(480, 360);
        frame.setVisible(true);
*/


    }
}


