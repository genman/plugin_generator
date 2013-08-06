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

package org.rhq.helpers.pluginGen;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.rhq.helpers.pluginAnnotations.agent.ConfigProperty;
import org.rhq.helpers.pluginAnnotations.agent.Metric;
import org.rhq.helpers.pluginAnnotations.agent.Operation;
import org.rhq.helpers.pluginAnnotations.agent.Parameter;
import org.rhq.helpers.pluginAnnotations.agent.RhqType;

/**
 * Processor that scans a directory for annotated classes and generates metrics etc. from them.
 * @author Heiko W. Rupp
 */
public class AnnotationProcessor {

    private final DirectoryClassLoader classLoader;

    public AnnotationProcessor(String baseDirectory) {
        classLoader = new DirectoryClassLoader();
        classLoader.setBaseDir(baseDirectory);
    }

    public void populate(Props props) {
        List<Class> classList = classLoader.findClasses();

        populateMetrics(props, classList);
        populateOperations(props, classList);
        populateConfigurations(props, classList);
    }

    public void populateMetrics(Props props, List<Class> classes) {
        for (Class<?> clazz : classes) {
            for (Field field : clazz.getDeclaredFields()) {
                Metric metricAnnot = field.getAnnotation(Metric.class);
                addMetric(props, metricAnnot, field.getName());
            }

            for (Method method : clazz.getDeclaredMethods()) {
                Metric metricAnnot = method.getAnnotation(Metric.class);
                addMetric(props, metricAnnot, method.getName());
            }
        }
    }

    public void populateOperations(Props props, List<Class> classes) {
        for (Class<?> clazz : classes) {
            for (Method method : clazz.getDeclaredMethods()) {
                Operation operationAnnot = method.getAnnotation(Operation.class);
                if (operationAnnot != null) {
                    String property = operationAnnot.name();
                    if (property.isEmpty()) {
                        property = method.getName();
                    }
                    Props.OperationProps op = new Props.OperationProps(property);
                    op.setDisplayName(operationAnnot.displayName());
                    op.setDescription(operationAnnot.description());
                    RhqType type = RhqType.findType(method.getReturnType());
                    if (type != RhqType.VOID) {
                        Props.SimpleProperty simpleProperty = new Props.SimpleProperty(type.getRhqName());
                        op.setResult(simpleProperty);
                    }

                    Class[] types = method.getParameterTypes();
                    int i=0;
                    for (Annotation[] annotations : method.getParameterAnnotations() ) {
                        for (Annotation annotation : annotations) {
                            if (annotation instanceof Parameter) {
                                Parameter parameter = (Parameter) annotation;
                                Props.SimpleProperty simpleProperty = new Props.SimpleProperty(parameter.name());
                                simpleProperty.setDescription(parameter.description());
                                Class typeClass = types[i];
                                RhqType rhqType = RhqType.findType(typeClass);
                                if (parameter.type()!=RhqType.VOID){
                                    rhqType = parameter.type();
                                }
                                simpleProperty.setType(rhqType.getRhqName());
                                op.getParams().add(simpleProperty);
                            }
                        }
                        i++;
                    }
                    props.getOperations().add(op);
                }

            }
        }
    }

    public void populateConfigurations(Props props, List<Class> classes) {
        for (Class<?> clazz : classes) {
           for (Field field : clazz.getDeclaredFields()) {
               ConfigProperty configProperty = field.getAnnotation(ConfigProperty.class);
               if (configProperty!=null) {
                   String name = configProperty.property();
                   if(name.isEmpty()) {
                       name = field.getName();
                   }
                   Props.SimpleProperty property = new Props.SimpleProperty(name);
                   property.setDescription(configProperty.description());
                   property.setDisplayName(configProperty.displayName());
                   Class type = field.getType();
                   RhqType rhqType = RhqType.findType(type);
                   if (configProperty.rhqType()!=RhqType.VOID) {
                       rhqType = configProperty.rhqType();
                   }
                   property.setType(rhqType.getRhqName());

                   switch (configProperty.scope()){
                    case PLUGIN:
                        props.getPluginConfig().add(property);
                        break;
                    case RESOURCE:
                        props.getResourceConfig().add(property);
                        break;
                    default:
                        throw new IllegalStateException("Unknown scope: " +configProperty.scope().name());
                   }
               }
           }
       }
    }

    private void addMetric(Props props, Metric metricAnnot, String name) {
        if (metricAnnot != null) {
            String property = metricAnnot.property();
            if (property.isEmpty()) {
                property = name;
            }
            Props.MetricProps metric = new Props.MetricProps(property);
            metric.setDisplayName(metricAnnot.displayName());
            metric.setDisplayType(metricAnnot.displayType());
            metric.setDataType(metricAnnot.dataType());
            metric.setDescription(metricAnnot.description());
            metric.setUnits(metricAnnot.units());
            props.getMetrics().add(metric);
        }
    }

}
