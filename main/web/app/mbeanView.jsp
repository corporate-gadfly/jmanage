<!--    /app/mbeanView.jsp  -->
<%@ page import="java.util.Iterator,
                 org.jmanage.core.management.*,
                 org.jmanage.webui.util.RequestParams,
                 java.util.Arrays,
                 java.util.Comparator,
                 org.jmanage.webui.util.WebContext,
                 org.jmanage.core.auth.User,
                 java.util.List,
                 org.jmanage.webui.util.MBeanUtils"%>

<%@ taglib uri="/WEB-INF/tags/jmanage/html.tld" prefix="jmhtml"%>
<%@ taglib uri="/WEB-INF/tags/jstl/c.tld" prefix="c"%>

<%
    final WebContext webContext = WebContext.get(request);
    final User user = webContext.getUser();

    ObjectInfo objectInfo = (ObjectInfo)request.getAttribute("objInfo");
    ObjectAttributeInfo[] attributes = objectInfo.getAttributes();
    ObjectOperationInfo[] operations = objectInfo.getOperations();
    ObjectNotificationInfo[] notifications = objectInfo.getNotifications();

    List attributeList =
            (List)request.getAttribute("attributeList");
%>

<tr>
    <td>
    <table border="0">
        <tr><td class="headtext" width="30%" nowrap><b>Object Name</b></td><td class="plaintext" width="70%"><c:out value="${param.objName}" /></td></tr>
        <tr><td class="headtext" width="30%" nowrap><b>Class Name</b></td><td class="plaintext" width="70%"><c:out value="${requestScope.objInfo.className}" /></td></tr>
        <tr><td class="headtext" width="30%" nowrap><b>Description</b></td><td class="plaintext" width="70%"><c:out value="${requestScope.objInfo.description}" /></td></tr>
        <tr><td colspan="2" nowrap>
            <c:choose>
                <c:when test="${requestScope.mbeanIncluded == 'true'}">
                    <jmhtml:form action="/config/removeMBeanConfig">
                        <jmhtml:hidden property="objectName"/>
                        <jmhtml:hidden property="refreshApps" value="true"/>
                        <a href="JavaScript:document.mbeanConfigForm.submit();" class="a1">Remove from Application</a>
                    </jmhtml:form>
                </c:when>
                <c:otherwise>
                    <jmhtml:form action="/config/addMBeanConfig">
                        <jmhtml:text property="name"/>
                        <jmhtml:hidden property="objectName"/>
                        <jmhtml:hidden property="refreshApps" value="true"/>
                        <a href="JavaScript:document.mbeanConfigForm.submit();" class="a1">Add to Application</a>
                    </jmhtml:form>
                </c:otherwise>
            </c:choose>
        </td></tr>
        <tr><td>&nbsp;</td></tr>
        <tr><td class="headtext" colspan="2" align="left"><b>Attributes</b></td></tr>
    </table>
    </td>
</tr>

<tr>
<td bgcolor="#E6EEF9" class="plaintext">
<jmhtml:form action="/app/updateAttributes" method="post">
<table border="0" cellspacing="5">
<tr>
    <td class="headtext"><b>Name</b></td>
    <td class="headtext"><b>Value</b></td>
    <td class="headtext"><b>Type</b></td>
</tr>
<%
    for(int index=0; index < attributes.length; index++){
        String rowStyle = index % 2 != 0 ? "oddrow" : "evenrow";
        ObjectAttributeInfo attributeInfo = attributes[index];
%>
<tr>
<td class="<%=rowStyle%>">
    <a href="JavaScript:showDescription('<%=MBeanUtils.jsEscape(attributeInfo.getDescription())%>');"><%=attributeInfo.getName()%></a>
</td>
<td class="<%=rowStyle%>">
    <%
        String attrValue =
                MBeanUtils.getAttributeValue(attributeList, attributeInfo.getName());
    %>
    <%if(user.isAdmin() && attributeInfo.isWritable() && !attrValue.equals("Object")){%>
        <input type="text" name="attr+<%=attributeInfo.getName()%>+<%=attributeInfo.getType()%>" size="50"
        value="<%=attrValue%>"/>
    <%}else{%>
        <%=attrValue%>
    <%}%>
</td>
<td class="<%=rowStyle%>">
    <%=attributeInfo.getType()%>
</td>
</tr>
<%  }%>
</table>
<%if(user.isAdmin()){%>
To save the changes to the attribute values click on
<jmhtml:submit value="Save" styleClass="Inside3d" />
<%}%>
</jmhtml:form>
</td>
</tr>

<%if(operations.length > 0 && user.isAdmin()){%>
<tr><td class="headtext" align="left"><b>Operations</b></td></tr>
<tr>
<td bgcolor="#E6EEF9" class="plaintext">
<table>
<%
    for(int index=0; index < operations.length; index++){
        String rowStyle = index % 2 != 0 ? "oddrow" : "evenrow";
        ObjectOperationInfo operationInfo = operations[index];
%>
<jmhtml:form action="/app/executeOperation">
<tr>
    <td class="<%=rowStyle%>"><%=operationInfo.getReturnType()%>
    <a  alt="<%=operationInfo.getName()%>" href="JavaScript:showDescription('<%=MBeanUtils.jsEscape(operationInfo.getDescription())%>');"><%=operationInfo.getName()%></a>
    (
    <%
        ObjectParameterInfo[] params = operationInfo.getSignature();
    %>
    <input type="hidden" name="paramCount" value="<%=params.length%>"/>
    <%
        for(int paramIndex = 0; paramIndex < params.length; paramIndex ++){
            if(paramIndex>0){%>,&nbsp;<%}%>
            <input type="hidden" name="<%=operationInfo.getName()%><%=paramIndex%>_type" value="<%=params[paramIndex].getType()%>"/>
            <input type="text" name="<%=operationInfo.getName()%><%=paramIndex%>_value" value=""/>
            <%=params[paramIndex].getType()%>
    <%  }%>
    )
    </td>
    <td class="<%=rowStyle%>">
        <input type="hidden" name="operationName" value="<%=operationInfo.getName()%>"/>
        <jmhtml:submit value="Execute" styleClass="Inside3d"/>&nbsp;
        [Impact: <%=MBeanUtils.getImpact(operationInfo.getImpact())%>]
    </td>
</tr>
</jmhtml:form>
<%  }%>
</table>
</td>
</tr>
<%}%>

<%if(notifications.length > 0 && user.isAdmin()){%>
<tr><td class="headtext" align="left"><b>Notifications</b></td></tr>
<tr>
<td bgcolor="#E6EEF9" class="plaintext">
<table>
<%
    for(int index=0; index < notifications.length; index++){
        String rowStyle = index % 2 != 0 ? "oddrow" : "evenrow";
        ObjectNotificationInfo notificationInfo = notifications[index];
%>
<tr>
    <td class="<%=rowStyle%>"><%=notificationInfo.getName()%></td>
    <td class="<%=rowStyle%>"><%=notificationInfo.getDescription()%></td>
</tr>
<%  }%>
</table>
</td>
</tr>
<%}%>