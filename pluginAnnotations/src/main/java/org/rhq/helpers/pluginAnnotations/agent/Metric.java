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
package org.rhq.helpers.pluginAnnotations.agent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to denote a metric that should be measured
 *
 * @author Heiko W. Rupp
 * @author Galder Zamarre–o
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD, ElementType.METHOD })
public @interface Metric {
   String property() default "";
   String displayName() default "";
   String description() default "";
   long defaultInterval() default 120000000L; // 20 min
   DisplayType displayType() default DisplayType.DETAIL;
   DataType dataType() default DataType.MEASUREMENT;
   Units units() default Units.NONE;
   MeasurementType measurementType() default MeasurementType.DYNAMIC;
 }