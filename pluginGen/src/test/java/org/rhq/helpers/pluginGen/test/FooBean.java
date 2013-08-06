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

package org.rhq.helpers.pluginGen.test;

import org.rhq.helpers.pluginAnnotations.agent.ConfigProperty;
import org.rhq.helpers.pluginAnnotations.agent.DataType;
import org.rhq.helpers.pluginAnnotations.agent.DisplayType;
import org.rhq.helpers.pluginAnnotations.agent.MeasurementType;
import org.rhq.helpers.pluginAnnotations.agent.Metric;
import org.rhq.helpers.pluginAnnotations.agent.Operation;
import org.rhq.helpers.pluginAnnotations.agent.Parameter;
import org.rhq.helpers.pluginAnnotations.agent.RhqType;
import org.rhq.helpers.pluginAnnotations.agent.Units;

/**
 * Just a sample
 * @author Heiko W. Rupp
 */

public class FooBean {

    @Metric(description = "How often was this bean invoked", displayType = DisplayType.SUMMARY, measurementType = MeasurementType.DYNAMIC,
        units = Units.SECONDS)
    int invocationCount;

    @Metric(description = "Just a foo", dataType = DataType.TRAIT)
    String lastCommand;

    @Operation(description = "Increase the invocation count")
    public int increaseCounter() {
        invocationCount++;
        return invocationCount;
    }

    @Operation(description = "Decrease the counter")
    public void decreaseCounter(@Parameter(description = "How much to decrease?", name = "by") int by) {
        invocationCount -= by;
    }

    @ConfigProperty(scope = ConfigProperty.Scope.PLUGIN, displayName="The Password",
        readOnly = false, property="thePassword",description = "A password", rhqType = RhqType.PASSWORD)
    String password;

    @ConfigProperty(scope = ConfigProperty.Scope.RESOURCE)
    int defaultSteps;

}
