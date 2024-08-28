package com.nickcoblentz.montoya.settings

import de.milchreis.uibooster.UiBooster
import de.milchreis.uibooster.model.Form
import de.milchreis.uibooster.model.FormBuilder
import de.milchreis.uibooster.model.FormElement
import de.milchreis.uibooster.model.UiBoosterOptions
import java.util.function.BiConsumer

class GenericExtensionSettingsFormGenerator(
    private val extensionSettings: List<GenericExtensionSetting<out Any>>,
    private val formName: String
) {
    val booster = UiBooster(UiBoosterOptions.Theme.DEFAULT)
    private var settingsFormBuilder: FormBuilder = booster.createForm(formName)
    private val saveCallbacks: MutableList<BiConsumer<FormElement<Any>, Form>> = mutableListOf()

    init {
        buildForm()
    }

    private fun buildForm() {

        settingsFormBuilder.setChangeListener { element, value, form ->
                if (element.id == null || element.id != "_save") {
                    form.getById("Change").setValue("Settings have not yet been saved! ")
                }
            }

        extensionSettings.forEach { s ->
            /*
            when (s) {
                instanceof GenericExtensionSetting<Boolean> -> (s as? BooleanExtensionSetting)?.let {
                    settingsFormBuilder.startRow()
                        .addCheckbox(it.name, it.currentValue)
                        .setID(s.storageKey)
                        .endRow()
                    }
                }
                is ListStringExtensionSetting -> settingsFormBuilder.startRow()
                    .addTextArea(s.name, s.currentValueAsString())
                    .setId(s.storageKey)
                    .endRow()
                else -> settingsFormBuilder.startRow()
                    .addText(s.name, s.currentValue.toString())
                    .setID(s.storageKey)
                    .endRow()
            }*/

            val booleanExtensionSetting = s as? BooleanExtensionSetting
            val listStringExtensionSetting = s as? ListStringExtensionSetting
            if(booleanExtensionSetting!=null) {
                settingsFormBuilder.startRow()
                    .addCheckbox(booleanExtensionSetting.name, booleanExtensionSetting.currentValue)
                    .setID(s.storageKey)
                    .endRow()
            }
            else if(listStringExtensionSetting!=null ) {
                settingsFormBuilder.startRow()
                    .addTextArea(s.name,10, listStringExtensionSetting.getCurrentValueAsString(), s.isReadOnly)
                    .setID(s.storageKey)
                    .endRow()
            }
            else {
                settingsFormBuilder.startRow()
                    .addText(s.name, s.currentValue.toString(), s.isReadOnly)
                    .setID(s.storageKey)
                    .endRow()
            }
        }

        settingsFormBuilder.startRow()
            .addLabel(" ")
            .setID("_change")
            .setMargin(0, 15, 0, 5)
            .endRow()
            .startRow()
            .addButton("Reset") { formElement, form ->
                extensionSettings.forEach { item ->
                    val element = form.getById(item.storageKey)
                    val booleanExtensionSetting = item as? BooleanExtensionSetting
                    val listStringExtensionSetting = item as? ListStringExtensionSetting
                    if(booleanExtensionSetting!=null) {
                        element.setValue(booleanExtensionSetting.reset()) //.toString().toBoolean()
                    }
                    else if(listStringExtensionSetting!=null) {
                        element.setValue(listStringExtensionSetting.joinListAsString(listStringExtensionSetting.reset()))
                    }
                    else {
                        element.setValue(item.reset().toString())
                    }
                }
            }
            .setID("_reset")
            .addButton("Save") { formElement, form ->
                extensionSettings.forEach { item ->
                    item.setCurrentValue(form.getById(item.storageKey).value.toString())
                    item.save()
                }
                form.getById("_change").setValue("Saved!")
                saveCallbacks.forEach { callback -> callback.accept(formElement, form) }
            }
            .setID("_save")
            .addButton("Close") { formElement, form -> form.close() }
            .endRow()
    }

    fun getSettingsFormBuilder(): FormBuilder {
        return settingsFormBuilder
    }

    fun addSaveCallback(callback: BiConsumer<FormElement<Any>, Form>) {
        saveCallbacks.add(callback)
    }
}