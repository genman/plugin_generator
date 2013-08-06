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

import org.rhq.core.domain.event.Event;
import org.rhq.core.domain.event.EventSeverity;
import org.rhq.core.pluginapi.event.EventPoller;


public class ${props.name?cap_first}EventPoller implements EventPoller {

    public ${props.name?cap_first}EventPoller() {

    }


    /** Return the type of events we handle
     * @see org.rhq.core.pluginapi.event.EventPoller#getEventType()
     */
    public String getEventType() {
        return ${props.componentClass}.DUMMY_EVENT;
    }


    /** Return collected events
     * @see org.rhq.core.pluginapi.event.EventPoller#poll()
     */
    public Set<Event> poll() {
        Set<Event> eventSet = new HashSet<Event>();
        // TODO add your events here. Below is an example that
        /*
        synchronized (events) {
            eventSet.addAll(events);
            events.clear();
        }
        */
        return eventSet;
    }

}