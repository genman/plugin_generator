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

<#if  props.manualAddOfResourceType>
import java.util.Collections;
</#if>
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.rhq.core.domain.configuration.Configuration;
import org.rhq.core.domain.configuration.Property;
import org.rhq.core.domain.configuration.PropertySimple;
import org.rhq.core.pluginapi.inventory.DiscoveredResourceDetails;
import org.rhq.core.pluginapi.inventory.InvalidPluginConfigurationException;
<#if props.manualAddOfResourceType>
import org.rhq.core.pluginapi.inventory.ManualAddFacet;
</#if>
import org.rhq.core.pluginapi.inventory.ProcessScanResult;
import org.rhq.core.pluginapi.inventory.ResourceDiscoveryComponent;
import org.rhq.core.pluginapi.inventory.ResourceDiscoveryContext;


/**
 * Discovery class
 */
public class ${props.discoveryClass} implements ResourceDiscoveryComponent<#if props.parentType??><${props.parentType}></#if>
<#if props.manualAddOfResourceType>,ManualAddFacet</#if>
{

    private final Log log = LogFactory.getLog(this.getClass());

<#if props.manualAddOfResourceType>
    /**
     * This method is an empty dummy, as you have selected manual addition
     * in the plugin generator.
     * If you want to have auto discovery too, remove the "return emptySet"
     * and implement the auto discovery logic.
     */
<#else>
    /**
     * Run the auto-discovery
     */
</#if>
    public Set<DiscoveredResourceDetails> discoverResources(ResourceDiscoveryContext<#if props.parentType??><${props.parentType}></#if> discoveryContext) throws Exception {
<#if  props.manualAddOfResourceType>
        return Collections.emptySet();
<#else>
        Set<DiscoveredResourceDetails> discoveredResources = new HashSet<DiscoveredResourceDetails>();

        /**
         * TODO : do your discovery here
         * A discovered resource must have a unique key, that must
         * stay the same when the resource is discovered the next
         * time
         */
        DiscoveredResourceDetails detail = null; // new DiscoveredResourceDetails(
//            discoveryContext.getResourceType(), // ResourceType
//        );


        // Add to return values
        discoveredResources.add(detail);
        log.info("Discovered new ... TODO "); // TODO change

        return discoveredResources;

</#if>
        }

<#if props.manualAddOfResourceType>
      /**
       * Do the manual add of this one resource
       */
      public DiscoveredResourceDetails discoverResource(Configuration pluginConfiguration, ResourceDiscoveryContext<#if props.parentType??><${props.parentType}></#if> context) throws InvalidPluginConfigurationException {

            // TODO implement this
            DiscoveredResourceDetails detail = null; // new DiscoveredResourceDetails(
//                context.getResourceType(), // ResourceType
//            );

            return detail;
      }
</#if>
}