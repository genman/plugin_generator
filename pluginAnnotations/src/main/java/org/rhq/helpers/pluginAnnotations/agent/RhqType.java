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

import java.io.File;


/**
 * Base data types from RHQ for properties
 * @author Heiko W. Rupp
 */
public enum RhqType {
    INTEGER(new Class<?>[]{Integer.class,int.class},Boolean.class),
    LONG(new Class<?>[]{Long.class,long.class},Long.class),
    DOUBLE(new Class<?>[]{Double.class,double.class},Double.class),
    STRING(new Class<?>[]{String.class},String.class),
    LONG_STRING(new Class<?>[]{},String.class),
    PASSWORD(new Class<?>[]{},String.class),
    BOOLEAN(new Class<?>[]{Boolean.class, boolean.class},Boolean.class),
    FLOAT(new Class<?>[]{Float.class, float.class},Float.class),
    FILE(new Class<?>[]{File.class},File.class),
    DIRECTORY(new Class<?>[]{},File.class),
    VOID(new Class<?>[]{Void.class,void.class},Void.class)
    ;
    private Class<?>[] fromClasses;
    private Class<?> toClass;

    private RhqType(Class<?>[] fromClasses,Class<?> toClass) {

        this.fromClasses = fromClasses;
        this.toClass = toClass;
    }

    public Class<?>[] getFromClasses() {
        return fromClasses;
    }

    public Class<?> getToClass() {
        return toClass;
    }

    public static RhqType findType(Class<?> clazz) {
        for (RhqType type : RhqType.values()) {
            for (Class from : type.getFromClasses()) {
                if (clazz.equals(from)) {
                    return type;
                }
            }
        }
        return null;
    }

    public String getRhqName() {
        String name = name().toLowerCase();
        if (name.equals("long_string")) {
            name = "longString";
        }
        return name;
    }
}
