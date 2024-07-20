package com.nickcoblentz.montoya.settings

import de.milchreis.uibooster.UiBooster
import de.milchreis.uibooster.model.*
import java.util.function.Consumer

class ExtensionSettingsFormGenerator(var extensionSettings: List<IExtensionSetting>, private val formName: String) {
    val booster = UiBooster(UiBoosterOptions.Theme.DEFAULT)
    var settingsFormBuilder: FormBuilder = booster.createForm(formName)
        private set

    init {
        buildForm()
    }

    private fun buildForm() {

        settingsFormBuilder.setChangeListener { element: FormElement<*>?, value: Any?, form: Form ->
                form.getById("Change").setValue("Settings have not yet been saved!")
            }

        extensionSettings.forEach(Consumer { s: IExtensionSetting ->
            settingsFormBuilder
                .startRow()
                .addText(s.name, s.currentValue).setID(s.storageKey) /*.addButton("\u27F3", new ButtonClickListener() {
                    @Override
                    public void onClick(FormElement formElement, Form form) {
                        form.getById(s.getStorageKey()).setValue(s.reset());
                    }
                }).setMargin(25,0,0,0)*/
                .endRow()
        })

        settingsFormBuilder
            .startRow().addLabel(" ").setID("Change").setMargin(0, 15, 0, 5).endRow()
            .startRow()
            .addButton(
                "Reset"
            ) { formElement: FormElement<*>?, form: Form ->
                extensionSettings.forEach(
                    Consumer { item: IExtensionSetting ->
                        form.getById(item.storageKey).setValue(item.reset())
                    })
            }
            .addButton("Save") { formElement: FormElement<*>?, form: Form ->
                extensionSettings.forEach(
                    Consumer<IExtensionSetting> { item: IExtensionSetting ->
                        (form.getById(item.storageKey).value as? String)?.let {
                            item.currentValue = it
                            item.save()
                        }
                    })
                form.getById("Change").setValue(" ")
            }
            .addButton(
                "Close"
            ) { formElement: FormElement<*>?, form: Form -> form.close() }
            .endRow()
    }
}