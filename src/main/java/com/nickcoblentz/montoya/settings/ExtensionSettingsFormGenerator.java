package com.nickcoblentz.montoya.settings;

import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.*;

import java.util.List;

public class ExtensionSettingsFormGenerator {
    private final String FormName;
    List<IExtensionSetting> ExtensionSettings;
    private FormBuilder SettingsFormBuilder;

    public ExtensionSettingsFormGenerator(List<IExtensionSetting> extensionSettings, String formName) {
        ExtensionSettings = extensionSettings;
        FormName=formName;
        buildForm();
    }

    private void buildForm()
    {
        UiBooster booster = new UiBooster(UiBoosterOptions.Theme.DEFAULT);
        SettingsFormBuilder = booster.createForm(FormName)
            .setChangeListener((element, value, form) -> {
                form.getById("Change").setValue("Settings have not yet been saved!");
            });

        ExtensionSettings.forEach((s) -> {
            SettingsFormBuilder
                    .startRow()
                    .addText(s.getName(),s.getCurrentValue()).setID(s.getStorageKey())
                    /*.addButton("\u27F3", new ButtonClickListener() {
                        @Override
                        public void onClick(FormElement formElement, Form form) {
                            form.getById(s.getStorageKey()).setValue(s.reset());
                        }
                    }).setMargin(25,0,0,0)*/
                    .endRow();
                });

            SettingsFormBuilder
                .startRow().addLabel(" ").setID("Change").setMargin(0,15,0,5).endRow()
                .startRow()
                .addButton("Reset", new ButtonClickListener() {
                    @Override
                    public void onClick(FormElement formElement, Form form) {
                        ExtensionSettings.forEach((item) ->{
                            form.getById(item.getStorageKey()).setValue(item.reset());
                        });
                    }
                })
                .addButton("Save", new ButtonClickListener() {
                    @Override
                    public void onClick(FormElement formElement, Form form) {
                        ExtensionSettings.forEach((item) ->{
                            if(form.getById(item.getStorageKey()).getValue() instanceof String sValue)
                            {
                                item.setCurrentValue(sValue);
                                item.save();
                            }
                        });
                        form.getById("Change").setValue(" ");
                    }
                })
                .addButton("Close", new ButtonClickListener() {
                    @Override
                    public void onClick(FormElement formElement, Form form) {
                        form.close();
                    }
                })
            .endRow();
    }

    public FormBuilder getSettingsFormBuilder() {
        return SettingsFormBuilder;
    }
}
