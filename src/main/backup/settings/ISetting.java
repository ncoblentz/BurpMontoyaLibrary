package com.nickcoblentz.montoya.settings;

public interface ISetting<T> {
    T getValue();

    void setValue(T value);

    T getDefault();

    void setDefault(T aDefault);

    boolean isViewInUI();


    void setViewInUI(boolean viewInUI);

    boolean isEditableInUI();
    void setEditableInUI(boolean editableInUI);

    String getDisplayName();

    void setDisplayName(String displayName);

    String getSettingStorageKey();

    void setSettingStorageKey(String settingStorageKey);

    String getDescription();

    void setDescription(String description);

    PersistInOption getPersistIn();

    void setPersistIn(PersistInOption persistIn);

    void restoreDefault();
}
