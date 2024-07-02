package com.nickcoblentz.montoya.settings;

import java.util.Collection;

public class CollectionSetting<T> implements ICollectionOfSettings<T> {

    private Collection<T> Value;
    private Collection<T> Default;
    private boolean ViewInUI;
    private String DisplayName;
    private String SettingStorageKey;
    private String Description;
    private PersistInOption PersistIn;
    private boolean EditableInUI;
    private boolean MultiSelect;
    private Collection<T> Options;


    public CollectionSetting(Collection<T> value, Collection<T> aDefault, boolean viewInUI, boolean editableInUI, String displayName, String settingStorageKey, String description, PersistInOption persistIn, boolean multiSelect, Collection<T> options) {
        setValue(value);
        setDefault(aDefault);
        setViewInUI(viewInUI);
        setDisplayName(displayName);
        setSettingStorageKey(settingStorageKey);
        setDescription(description);
        setPersistIn(persistIn);
        setEditableInUI(editableInUI);
        setMultiSelect(multiSelect);
        setOptions(options);
    }
    public void setOptions(Collection<T> options)
    {
        Options=options;
    }

    public Collection<T> getOptions()
    {
        return Options;
    }

    public void setValue(Collection<T> value) {
        Value = value;

    }

    public void setDefault(Collection<T> aDefault) {
        Default = aDefault;
        if(Value==null)
        {
            Value=aDefault;
        }
    }

    @Override
    public Collection<T> getValue() {
        return Value;
    }

    @Override
    public void setValue(Object value) {
        System.out.println("Called");
        if (value instanceof Collection colValue) {
            Value = colValue;
            colValue.forEach(item -> System.out.println(item));
        }
    }

    @Override
    public Collection<T> getDefault() {
        return Default;
    }

    @Override
    public void setDefault(Object aDefault) {
        if (aDefault instanceof Collection colValue)
        Default=colValue;
    }

    @Override
    public boolean isViewInUI() {
        return ViewInUI;
    }

    @Override
    public void setViewInUI(boolean viewInUI) {
        ViewInUI = viewInUI;
    }

    @Override
    public boolean isEditableInUI() {
        return EditableInUI;
    }

    @Override
    public void setEditableInUI(boolean editableInUI) {
        EditableInUI=editableInUI;
    }

    @Override
    public String getDisplayName() {
        return DisplayName;
    }

    @Override
    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    @Override
    public String getSettingStorageKey() {
        return SettingStorageKey;
    }

    @Override
    public void setSettingStorageKey(String settingStorageKey) {
        SettingStorageKey = settingStorageKey;
    }

    @Override
    public String getDescription() {
        return Description;
    }

    @Override
    public void setDescription(String description) {
        Description = description;
    }

    @Override
    public PersistInOption getPersistIn() {
        return PersistIn;
    }

    @Override
    public void setPersistIn(PersistInOption persistIn) {
        PersistIn = persistIn;
    }

    @Override
    public void restoreDefault() {
        setValue(getDefault());
    }

    @Override
    public void addItem(T item) {
        getValue().add(item);
    }

    @Override
    public void clear() {
        getValue().clear();
    }

    @Override
    public void removeItem(T item) {
        getValue().remove(item);
    }

    @Override
    public boolean isMultiSelect() {
        return MultiSelect;
    }

    public void setMultiSelect(boolean multiSelect) {
        MultiSelect = multiSelect;
    }
}
