<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Datos del Estudiante</title>
    <style>
        body { font-family: Arial, sans-serif; padding: 20px; }
        table { border-collapse: collapse; width: 400px; margin-bottom: 20px; }
        th, td { border: 1px solid #999; padding: 8px; }
        th { background: #eee; text-align: left; }
        #zona-tablas { margin-top: 30px; }
        input, button { padding: 6px 10px; }
    </style>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

<h2>Consulta de Estudiantes</h2>

<!-- Formulario simple -->
<label for="txtId">ID de estudiante:</label>
<input type="number" id="txtId" value="1">
<button onclick="consultar()">Consultar</button>

<div id="zona-tablas"></div>

<script>
    // ==============================
    //  URL base automática del servidor
    // ==============================
    const baseUrl = "<%= request.getScheme() + "://"
                    + request.getServerName() + ":"
                    + request.getServerPort()
                    + request.getContextPath() %>";



</script>
<script src="js/Student.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
