<%@ page isELIgnored="false" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
    <body>
        <h1>Formulari</h1>
        <form method = "post" action = "/form">
            Escriu un numero:
            <input type = "number" name = "var1">
            <br>
            Escriu un altre numero:
            <input type = "number" name = "var2">
            <br>
            <select name = "op">
                <option value = "+"> + </option>
                <option value = "-"> - </option>
                <option value = "*"> * </option>
                <option value = "/"> / </option>
            <br>
            <input type = "submit">
        </form
    </body>
</html>