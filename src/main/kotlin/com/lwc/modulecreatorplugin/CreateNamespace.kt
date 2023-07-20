package com.lwc.modulecreatorplugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

class CreateNamespace: AnAction() {
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

        val teamName = Messages.showInputDialog("Enter the team name", "Team Name", null)
        if (teamName.isNullOrBlank()) {
            Messages.showErrorDialog("Team name is required.", "Error")
            return
        }else if (teamName.length > 50) {
            Messages.showErrorDialog("Team name cannot exceed 50 characters.", "Error")
            return
        }

        Creator.createNamespace(baseDirectoryPath, moduleName, namespace, teamName)
    }
}