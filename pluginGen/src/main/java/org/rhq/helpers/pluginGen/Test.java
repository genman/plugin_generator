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

import java.io.File;

/**
 * Simple test class to easily trigger creation of output
 *
 * @author Heiko W. Rupp
 */
public class Test {

    public static void main(String[] args) throws Exception {

        File f = new File(".");
        System.out.println("here: " + f.getAbsolutePath());

        Props p = new Props();
        p.setName("foo");
        p.setPluginName("fooPlugin");
        p.setPluginDescription("fooDescription");
        p.setPkg("a.package");
        p.setCategory(ResourceCategory.SERVER);
        p.setPackagePrefix("com.acme.plugin");
        p.setDiscoveryClass("FooDiscovery");
        p.setComponentClass("FooComponent");
        p.setResourceConfiguration(true);
        p.setHasMetrics(true);
        p.setHasOperations(true);
        p.setEvents(true);
        p.setCreateChildren(true);
        p.setSingleton(true);

        Props child = new Props();
        child.setName("FooChild1");
        child.setPkg("child.package1");
        child.setCategory(ResourceCategory.SERVICE);
        child.setPackagePrefix(p.getPackagePrefix());
        child.setDiscoveryClass("ChildDiscovery1");
        child.setComponentClass("ChildComponent1");
        child.setParentType("FooComponent");
        child.setEvents(true);

        p.getChildren().add(child);

        child = new Props();
        child.setName("FooChild2");
        child.setPkg("child.package2");
        child.setCategory(ResourceCategory.SERVICE);
        child.setParentType("FooComponent");
        child.setPackagePrefix(p.getPackagePrefix());
        child.setDiscoveryClass("ChildDiscovery2");
        child.setComponentClass("ChildComponent2");
        child.setEvents(true);
        child.setHasMetrics(true);
        child.setHasOperations(true);

        p.getChildren().add(child);


        PluginGen pg = new PluginGen();
        pg.createFile(p,"descriptor","rhq-plugin.xml","/tmp");
        pg.createFile(p,"component", "FooComponent.java", "/tmp");
        pg.createFile(p,"discovery", "FooDiscovery.java", "/tmp");
        pg.createFile(p,"pom", "pom.xml", "/tmp");
        pg.createFile(p,"eventPoller", "FooEventPoller.java", "/tmp");
        pg.createFile(p.getChildren().iterator().next(),"component", "ChildComponent1.java", "/tmp");

    }
}
