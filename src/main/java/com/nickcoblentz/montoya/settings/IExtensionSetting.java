package com.nickcoblentz.montoya.settings;

public interface IExtensionSetting {
    String getCurrentValue();

    void setCurrentValue(String currentValue);

    void saveToProject();

    void loadFromProject();

    void saveToPreferences();

    void loadFromPreferences();

    void save();
    void load();

    String getName();

    String getStorageKey();

    String reset();
}

