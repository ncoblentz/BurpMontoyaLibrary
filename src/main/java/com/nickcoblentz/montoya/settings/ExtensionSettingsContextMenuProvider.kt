package com.nickcoblentz.montoya.settings

import burp.api.montoya.MontoyaApi
import burp.api.montoya.ui.contextmenu.AuditIssueContextMenuEvent
import burp.api.montoya.ui.contextmenu.ContextMenuEvent
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider
import burp.api.montoya.ui.contextmenu.WebSocketContextMenuEvent
import com.nickcoblentz.montoya.LogLevel
import com.nickcoblentz.montoya.MontoyaLogger
import de.milchreis.uibooster.model.Form
import java.awt.Component
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.JMenuItem

class ExtensionSettingsContextMenuProvider(private val api: MontoyaApi, private val settingsForm: Form) :
    ContextMenuItemsProvider, ActionListener {
    //Logger = new MontoyaLogger(api, MontoyaLogger.DebugLogLevel);
    private val logger = MontoyaLogger(api, LogLevel.DEBUG)
    private val settingsMenuItem = JMenuItem("Settings")

    init {
        settingsMenuItem.addActionListener(this)
    }

    override fun provideMenuItems(event: ContextMenuEvent): List<Component> {
        return provideMenuItems()
    }

    override fun provideMenuItems(event: WebSocketContextMenuEvent): List<Component> {
        return provideMenuItems()
    }

    override fun provideMenuItems(event: AuditIssueContextMenuEvent): List<Component> {
        return provideMenuItems()
    }

    fun provideMenuItems(): List<Component> {
        return listOf(settingsMenuItem)
    }

    override fun actionPerformed(e: ActionEvent) {
        settingsForm.show()
        logger.debugLog("This fired after showing the form!")
    }
}