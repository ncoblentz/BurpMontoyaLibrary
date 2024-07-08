package com.nickcoblentz.montoya.settings;

import burp.api.montoya.MontoyaApi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStringExtensionSetting extends GenericExtensionSetting<List<String>> {


    public ListStringExtensionSetting(MontoyaApi api, String name, String storageKey, List<String> defaultValue, ExtensionSettingSaveLocation defaultSaveLocation) {
        super(api, name, storageKey, defaultValue, defaultSaveLocation);
    }

    @Override
    public void loadFromProject() {
        setCurrentValue(parseStringAsList(getApi().persistence().extensionData().getString(getStorageKey())));
    }

    @Override
    public void loadFromPreferences() {
        setCurrentValue(parseStringAsList(getApi().persistence().preferences().getString(getStorageKey())));
    }

    @Override
    public void saveToProject() {
        getApi().persistence().extensionData().setString(getStorageKey(),joinListAsString(getCurrentValue()));
    }

    @Override
    public void saveToPreferences() {
        getApi().persistence().preferences().setString(getStorageKey(),joinListAsString(getCurrentValue()));
    }

    public List<String> parseStringAsList(String values)
    {
        if(values!=null && !values.isEmpty())
            return Arrays.asList(values.split("\n"));
        return new ArrayList<String>();
    }

    public String joinListAsString(List<String> values)
    {
        if(values!=null)
            return String.join("\n",values);
        return "";
    }

    @Override
    public void setCurrentValue(String value) {
        setCurrentValue(parseStringAsList(value));
    }

    @Override
    public List<String> parseString(String value) {
        return parseStringAsList(value);
    }

    public String GetCurrentValueAsString()
    {
        return joinListAsString(getCurrentValue());
    }
}
