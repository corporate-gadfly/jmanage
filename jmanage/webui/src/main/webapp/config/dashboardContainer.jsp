<meta http-equiv="refresh" content="<%=request.getSession().getMaxInactiveInterval()/3%>">
<script type="text/javascript" src="/js/dojo/dojo.js"></script>
<script type="text/javascript" src="/js/dashboard.js"></script>

<jsp:include page="<%=(String)request.getAttribute("dashboardPage")%>" />