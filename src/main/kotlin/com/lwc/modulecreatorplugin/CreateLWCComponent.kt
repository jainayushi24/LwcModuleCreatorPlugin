package com.lwc.modulecreatorplugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages
import java.util.*

class CreateLWCComponent: AnAction() {
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

        val namespace = Messages.showInputDialog("Enter the namespace", "Namespace", null)
        if (namespace.isNullOrBlank()) {
            Messages.showErrorDialog("Namespace is required.", "Error")
            return
        }else if (namespace.length > 30) {
            Messages.showErrorDialog("Namespace cannot exceed 30 characters.", "Error")
            return
        }

        val lwcModuleName = Messages.showInputDialog("Enter the LWC Module name", "LWC Module Name", null)
        if (lwcModuleName.isNullOrBlank()) {
            Messages.showErrorDialog("LWC Module Name is required.", "Error")
            return
        }else if (lwcModuleName.length > 30) {
            Messages.showErrorDialog("LWC Module name cannot exceed 30 characters.", "Error")
            return
        }

        Creator.createLWCModule(baseDirectoryPath ,moduleName, namespace, lwcModuleName)

        val filePath = baseDirectoryPath + "ui-" + moduleName.lowercase(Locale.getDefault()) + "-component"
        val path = "$filePath/module/$namespace/$lwcModuleName"

        Creator.runBashScript(path)
    }
}