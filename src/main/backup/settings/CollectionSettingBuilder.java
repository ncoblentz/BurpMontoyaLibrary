package com.nickcoblentz.montoya.settings;

import java.util.Collection;

public final class CollectionSettingBuilder<T> {
    private Collection<T> Value;
    private Collection<T> Default;
    private Collection<T> Options;
    private boolean ViewInUI;
    private String DisplayName;
    private String SettingStorageKey;
    private String Description;
    private PersistInOption PersistIn;
    private boolean EditableInUI;
    private boolean MultiSelect;

    private CollectionSettingBuilder() {
    }

    public static CollectionSettingBuilder aCollectionSetting() {
        return new CollectionSettingBuilder();
    }

    public CollectionSettingBuilder withValue(Collection<T> Value) {
        this.Value = Value;
        return this;
    }

    public CollectionSettingBuilder withValueIgnoreNull(Collection<T> Value) {
        if(Value!=null)
            withValue(Value);
        return this;
    }



    public CollectionSettingBuilder withDefault(Collection<T> Default) {
        this.Default = Default;
        return this;
    }

    public CollectionSettingBuilder withOptions(Collection<T> options) {
        this.Options = options;
        return this;
    }

    public CollectionSettingBuilder withViewInUI(boolean ViewInUI) {
        this.ViewInUI = ViewInUI;
        return this;
    }

    public CollectionSettingBuilder withEditableInUI(boolean editableInUI) {
        this.EditableInUI = editableInUI;
        return this;
    }

    public CollectionSettingBuilder withDisplayName(String DisplayName) {
        this.DisplayName = DisplayName;
        return this;
    }

    public CollectionSettingBuilder withSettingStorageKey(String SettingStorageKey) {
        this.SettingStorageKey = SettingStorageKey;
        return this;
    }

    public CollectionSettingBuilder withDescription(String Description) {
        this.Description = Description;
        return this;
    }

    public CollectionSettingBuilder withPersistIn(PersistInOption PersistIn) {
        this.PersistIn = PersistIn;
        return this;
    }

    public CollectionSettingBuilder withMultiSelect(boolean multiSelect) {
        MultiSelect = multiSelect;
        return this;
    }

    public CollectionSetting build() {
        CollectionSetting collectionSetting = new CollectionSetting(null, null, false, false, null, null, null, null,false, null);
        collectionSetting.setValue(Value);
        collectionSetting.setDefault(Default);
        collectionSetting.setViewInUI(ViewInUI);
        collectionSetting.setDisplayName(DisplayName);
        collectionSetting.setSettingStorageKey(SettingStorageKey);
        collectionSetting.setDescription(Description);
        collectionSetting.setPersistIn(PersistIn);
        collectionSetting.setMultiSelect(MultiSelect);
        collectionSetting.setOptions(Options);
        return collectionSetting;
    }
}
