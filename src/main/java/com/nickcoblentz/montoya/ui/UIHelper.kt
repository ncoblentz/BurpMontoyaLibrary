package com.nickcoblentz.montoya.ui

import de.milchreis.uibooster.UiBooster
import de.milchreis.uibooster.model.UiBoosterOptions
import java.io.File
import de.milchreis.uibooster.components.FilesystemDialog
import javax.swing.JFileChooser
import de.milchreis.uibooster.utils.WindowIconHelper
import javax.swing.JFrame

class UIHelper {

    val uibooster = UiBooster(UiBoosterOptions.Theme.DEFAULT)

    public fun popUpNotification(title: String?, message: String?) {
        uibooster.createNotification(message, title)
    }

    public fun showFileMultiSelectionDialog() : Array<File> {
        val frameWithIcon = JFrame()
        //frameWithIcon.setIconImage(uibooster. WindowIconHelper.getIcon(uibooster.options.iconPath).image)

        val chooser = JFileChooser()
        chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY)
        chooser.setMultiSelectionEnabled(true)

        val returnVal = chooser.showOpenDialog(frameWithIcon)

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFiles()
        }

        return emptyArray<File>()
    }

}