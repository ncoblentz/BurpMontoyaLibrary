package com.nickcoblentz.montoya.settings

import burp.api.montoya.extension.ExtensionUnloadingHandler
import de.milchreis.uibooster.model.Form

class ExtensionSettingsUnloadHandler(private val settingsForm: Form) : ExtensionUnloadingHandler {

    override fun extensionUnloaded() {
        settingsForm.hide().close()
    }
}