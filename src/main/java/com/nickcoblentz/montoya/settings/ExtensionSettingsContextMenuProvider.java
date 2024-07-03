package com.nickcoblentz.montoya.settings;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.ui.contextmenu.AuditIssueContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;
import burp.api.montoya.ui.contextmenu.WebSocketContextMenuEvent;
import com.nickcoblentz.montoya.MontoyaLogger;
import de.milchreis.uibooster.model.Form;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ExtensionSettingsContextMenuProvider implements ContextMenuItemsProvider, ActionListener {

    private MontoyaApi Api;
    private MontoyaLogger Logger;
    private Form SettingsForm;
    private JMenuItem SettingsMenuItem = new JMenuItem("Settings");

    public ExtensionSettingsContextMenuProvider(MontoyaApi api, Form settingsForm) {
        Api = api;
        SettingsForm = settingsForm;
        Logger = new MontoyaLogger(api, MontoyaLogger.DebugLogLevel);
        SettingsMenuItem.addActionListener(this);
    }

    @Override
    public List<Component> provideMenuItems(ContextMenuEvent event) {
        return provideMenuItems();
    }

    @Override
    public List<Component> provideMenuItems(WebSocketContextMenuEvent event) {
        return provideMenuItems();
    }

    @Override
    public List<Component> provideMenuItems(AuditIssueContextMenuEvent event) {
        return provideMenuItems();
    }

    public List<Component> provideMenuItems()
    {
        return List.of(SettingsMenuItem);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SettingsForm.show();
        Logger.debugLog("This fired after showing the form!");

    }
}
