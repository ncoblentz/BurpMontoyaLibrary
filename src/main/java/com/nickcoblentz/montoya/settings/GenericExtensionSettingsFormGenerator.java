package com.nickcoblentz.montoya.settings;

import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.Form;
import de.milchreis.uibooster.model.FormBuilder;
import de.milchreis.uibooster.model.FormElement;
import de.milchreis.uibooster.model.UiBoosterOptions;
import de.milchreis.uibooster.model.formelements.CheckboxFormElement;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class GenericExtensionSettingsFormGenerator {
    private final String FormName;
    List<GenericExtensionSetting> ExtensionSettings;
    private FormBuilder SettingsFormBuilder;
    List<BiConsumer<FormElement, Form>> saveCallbacks=new LinkedList<>();

    public GenericExtensionSettingsFormGenerator(List<GenericExtensionSetting> extensionSettings, String formName) {
        ExtensionSettings = extensionSettings;
        FormName=formName;
        buildForm();
    }

    private void buildForm()
    {
        UiBooster booster = new UiBooster(UiBoosterOptions.Theme.DEFAULT);
        SettingsFormBuilder = booster.createForm(FormName)
            .setChangeListener((element, value, form) -> {
                if(element.getId()==null || element.getId()!="_save")
                    form.getById("Change").setValue("Settings have not yet been saved! ");
            });

        ExtensionSettings.forEach((s) -> {
            if(s instanceof BooleanExtensionSetting bSetting)
                SettingsFormBuilder.startRow().addCheckbox(s.getName(),bSetting.getCurrentValue().booleanValue()).setID(s.getStorageKey()).endRow();
            else
                SettingsFormBuilder.startRow().addText(s.getName(),s.getCurrentValue().toString()).setID(s.getStorageKey()).endRow();
        });

            SettingsFormBuilder
                .startRow().addLabel(" ").setID("Change").setMargin(0,15,0,5).endRow()
                .startRow()
                .addButton("Reset", (formElement, form) -> ExtensionSettings.forEach((item) -> {
                    var element = form.getById(item.getStorageKey());
                    if(element instanceof CheckboxFormElement checkbox)
                        checkbox.setValue(Boolean.parseBoolean(item.reset().toString()));
                    else
                        element.setValue(item.reset().toString());
                })).setID("_reset")
                .addButton("Save", (formElement, form) -> {
                    ExtensionSettings.forEach((item) ->{

                        item.setCurrentValue(form.getById(item.getStorageKey()).getValue().toString());
                        item.save();
                        saveCallbacks.forEach(callback -> callback.accept(formElement,form));
                    });
                    form.getById("Change").setValue("Saved!");
                }).setID("_save")
                .addButton("Close", (formElement, form) -> form.close())
            .endRow();
    }

    public FormBuilder getSettingsFormBuilder() {
        return SettingsFormBuilder;
    }

    public void addSaveCallback(BiConsumer<FormElement, Form> callback)
    {
        saveCallbacks.add(callback);
    }
}
