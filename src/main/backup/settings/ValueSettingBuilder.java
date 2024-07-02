package com.nickcoblentz.montoya.settings;

public final class ValueSettingBuilder<T> {
    private T Value;
    private T Default;
    private boolean ViewInUI;
    private boolean EditableInUI;
    private String DisplayName;
    private String SettingStorageKey;
    private String Description;
    private PersistInOption PersistIn;

    private ValueSettingBuilder() {
    }

    public static ValueSettingBuilder aValueSetting() {
        return new ValueSettingBuilder();
    }

    public ValueSettingBuilder withValue(T Value) {
        this.Value = Value;
        return this;
    }

    public ValueSettingBuilder withValueIgnoreNull(T Value) {
        if(Value != null)
            withValue(Value);
        return this;
    }

    public ValueSettingBuilder withDefault(T Default) {
        this.Default = Default;
        return this;
    }

    public ValueSettingBuilder withViewInUI(boolean ViewInUI) {
        this.ViewInUI = ViewInUI;
        return this;
    }

    public ValueSettingBuilder withEditableInUI(boolean EditableInUI) {
        this.EditableInUI = EditableInUI;
        return this;
    }

    public ValueSettingBuilder withDisplayName(String DisplayName) {
        this.DisplayName = DisplayName;
        return this;
    }

    public ValueSettingBuilder withSettingStorageKey(String SettingStorageKey) {
        this.SettingStorageKey = SettingStorageKey;
        return this;
    }

    public ValueSettingBuilder withDescription(String Description) {
        this.Description = Description;
        return this;
    }

    public ValueSettingBuilder withPersistIn(PersistInOption PersistIn) {
        this.PersistIn = PersistIn;
        return this;
    }

    public ValueSetting build() {
        ValueSetting valueSetting = new ValueSetting(null, null, false, false,null, null, null, null);
        valueSetting.setValue(Value);
        valueSetting.setDefault(Default);
        valueSetting.setViewInUI(ViewInUI);
        valueSetting.setDisplayName(DisplayName);
        valueSetting.setSettingStorageKey(SettingStorageKey);
        valueSetting.setDescription(Description);
        valueSetting.setPersistIn(PersistIn);
        return valueSetting;
    }
}
