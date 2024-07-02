package com.nickcoblentz.montoya.settings;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ValueSetting<T> implements ISetting<T> {

    private T Value;
    private T Default;
    private boolean ViewInUI;
    private String DisplayName;
    private String SettingStorageKey;
    private String Description;
    private PersistInOption PersistIn;
    private boolean EditableInUI;

    public ValueSetting(T value, T aDefault, boolean viewInUI,boolean editableInUI, String displayName, String settingStorageKey, String description, PersistInOption persistIn) {
        setValue(value);
        setDefault(aDefault);
        setViewInUI(viewInUI);
        setDisplayName(displayName);
        setSettingStorageKey(settingStorageKey);
        setDescription(description);
        setPersistIn(persistIn);
        setEditableInUI(editableInUI);
    }

    @Override
    public T getValue() {
        return Value;
    }

    @Override
    public void setValue(T value) {
        Value=value;
    }

    @Override
    public T getDefault() {
        return Default;
    }

    @Override
    public void setDefault(T aDefault) {
        Default=aDefault;
        if(Value==null)
        {
            Value=aDefault;
        }
    }

    @Override
    public boolean isViewInUI() {
        return ViewInUI;
    }

    @Override
    public void setViewInUI(boolean viewInUI) {
        ViewInUI=viewInUI;
    }

    @Override
    public String getDisplayName() {
        return DisplayName;
    }

    @Override
    public void setDisplayName(String displayName) {
        DisplayName=displayName;
    }

    @Override
    public String getSettingStorageKey() {
        return SettingStorageKey;
    }

    @Override
    public void setSettingStorageKey(String settingStorageKey) {
        SettingStorageKey=settingStorageKey;
    }

    @Override
    public String getDescription() {
        return Description;
    }

    @Override
    public void setDescription(String description) {
        Description=description;
    }

    @Override
    public PersistInOption getPersistIn() {
        return PersistIn;
    }

    @Override
    public void setPersistIn(PersistInOption persistIn) {
        PersistIn=persistIn;
    }

    @Override
    public void restoreDefault() {
        setValue(getDefault());
    }

    @Override
    public boolean isEditableInUI() {
        return EditableInUI;
    }

    @Override
    public void setEditableInUI(boolean editableInUI) {
        EditableInUI=editableInUI;
    }
}
