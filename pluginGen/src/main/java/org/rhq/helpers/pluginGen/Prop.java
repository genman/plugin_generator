/*
 * RHQ Management Platform
 *  Copyright [2005-2013] [Red Hat, Inc]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.rhq.helpers.pluginGen;

import java.io.File;

/**
 * All the properties that can be configured along with type, description and simple validation rules
 * @author Heiko W. Rupp
 */
public enum Prop {

    PLUGIN_NAME("pluginName", String.class,"Name of the plugin", "\\w+" , true ),
    PLUGIN_DESCRIPTION("pluginDescription", String.class,"Description of the plugin",".*" , true ),
    PACKAGE("packagePrefix", String.class,"Default Package","[a-zA-Z\\.]+",true ),
    FILE_ROOT("fileSystemRoot", File.class,"Root directory to put the plugin into",".*",true , true,null),
    RHQ_VERSION("rhqVersion",String.class,"RHQ version to use","[0-9][0-9\\.]+",true),

    CATEGORY("category", ResourceCategory.class, "Category of the resource type (platform = host level)",null),
    TYPE_NAME("name", String.class, "Name of the resource type", "\\w+"),
    DESCRIPTION("description", String.class, "Description of the type", ".*"),
    DISCOVERY_CLASS("discoveryClass", String.class, "Name of the Discovery class. '{name}' will be replaced with the type name", "[A-Z][a-zA-Z0-9]*",false,false,"{name}Discovery"),
    COMPONENT_CLASS("componentClass", String.class, "Name of the Discovery class. '{name}' will be replaced with the type name", "[A-Z][a-zA-Z0-9]*",false,false,"{name}Component"),
    IS_SINGLETON("singleton",boolean.class,"Is this type a singleton, which means that" +
        " there can only be one resource of that type for the given parent?"),
    HAS_METRICS("hasMetrics",boolean.class,"Does this type support taking metrics?"),
    HAS_OPERATIONS("hasOperations",boolean.class,"Does this type support operations?"),
    HAS_EVENTS("events",boolean.class,"Does this type support events?"),
    HAS_SUPPORT_FACET("supportFacet",boolean.class,"Does this type support the support facet?"),
    RESOURCE_CONFIGURATION("resourceConfiguration",boolean.class,"Does this type support " +
        "configuring the resource?"),
    CAN_CREATE_CHILDREN("createChildren",boolean.class,"Can the type create child resources?"),
    CAN_DELETE_CHILDREN("deleteChildren",boolean.class,"Can the type delete child resources?"),
    USE_EXTENAL_JARS("usesExternalJarsInPlugin",boolean.class,"Will the plugin use external jars in the plugin jar?"),
    ALLOW_MANUAL_ADD("manualAddOfResourceType",boolean.class,"Should manually adding resource be supported?"),
    USE_LIFECYLE_API("usePluginLifecycleListenerApi",boolean.class,"Should the plugin lifecycle api be supported?"),
    DEPENDS_ON_JMX_PLUGIN("dependsOnJmxPlugin",boolean.class,"Does the plugin use JMX and extend the JMX Plugin?"),
    DEPENDS_ON_AS7_PLUGIN("dependsOnAs7Plugin",boolean.class,"Does the plugin use DMR and extend the AS7 Plugin?"),
    USE_SUPPORT_FACET("supportFacet",boolean.class,"Will the support facet be used?"),

    SCAN_FOR_ANNOTATIONS("scanForAnnotations",File.class,"Directory to scan for plugin annotations to include in type",null, false,false, null)
    ;

    private String variableName;
    private Class type;
    private String description;
    private boolean pluginLevel;
    private boolean directoryWriteable;
    private String defaultValue;
    private String validationRegex;

    private Prop(String variableName, Class type, String description, String validationRegex, boolean pluginLevel, boolean directoryWriteable, String defaultValue) {
        this.variableName = variableName;
        this.type = type;
        this.description = description;
        this.validationRegex = validationRegex;
        this.pluginLevel = pluginLevel;
        this.directoryWriteable = directoryWriteable;
        this.defaultValue = defaultValue;
    }

    private Prop(String variableName, Class type, String description, String validationRegex, boolean pluginLevel) {
        this(variableName,type,description,validationRegex,pluginLevel,false,null);
    }

    private Prop(String variableName, Class type, String description, String validationRegex) {
        this(variableName,type,description,validationRegex,false,false,null);
    }

    private Prop(String variableName, Class type, String description) {
        this(variableName,type,description,null,false,false,null);
    }

    public String getVariableName() {
        return variableName;
    }

    public Class getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPluginLevel() {
        return pluginLevel;
    }

    public String getValidationRegex() {
        return validationRegex;
    }

    public String readableName() {

        String name = name();
        name = name.replaceAll("_", " ");
        String[] parts = name.split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            part = part.substring(0,1).toUpperCase() + part.substring(1).toLowerCase();
            builder.append(part);
            if (i < parts.length-1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    public boolean isDirectoryWriteable() {
        return directoryWriteable;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
