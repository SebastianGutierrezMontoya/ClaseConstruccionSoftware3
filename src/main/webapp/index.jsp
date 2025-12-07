<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
    <h1><%= "Hello World!" %></h1>
    <br/>
<%--    <a href="hello-servlet">Hello Servlet</a>--%>

    <ul>
        <li><a href="StudentTree.jsp">Student Tree</a></li>
        <li><a href="api/student">Students</a></li>
        <li><a href="api/academico">Academico</a></li>
        <li><a href="api/detallematerias?idAcademico=1">Detalle Materias</a></li>
        <li><a href="api/preferencias?studentId=1">Preferencias</a></li>
    </ul>



    <br/>
<%--    <table>--%>
<%--        <thead>--%>
<%--            <tr>--%>
<%--                <th>Nombre</th>--%>
<%--                <th>Apellido</th>--%>
<%--                <th>Fecha nacimiento</th>--%>
<%--                <th>Correo</th>--%>
<%--            </tr>--%>
<%--        </thead>--%>
<%--        <tbody>--%>
<%--            <tr>--%>
<%--                <td></td>--%>
<%--            </tr>--%>
<%--        </tbody>--%>
<%--    </table>--%>

</body>
</html>