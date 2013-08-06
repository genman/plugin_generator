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

package ${props.pkg};

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.rhq.core.domain.configuration.Configuration;
import org.rhq.core.pluginapi.inventory.InvalidPluginConfigurationException;
import org.rhq.core.domain.measurement.AvailabilityType;
<#if props.metrics??>
import org.rhq.core.domain.measurement.MeasurementDataNumeric;
import org.rhq.core.domain.measurement.MeasurementReport;
import org.rhq.core.domain.measurement.MeasurementScheduleRequest;
</#if>
<#if props.resourceConfiguration>
import org.rhq.core.pluginapi.configuration.ConfigurationFacet;
import org.rhq.core.pluginapi.configuration.ConfigurationUpdateReport;
</#if>
<#if props.createChildren>
import org.rhq.core.pluginapi.inventory.CreateChildResourceFacet;
import org.rhq.core.pluginapi.inventory.CreateResourceReport;
</#if>
<#if props.deleteChildren>
import org.rhq.core.pluginapi.inventory.DeleteResourceFacet;
</#if>
import org.rhq.core.pluginapi.inventory.ResourceComponent;
import org.rhq.core.pluginapi.inventory.ResourceContext;
<#if props.metrics??>
import org.rhq.core.pluginapi.measurement.MeasurementFacet;
</#if>
<#if props.events>
import org.rhq.core.pluginapi.event.EventContext;
</#if>
<#if props.operations??>
import org.rhq.core.pluginapi.operation.OperationContext;
import org.rhq.core.pluginapi.operation.OperationFacet;
import org.rhq.core.pluginapi.operation.OperationResult;
</#if>
<#if props.usePluginLifecycleListenerApi>
import org.rhq.core.pluginapi.plugin.PluginContext;
import org.rhq.core.pluginapi.plugin.PluginLifecycleListener;
</#if>
<#if props.supportFacet>
import org.rhq.core.pluginapi.support.SnapshotReportRequest;
import org.rhq.core.pluginapi.support.SnapshotReportResults;
import org.rhq.core.pluginapi.support.SupportFacet;
</#if>


