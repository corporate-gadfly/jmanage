/**
 * Copyright (c) 2004-2005 jManage.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 */
package org.jmanage.core.alert;

/**
 *
 * Date:  Jul 31, 2005
 * @author	Rakesh Kalra
 */
public interface AlertHandler {

    public void handle(AlertInfo alertInfo);
}