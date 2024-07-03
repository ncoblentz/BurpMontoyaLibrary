package com.nickcoblentz.montoya.ui;

import de.milchreis.uibooster.UiBooster;
import de.milchreis.uibooster.model.UiBoosterOptions;

public class UIHelper {

    public static void PopUpNotification(String title, String message)
    {
        new UiBooster(UiBoosterOptions.Theme.DEFAULT).createNotification(message, title);
    }
}
