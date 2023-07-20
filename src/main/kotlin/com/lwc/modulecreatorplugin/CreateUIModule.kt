package com.lwc.modulecreatorplugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import java.util.*


class CreateUIModule: AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val baseDirectoryPath = Messages.showInputDialog("Enter the base directory path for core folder", "Base Directory Path", null)
        if (baseDirectoryPath.isNullOrBlank()) {
            Messages.showErrorDialog("Base directory path is required.", "Error")
            return
        }

        val moduleName = Messages.showInputDialog("Enter the module name", "Module Name", null)
        if (moduleName.isNullOrBlank()) {
            Messages.showErrorDialog("Module name is required.", "Error")
            return
        }else if (moduleName.length > 30) {
            Messages.showErrorDialog("Module name cannot exceed 30 characters.", "Error")
            return
        }

        Creator.generateFiles(baseDirectoryPath, moduleName)
        Creator.runBashScript(baseDirectoryPath + "ui-" + moduleName.lowercase(Locale.getDefault()) + "-component")
    }
}