<%--
  Created by IntelliJ IDEA.
  User: FoxyOwu
  Date: 17/09/2025
  Time: 6:21 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Infomacion</title>
    <%
        //codigo java
        int x = 5;
        float y = 9.8f;
        char letter = 'a';
        String name = "sebas";
    %>
</head>
<body>
    <h1>Informacion</h1>
    <ol>
        <li><b> x = </b> <%= x %></li>
        <li><b> y = </b> <%= y %></li>
        <li><b> nombre: </b><%= name.toUpperCase() %></li>
    </ol>
</body>
</html>
