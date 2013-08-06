<#--
  ~ RHQ Management Platform
  ~  Copyright [2005-2013] [Red Hat, Inc]
  ~
  ~    Licensed under the Apache License, Version 2.0 (the "License");
  ~    you may not use this file except in compliance with the License.
  ~    You may obtain a copy of the License at
  ~
  ~        http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~    Unless required by applicable law or agreed to in writing, software
  ~    distributed under the License is distributed on an "AS IS" BASIS,
  ~    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~    See the License for the specific language governing permissions and
  ~    limitations under the License.
-->
<#-- @ftlvariable name="props" type="org.rhq.helpers.pluginGen.Props" -->
<?xml version="1.0"?>
<plugin name="${props.pluginName}"
        displayName="${props.pluginName}Plugin"
<#if props.pluginDescription??>
        description="${props.pluginDescription}"
<#else>
        description="TODO provide a description for the plugin"
</#if>
<#if props.usePluginLifecycleListenerApi>
        pluginLifecycleListener="${props.componentClass}"
</#if>
        package="${props.pkg}"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="urn:xmlns:rhq-plugin"
        xmlns:c="urn:xmlns:rhq-configuration">

<#if props.dependsOnJmxPlugin>
   <depends plugin="JMX" useClasses="true"/>
</#if>
<#if props.dependsOnAs7Plugin>
   <depends plugin="JBossAS7" useClasses="true"/>
</#if>

   <${props.category.lowerName} <#include "descriptorMain.ftl"/>

   <#-- Those are the embedded children -->
   <#list props.children as props>
       <${props.category.lowerName} <#include "./descriptorMain.ftl"/>
       </${props.category.lowerName}>
   </#list>
   </${props.category.lowerName}>

</plugin>