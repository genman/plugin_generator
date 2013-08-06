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

/**
 * Metric Units.
 *
 * @author Galder Zamarreño
 * @author Heiko W. Rupp
 * See also org.rhq.core.domain.measurement.MeasurementUnits
 * @since 4.0
 */
@SuppressWarnings("unused")
public enum Units {
    NONE,  PERCENTAGE,
    BYTES, KILOBYTES, MEGABYTES, GIGABYTES, TERABYTES, PETABYTES,
    BITS, KILOBITS, MEGABITS, GIGABITS, TERABITS, PETABITS,
    EPOCH_MILLISECONDS, EPOCH_SECONDS,
    JIFFYS, NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS,
    CELSIUS, KELVIN, FAHRENHEIT;

   @Override
   public String toString() {
      return super.toString().toLowerCase();
   }
}
