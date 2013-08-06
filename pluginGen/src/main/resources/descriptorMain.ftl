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
<#--
    This file contains the body of the descriptor for a single
    platform/server/service. It is called by descriptor.ftl

-->
name="${props.name}"
            discovery="${props.discoveryClass}"
            class="${props.componentClass}"
            <#if props.singleton>singleton="true"</#if>
            <#if props.manualAddOfResourceType>supportsManualAdd="true"</#if>
            <#if props.createChildren && props.deleteChildren>createDeletePolicy="both"<#elseif props.createChildren && !props.deleteChildren>createDeletePolicy="create-only"<#elseif !props.createChildren && props.deleteChildren>createDeletePolicy="delete-only"<#else > <#-- Dont mention it, as 'neither' is default --></#if>
          >

          <#if props.runsInsides?has_content>
            <runs-inside>
              <#list props.runsInsides as typeKey>
                <parent-resource-type name="${typeKey.name}" plugin="${typeKey.pluginName}"/>
              </#list>
            </runs-inside>
          </#if>
          <#if props.dependsOnAs7Plugin>
          <runs-inside> <!-- TODO adjust type -->
                <parent-resource-type name="JBossAS7 Standalone Server" plugin="JBossAS7"/>
          </runs-inside>
          </#if>

          <#if props.pluginConfig?has_content>
            <plugin-configuration>
                <#list props.pluginConfig as simpleProps>
                <c:simple-property name="${simpleProps.name}" description="${simpleProps.description}" <#if simpleProps.type??>type="${simpleProps.type}"</#if> <#if simpleProps.readOnly>readOnly="true"</#if>/>
                </#list>
                <!-- The template section is only for manual resource additions, and default parameters and the ones presented to the user. -->
                <#list props.templates as templates>
                <c:template name="${templates.name}" description="${templates.description}">
                  <#list templates.simpleProps as innerSimpleProps>
                  <c:simple-property name="${innerSimpleProps.name}" displayName="${innerSimpleProps.displayName}"
                                     defaultValue="${innerSimpleProps.defaultValue}"/>
                  </#list>
                </c:template>
                </#list>
            </plugin-configuration>
         </#if>

     <#if props.hasOperations || props.operations?has_content>
        <#if props.operations?has_content>
           <#list props.operations as operation>
           <operation name="${operation.name}" <#if operation.displayName?has_content>displayName="${operation.displayName}"</#if> description="${operation.description}">
              <#if operation.params?has_content>
              <parameters>
              <#list operation.params as param>
                 <c:simple-property name="${param.name}" <#if param.description??>description="${param.description}"</#if> type="${param.type}"/>
              </#list>
              </parameters>
              </#if>
              <#if operation.result??>
              <results>
                 <c:simple-property name="${operation.result.name}" />
              </results>
              </#if>
           </operation>
           </#list>
        <#else>
           <operation name="dummyOperation">
              <!-- TODO supply parameters and return values -->
           </operation>
        </#if>
     </#if>

     <#if props.hasMetrics || props.metrics?has_content>
        <#if props.metrics?has_content>
           <#list props.metrics as metric>
           <metric property="${metric.property}" <#if metric.displayName?has_content>displayName="${metric.displayName}"</#if> displayType="${metric.displayType}" units="${metric.units}" dataType="${metric.dataType}"
                   description="${metric.description}" />
           </#list>
        <#else>
           <metric property="dummyMetric" displayName="Dummy display name"/>
        </#if>
     </#if>

        <#if props.events>
            <event name="${props.name}DummyEvent"/>
        </#if>
        <#if props.resourceConfiguration>
            <resource-configuration>
                <!-- TODO supply your configuration parameters -->
                <c:simple-property name="dummy"/>
            </resource-configuration>
        </#if>

<#if props.resourceConfig?has_content>
      <resource-configuration>
          <#list props.resourceConfig as simpleProps>
          <c:simple-property name="${simpleProps.name}" description="${simpleProps.description}" <#if simpleProps.type??>type="${simpleProps.type}"</#if> <#if simpleProps.readOnly>readOnly="true"</#if>/>
          </#list>
      </resource-configuration>
</#if>
