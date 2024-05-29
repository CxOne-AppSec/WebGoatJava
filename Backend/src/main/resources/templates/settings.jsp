<!DOCTYPE>
<html lang="en">
<head>
    <meta charset = "UTF-8">
    <title>Settings</title>
</head>
<body>
    <h1> IRC Connection Parameters</h1>
    <form method="POST" action="/settings/updaate/">
    <input name="nombre" placeholder="Ingrese un nombre..." value= "${nombre}"/>
    <input name="servidor" placeholder="Ingrese un servidor..." value= "${servidor}"/>
    <input name="puerto" placeholder="6665" value= "${puerto}"/>
    <input type="submit" value="Guardar configurarcion"/>
    </form>
</body>
</html>