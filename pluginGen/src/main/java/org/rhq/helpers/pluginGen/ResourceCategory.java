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

import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ArrayList;

/**
 * Possible categories
 * @author Heiko W. Rupp
 */
public enum ResourceCategory {
    /** Possible categories */
    PLATFORM('P'), SERVER('S'), SERVICE('I');

    char abbrevLetter;

    private ResourceCategory(char abbrev) {
        abbrevLetter = abbrev;
    }

    private static Map<ResourceCategory,List<ResourceCategory>> enumMap =
            new EnumMap<ResourceCategory, List<ResourceCategory>>(ResourceCategory.class);

    static {
        for (ResourceCategory cat : ResourceCategory.values()) {
            List<ResourceCategory> catList = new ArrayList<ResourceCategory>();
            switch (cat) {
                case PLATFORM:
                    catList.addAll(Arrays.asList(PLATFORM, SERVER, SERVICE));
                    break;
                case SERVER:
                    catList.addAll(Arrays.asList(SERVER, SERVICE));
                    break;
                case SERVICE:
                    catList.addAll(Arrays.asList(SERVICE));
                    break;
            }
            enumMap.put(cat,catList);
        }
    }

    public static List<ResourceCategory> getPossibleChildren(ResourceCategory parent) {

        if (parent == null)
            return enumMap.get(PLATFORM);
        else
            return enumMap.get(parent);

    }

    public char getAbbrev() {
        return abbrevLetter;
    }

    public static ResourceCategory getByAbbrv(char abbrev) {
        EnumSet<ResourceCategory> set = EnumSet.allOf(ResourceCategory.class);
        for (ResourceCategory cat : set) {
            if (cat.abbrevLetter ==abbrev)
                return cat;
        }
        return null;
    }

    public String getLowerName() {
        return toString().toLowerCase(Locale.getDefault());
    }
}
