<%@ page import="org.mskcc.cbio.portal.servlet.QueryBuilder" %>
<%@ page import="org.mskcc.cbio.portal.util.GlobalProperties" %>

<%
    String siteTitle = GlobalProperties.getTitle();
%>

<head>
<link href="css/cancergenomics.css?<%=GlobalProperties.getAppVersion()%>" type="text/css" rel="stylesheet" />
<link href="css/style.css?<%=GlobalProperties.getAppVersion()%>" type="text/css" rel="stylesheet" />
</head>

<% request.setAttribute(QueryBuilder.HTML_TITLE, siteTitle+"::OncoPrinter"); %>
<body>

    <div id="instructions">
    <div class="markdown">
            <p><jsp:include page="content/release_notes_oncoprinter.html" flush="true" /></p>
    </div>
    <jsp:include page="WEB-INF/jsp/global/footer.jsp" flush="true" />
    </div>
</body>
</html>