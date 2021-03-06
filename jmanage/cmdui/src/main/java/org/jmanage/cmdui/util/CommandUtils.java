/**
 * Copyright 2004-2005 jManage.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jmanage.cmdui.util;

import org.jmanage.core.data.MBeanData;
import org.jmanage.core.data.AttributeListData;
import org.jmanage.core.management.ObjectAttribute;

import java.util.*;

/**
 *
 * Date:  Feb 23, 2005
 * @author	Rakesh Kalra
 */
public class CommandUtils {

    public static void printMBeans(List<MBeanData> mbeanList){
        /* first sort the list */
        Collections.sort(mbeanList, new Comparator<MBeanData>(){
            public int compare(MBeanData o1, MBeanData o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        if(mbeanList.size() > 0)
            Out.println();

        for(Iterator it=mbeanList.iterator(); it.hasNext(); ){
            MBeanData mbeanData = (MBeanData)it.next();
            Out.print(mbeanData.getName());
            if(mbeanData.getConfiguredName() != null){
                Out.println(" [" + mbeanData.getConfiguredName() + "]");
            }else{
                Out.println();
            }
        }
    }

    public static void printAttributeLists(AttributeListData[] attributeValues){
        if(attributeValues.length == 0){
            return;
        }
        Table table = new Table(attributeValues.length + 1);
        /* add header, if more than one attributeValues are present */
        if(attributeValues.length > 1){
            Object[] header = new Object[attributeValues.length + 1];
            header[0] = "Attributes";
            for(int i=0; i<attributeValues.length; i++){
                header[i+1] = attributeValues[i].getAppName();
            }
            table.setHeader(header);
        }

        List attrList = attributeValues[0].getAttributeList();
        int numberOfAttrs = attrList.size();
        for(int i=0; i<numberOfAttrs; i++){
            Object[] cols = new Object[attributeValues.length + 1];
            cols[0] = ((ObjectAttribute)attrList.get(i)).getName();
            for(int j=0; j<attributeValues.length; j++){
                if(!attributeValues[j].isError()){
                    ObjectAttribute objAttribute =
                            (ObjectAttribute)attributeValues[j].getAttributeList().get(i);
                    cols[j+1] = objAttribute.getDisplayValue();
                }else{
                    // todo: this should come out of display value - rk
                    cols[j+1] = "<unavailable>";
                }
            }
            table.add(cols);
        }
        table.print();
    }
}
