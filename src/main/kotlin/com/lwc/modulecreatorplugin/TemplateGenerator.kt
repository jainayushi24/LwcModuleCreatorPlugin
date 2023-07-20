package com.lwc.modulecreatorplugin

import java.util.*
object TemplateGenerator {
    fun readMeGenerator(): String {
        return "This UI Module is generated using UI Module Code generator tool"
    }

    fun ftestInventoryGenerator(moduleName: String): String {
        return """
            <?xml version="1.0" encoding="UTF-8"?>
            <ftests xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xi="http://www.w3.org/2001/XInclude" name="All functional tests" xsi:noNamespaceSchemaLocation="../../../sfdc-test/func/ftest.xsd">
                <category name="ui-${moduleName.lowercase(Locale.getDefault())}-components">
                </category>
            </ftests>
        """
    }

    fun generateHTMLFile(): String {
        return """
        <template>
            <h1>Hello World</h1>
        </template>
    """.trimIndent().replaceIndent("")
    }


    fun generateJSFile(lwcModuleName: String): String {
        return """
        import { LightningElement } from 'lwc';

        export default class $lwcModuleName extends LightningElement {

        }
    """.trimIndent().replaceIndent("")
    }


    fun springFactory(moduleName: String): String {
        return "<context:component-scan base-package=\"ui." + moduleName.lowercase(Locale.getDefault()) + ".components\" resource-pattern=\"*.class\" />"
    }

    fun apexNamespaceFactory(moduleName: String, namespace: String): String {
        return "public static final ReservedNamespace " + namespace.uppercase(Locale.getDefault()) + " = new AuraComponentNamespace(SfdcInternalNamespaceEnum." + namespace.uppercase(Locale.getDefault()) + ".getName(), \"ui-" + moduleName.lowercase(Locale.getDefault()) + "-components\");"
    }

    fun sfdcInternalNamespaceEnum(namespace: String): String {
        return namespace.uppercase(Locale.getDefault()) + "(\"" + namespace.lowercase(Locale.getDefault()) + "\"),"
    }

    fun auraSfdcNamespaceOwner(namespace: String, teamName: String): String {
        return namespace.uppercase(Locale.getDefault()) + "(\"" + teamName + "\"),"
    }

    fun VintageNamespaceSource(namespace: String): String {
        return namespace.uppercase(Locale.getDefault()) + ","
    }

    fun AuraSfdcNamespace(namespace: String): String {
        return namespace.uppercase(Locale.getDefault()) + "(\"" + namespace.lowercase(Locale.getDefault()) + "\", AuraSfdcNamespaceOwner." + namespace.uppercase(Locale.getDefault()) + ", true, true, false);"
    }
}
