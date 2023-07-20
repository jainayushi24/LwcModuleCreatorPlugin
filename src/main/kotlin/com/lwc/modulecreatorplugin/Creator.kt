package com.lwc.modulecreatorplugin

import java.io.*
import java.util.*
import com.intellij.openapi.application.ApplicationManager

object Creator {
    @Throws(IOException::class)
    fun fileWriter(path: String, content: String) {
        try {
            BufferedWriter(FileWriter(path)).use { writer -> writer.write(content) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    @Throws(IOException::class)
    private fun isEditPresent(filePath: String, edit: String): Boolean {
        val file = File(filePath)
        val fileContent = file.readText()

        return fileContent.contains(edit)
    }
    fun runBashScript(filePath: String) {
        val scriptPath = "/Users/jain.ayushi/IdeaProjects/ModuleCreatorPlugin/src/main/kotlin/com/lwc/modulecreatorplugin/addFilesToGit.sh" // Relative path to the Bash script
        val scriptFile = File(scriptPath)

        if (scriptFile.exists()) {
            ApplicationManager.getApplication().invokeLater {
                val processBuilder = ProcessBuilder("bash", scriptPath, filePath)
                processBuilder.directory(File(".")) // Set the working directory if needed

                val process = processBuilder.start()
                val exitCode = process.waitFor()

                if (exitCode == 0) {
                    println("Bash script executed successfully.")
                    println(process.inputStream.bufferedReader().readText())
                } else {
                    println("Failed to execute Bash script. Exit code: $exitCode")
                }
            }
        } else {
            println("Bash script file does not exist.")
        }

    }


    private fun appendContentAtLastIndexOfIndex(filePath: String, content: String, searchString: String) {
        val file = File(filePath)
        val fileContent = file.readText()

        val lastIndex = fileContent.lastIndexOf(searchString)
        if (lastIndex != -1) {
            val newContent = StringBuilder(fileContent)
                .insert(lastIndex, "\n  $content\n")
                .toString()

            file.writeText(newContent)
            println("Added!")
        } else {
            println("Namespace '$searchString' is already present")
        }
    }


    private fun appendContent(filePath: String, content: String, searchString: String) {
        val file = File(filePath)
        val fileContent = file.readText()

        if (fileContent.isEmpty()) {
            // File is empty, simply write the content to the file
            file.writeText(content)
            println("Template added successfully!")
            return
        }
    }

    @Throws(IOException::class)
    fun overWriteSpringAutoScan(baseFilePath: String, moduleName: String) {
        val filePathCore = baseFilePath + "ui-sfdc/java/resources/configuration/spring-autoscan.xml"
        val index = "</beans>"
        val edit: String = TemplateGenerator.springFactory(moduleName)
        val file = File(filePathCore)
        if (file.exists()) {
            appendContentAtLastIndexOfIndex(filePathCore, edit, index)
        } else {
            throw FileNotFoundException("spring-autoscan.xml file not found.")
        }
    }

    @Throws(IOException::class)
    fun overWriteApexNamespaceFactory(baseFilePath: String, moduleName: String, namespace: String) {
        val filePathCore = baseFilePath + "sfdc/java/src/common/apex/file/ApexNamespaceFactory.java"
        val index = "public static final"
        val edit: String = TemplateGenerator.apexNamespaceFactory(moduleName, namespace)
        val file = File(filePathCore)
        if (file.exists()) {
            appendContentAtLastIndexOfIndex(filePathCore, edit, index)
        } else {
            throw FileNotFoundException("ApexNamespaceFactory.java file not found.")
        }
    }

    @Throws(IOException::class)
    fun overWriteSfdcInternalNamespaceEnum(baseFilePath: String, namespace: String) {
        val edit: String = TemplateGenerator.sfdcInternalNamespaceEnum(namespace)
        val index = "DEFAULT_SYSTEM"
        val filePathCore = baseFilePath + "shared-sfdc-namespace/java/src/sfdc/namespace/SfdcInternalNamespaceEnum.java"
        val file = File(filePathCore)
        if (file.exists()) {
            appendContentAtLastIndexOfIndex(filePathCore, edit, index)
        } else {
            throw FileNotFoundException("SfdcInternalNamespaceEnum.java file not found.")
        }
    }

    @Throws(IOException::class)
    fun overWriteVintageNamespaceSource(baseFilePath: String, namespace: String) {
        val edit: String = TemplateGenerator.VintageNamespaceSource(namespace)
        val index = "AURA_SCL_NAMESPACE,"
        val filePathCore = baseFilePath + "sfdc/java/src/common/apex/file/VintageNamespaceSource.java"
        val file = File(filePathCore)
        if (file.exists()) {
            appendContentAtLastIndexOfIndex(filePathCore, edit, index)
        } else {
            throw FileNotFoundException("VintageNamespaceSource.java file not found.")
        }
    }

    @Throws(IOException::class)
    fun overWriteAuraSfdcNamespaceOwner(baseFilePath: String, namespace: String, teamName: String) {
        val edit: String = TemplateGenerator.auraSfdcNamespaceOwner(namespace, teamName)
        val index = "ACTIVITIES("
        val filePathCore = "$baseFilePath/ui-sfdc/test/func/java/src/ui/aura/components/AuraSfdcNamespaceOwner.java"
        val file = File(filePathCore)
        if (file.exists()) {
            appendContentAtLastIndexOfIndex(filePathCore, edit, index)
        } else {
            throw FileNotFoundException("AuraSfdcNamespaceOwner.java file not found.")
        }
    }

    @Throws(IOException::class)
    fun overWriteAuraSfdcNamespace(baseFilePath: String, namespace: String) {
        val edit: String = TemplateGenerator.AuraSfdcNamespace(namespace)
        val index = "private boolean isProdNamespace;"
        val filePathCore = "$baseFilePath/ui-sfdc/test/func/java/src/ui/aura/components/AuraSfdcNamespace.java"
        val file = File(filePathCore)
        if (file.exists()) {
            appendContentAtLastIndexOfIndex(filePathCore, edit, index)
        } else {
            throw FileNotFoundException("AuraSfdcNamespace.java file not found.")
        }
    }

    @Throws(IOException::class)
    fun generateFiles(baseFilePath: String, moduleName: String) {
        val modulePath = baseFilePath + "ui-" + moduleName.lowercase(Locale.getDefault()) + "-component"
        var file: File
        file = File(modulePath)
        if (file.exists()) {
            println("Module directory already exists. Exiting...")
            return
        }
        file.mkdir()

        // Creating README.md
        val readMeFilePath = "$modulePath/build/README.md"
        file = File(readMeFilePath)
        file.parentFile.mkdirs()
        file.createNewFile()
        // Updating README.md
        fileWriter(readMeFilePath, TemplateGenerator.readMeGenerator())
        file = File("$modulePath/components")
        file.mkdir()
        file = File("$modulePath/module")
        file.mkdir()
        file = File(modulePath + "/java/src/ui/" + moduleName + "/components/aura/configuration/UI" + moduleName.substring(0, 1).uppercase(Locale.getDefault()) + moduleName.substring(1) + "ComponentsComponentLocationAdapter.java")
        file.parentFile.mkdirs()
        file.createNewFile()
        file = File(modulePath + "/java/src/ui/" + moduleName + "/components/configuration/UI" + moduleName.substring(0, 1).uppercase(Locale.getDefault()) + moduleName.substring(1) + "ComponentsAutoConfiguration.java")
        file.parentFile.mkdirs()
        file.createNewFile()
        file = File("$modulePath/resources/META-INF/spring.factories")
        file.parentFile.mkdirs()
        file.createNewFile()

        // Creating ftest-inventory.xml
        val ftestInventoryPath = "$modulePath/test/func/ftest-inventory.xml"
        file = File(ftestInventoryPath)
        file.parentFile.mkdirs()
        file.createNewFile()
        // Updating ftest-inventory.xml
        fileWriter(ftestInventoryPath, TemplateGenerator.ftestInventoryGenerator(moduleName))
        file = File("$modulePath/test/func/BUILD.bazel")
        file.createNewFile()
        file = File("$modulePath/test/unit/BUILD.bazel")
        file.parentFile.mkdirs()
        file.createNewFile()
        file = File("$modulePath/BUILD.bazel")
        file.createNewFile()
        overWriteSpringAutoScan(baseFilePath, moduleName)
        println("Module creation is successfull!!!")
    }

    @Throws(IOException::class)
    fun createLWCModule(baseFilePath: String, moduleName: String, namespace: String, lwcModuleName: String) {
        val filePath = baseFilePath + "ui-" + moduleName.lowercase(Locale.getDefault()) + "-component"
        val path = "$filePath/module/$namespace/$lwcModuleName"
        var file = File(path)
        if (file.exists()) {
            println("LWC module already exists. Exiting...")
            return
        }
        file.mkdir()
        file = File("$path/$lwcModuleName.html")
        file.createNewFile()
//        editFile("$path/$lwcModuleName.html", TemplateGenerator.generateHTMLFile(), "")
        appendContent("$path/$lwcModuleName.html", TemplateGenerator.generateHTMLFile(), "")
        file = File("$path/$lwcModuleName.css")
        file.createNewFile()
        file = File("$path/$lwcModuleName.js")
        file.createNewFile()
//        editFile("$path/$lwcModuleName.js", TemplateGenerator.generateJSFile(lwcModuleName), "")
        appendContent("$path/$lwcModuleName.js", TemplateGenerator.generateJSFile(lwcModuleName), "")
        println("LWC component is created")
        return
    }

    @Throws(IOException::class)
    fun createNamespace(baseFilePath: String, moduleName: String, namespace: String, teamName: String) {
        val modulePath = baseFilePath + "ui-" + moduleName.lowercase(Locale.getDefault()) + "-component"
        val modulePathFile = File(modulePath)
        if (!modulePathFile.exists()) {
            println("Module $moduleName does not exist. Exiting...")
            return
        }
        val file = File(modulePath + "/module" + "/" + namespace.lowercase(Locale.getDefault()))
        if (file.exists()) {
            println("Namespace already exists. Exiting...")
            return
        }
        val filePathCore = baseFilePath + "sfdc/java/src/common/apex/file/VintageNamespaceSource.java"
        val edit: String = TemplateGenerator.VintageNamespaceSource(namespace)
        if (isEditPresent(filePathCore, edit)) {
            println("Namespace already exists. Exiting...")
            return
        }
        file.mkdir()
        overWriteVintageNamespaceSource(baseFilePath, namespace)
        overWriteApexNamespaceFactory(baseFilePath, moduleName, namespace)
        overWriteSfdcInternalNamespaceEnum(baseFilePath, namespace)
        overWriteAuraSfdcNamespaceOwner(baseFilePath, namespace, teamName)
        overWriteAuraSfdcNamespace(baseFilePath, namespace)
        println("Namespace is successfully created.")
    }

    @JvmStatic
    fun main(args: Array<String>) {
    }
}