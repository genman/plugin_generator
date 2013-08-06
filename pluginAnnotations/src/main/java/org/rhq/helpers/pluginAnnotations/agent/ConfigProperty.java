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
 * A configuration property for resource or plugin config.
 * Currently only property simple are supported.
 * @author Heiko W. Rupp
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD})
public @interface ConfigProperty {

    public Scope scope() default Scope.PLUGIN;
    String property() default "";
    String displayName() default "";
    String description() default "";
    boolean readOnly() default false;
    String defaultValue() default "";
    RhqType rhqType() default RhqType.VOID;


    public enum Scope {
        PLUGIN,
        RESOURCE;
    }
}
