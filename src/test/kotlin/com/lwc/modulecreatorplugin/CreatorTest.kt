package com.lwc.modulecreatorplugin

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

class CreatorTest {


    @Test
    fun generateFiles() {

        val baseFilePath = "/Users/jain.ayushi/core-public/core/"
        val moduleName = "test13"

        // Invoke the function
        Creator.generateFiles(baseFilePath, moduleName)

        // Verify the generated files
        val modulePath = baseFilePath + "ui-" + moduleName.lowercase() + "-component"
        val moduleDir = File(modulePath)
        assertTrue(moduleDir.exists())
        assertTrue(moduleDir.isDirectory())

        val readmeFile = File("$modulePath/build/README.md")
        assertTrue(readmeFile.exists())

        val componentsDir = File("$modulePath/components")
        assertTrue(componentsDir.exists())
        assertTrue(componentsDir.isDirectory())

        val moduleFile = File("$modulePath/module")
        assertTrue(moduleFile.exists())
        assertTrue(moduleFile.isDirectory())

        val locationAdapterFile = File(modulePath + "/java/src/ui/" + moduleName + "/components/aura/configuration/UI" + moduleName.substring(0, 1).uppercase() + moduleName.substring(1) + "ComponentsComponentLocationAdapter.java")
        assertTrue(locationAdapterFile.exists())

        val autoConfigurationFile = File(modulePath + "/java/src/ui/" + moduleName + "/components/configuration/UI" + moduleName.substring(0, 1).uppercase() + moduleName.substring(1) + "ComponentsAutoConfiguration.java")
        assertTrue(autoConfigurationFile.exists())

        val springFactoriesFile = File("$modulePath/resources/META-INF/spring.factories")
        assertTrue(springFactoriesFile.exists())

        val ftestInventoryFile = File("$modulePath/test/func/ftest-inventory.xml")
        assertTrue(ftestInventoryFile.exists())

        val buildBazelFile = File("$modulePath/test/func/BUILD.bazel")
        assertTrue(buildBazelFile.exists())

        val unitBuildBazelFile = File("$modulePath/test/unit/BUILD.bazel")
        assertTrue(unitBuildBazelFile.exists())

        val rootBuildBazelFile = File("$modulePath/BUILD.bazel")
        assertTrue(rootBuildBazelFile.exists())

        // Cleanup: Delete the generated files and directories
        moduleDir.deleteRecursively()
    }

//    @Test
//    fun createLWCModule() {
//        val baseFilePath = "/Users/jain.ayushi/core-public/core/"
//        val moduleName = "TestModule"
//        val namespace = "myNamespace"
//        val lwcModuleName = "TestLWC"
//
//        // Call the function being tested
//        Creator.createLWCModule(baseFilePath, moduleName, namespace, lwcModuleName)
//
//        // Verify the created files and folders
//        val filePath = baseFilePath + "ui-" + moduleName.lowercase(Locale.getDefault()) + "-component"
//        val path = "$filePath/module/$namespace/$lwcModuleName"
//        val moduleDir = File(path)
//        val htmlFile = File("$path/$lwcModuleName.html")
//        val cssFile = File("$path/$lwcModuleName.css")
//        val jsFile = File("$path/$lwcModuleName.js")
//
//        assertTrue(moduleDir.exists(), "Module directory should be created")
//        assertTrue(htmlFile.exists(), "HTML file should be created")
//        assertTrue(cssFile.exists(), "CSS file should be created")
//        assertTrue(jsFile.exists(), "JS file should be created")
//
//        // Clean up the created files and folders (optional)
//        htmlFile.delete()
//        cssFile.delete()
//        jsFile.delete()
//        moduleDir.delete()
//    }


}