public class ${props.componentClass} implements ResourceComponent<#if props.parentType??><${props.parentType}></#if>
<#if props.metrics??>
, MeasurementFacet
</#if>
<#if props.operations??>
, OperationFacet
</#if>
<#if props.resourceConfiguration>
, ConfigurationFacet
</#if>
<#if props.createChildren>
, CreateChildResourceFacet
</#if>
<#if props.deleteChildren>
, DeleteResourceFacet
</#if>
<#if props.usePluginLifecycleListenerApi>
, PluginLifecycleListener
</#if>
<#if props.supportFacet>
, SupportFacet
</#if>
{
    private final Log log = LogFactory.getLog(this.getClass());

    private static final int CHANGEME = 1; // TODO remove or change this

    private ResourceContext<#if props.parentType??><${props.parentType}></#if> context;


    <#if props.events>
    public static final String DUMMY_EVENT = "${props.name}DummyEvent"; // Same as in Plugin-Descriptor

    EventContext eventContext;
    </#if>

    <#if props.usePluginLifecycleListenerApi>
    /**
     * Callback when the plugin is created
     * @see org.rhq.core.pluginapi.plugin.PluginLifecycleListener#initialize(PluginContext)
     */
    public void initialize(PluginContext context) throws Exception
    {
    }

    /**
     * Callback when the plugin is unloaded
     * @see org.rhq.core.pluginapi.plugin.PluginLifecycleListener#shutdown()
     */
    public void shutdown()
    {
    }
    </#if>

    /**
     * Return availability of this resource
     *  @see org.rhq.core.pluginapi.inventory.ResourceComponent#getAvailability()
     */
    public AvailabilityType getAvailability() {
        // TODO supply real implementation
        return AvailabilityType.UP;
    }


    /**
     * Start the resource connection
     * @see org.rhq.core.pluginapi.inventory.ResourceComponent#start(org.rhq.core.pluginapi.inventory.ResourceContext)
     */
    public void start(ResourceContext<#if props.parentType??><${props.parentType}></#if> context) throws InvalidPluginConfigurationException, Exception {

        this.context = context;
        Configuration conf = context.getPluginConfiguration();
        // TODO add code to start the resource / connection to it

        <#if props.events>
        eventContext = context.getEventContext();
        ${props.name?cap_first}EventPoller eventPoller = new ${props.name?cap_first}EventPoller();
        eventContext.registerEventPoller(eventPoller, 60);
        </#if>

    }


    /**
     * Tear down the resource connection
     * @see org.rhq.core.pluginapi.inventory.ResourceComponent#stop()
     */
    public void stop() {


        <#if props.events>
        eventContext.unregisterEventPoller(DUMMY_EVENT);
        </#if>
    }


<#if props.metrics??>

    /**
     * Gather measurement data
     *  @see org.rhq.core.pluginapi.measurement.MeasurementFacet#getValues(org.rhq.core.domain.measurement.MeasurementReport, java.util.Set)
     */
    public  void getValues(MeasurementReport report, Set<MeasurementScheduleRequest> metrics) throws Exception {

         for (MeasurementScheduleRequest req : metrics) {
            if (req.getName().equals("dummyMetric")) {
                 MeasurementDataNumeric res = new MeasurementDataNumeric(req, Double.valueOf(CHANGEME));
                 report.addData(res);
            }
            // TODO add more metrics here
         }
    }
</#if>

<#if props.operations??>

    public void startOperationFacet(OperationContext context) {

    }


    /**
     * Invokes the passed operation on the managed resource
     * @param name Name of the operation
     * @param params The method parameters
     * @return An operation result
     * @see org.rhq.core.pluginapi.operation.OperationFacet
     */
    public OperationResult invokeOperation(String name, Configuration params) throws Exception {

        OperationResult res = new OperationResult();
<#if props.operations?has_content>
    <#list props.operations as operation>
        if (name.equals("${operation.name}") {
            // TODO implement me
        }
    </#list>
<#else>
        if ("dummyOperation".equals(name)) {
            // TODO implement me

        }
        return res;
    }
</#if>
</#if>


<#if props.resourceConfiguration>
    /**
     * Load the configuration from a resource into the configuration
     * @return The configuration of the resource
     * @see org.rhq.core.pluginapi.configuration.ConfigurationFacet
     */
    public Configuration loadResourceConfiguration()
    {
        // TODO supply code to load the configuration from the resource into the plugin
        return null;
    }

    /**
     * Write down the passed configuration into the resource
     * @param report The configuration updated by the server
     * @see org.rhq.core.pluginapi.configuration.ConfigurationFacet
     */
    public void updateResourceConfiguration(ConfigurationUpdateReport report)
    {
        // TODO supply code to update the passed report into the resource
    }
</#if>

<#if props.createChildren>
    /**
     * Create a child resource
     * @see org.rhq.core.pluginapi.inventory.CreateChildResourceFacet
     */
    public CreateResourceReport createResource(CreateResourceReport report)
    {
        // TODO supply code to create a child resource

        return null; // TODO change this
    }
</#if>

<#if props.deleteChildren>
    /**
     * Delete a child resource
     * @see org.rhq.core.pluginapi.inventory.DeleteResourceFacet
     */
    public void deleteResource() throws Exception
    {
        // TODO supply code to delete a child resource
    }
</#if>

<#if props.supportFacet>
    /**
     * Takes a snapshot and returns the snapshot report content in the given stream. A facet implementation
     * can support different kinds of snapshots, the given name determines which kind of snapshot to take.
     *
     * @param request identifies the type of snapshot to take
     * @return snapshot results, including a stream containing the contents of the snapshot report
     * @throws Exception if failed to generate the snapshot report
     */
    public SnapshotReportResults getSnapshotReport(SnapshotReportRequest request) throws Exception
    {
        // TODO
        return null;
    }
</#if>
}
