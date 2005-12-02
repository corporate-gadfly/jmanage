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
package org.jmanage.core.data;

import org.jmanage.core.management.data.DataFormatUtil;

/**
 *
 * date:  Jan 23, 2005
 * @author	Rakesh Kalra
 */
public class OperationResultData implements java.io.Serializable {

    public static final int RESULT_OK = 0;
    public static final int RESULT_ERROR =1;

    private String appName;
    private Object output;
    private int result = RESULT_OK;
    private String errorString;

    public OperationResultData(String appName){
        this.appName = appName;
    }

    public String getApplicationName(){
        return appName;
    }

    public Object getOutput() {
        return output;
    }

    public String getDisplayOutput(){
        return DataFormatUtil.format(getOutput());
    }

    public void setOutput(Object output) {
        this.output = output;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public boolean isError(){
        return result == RESULT_ERROR;
    }
}
