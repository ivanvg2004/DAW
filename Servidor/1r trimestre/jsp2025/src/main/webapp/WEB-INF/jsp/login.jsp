<%@ page isELIgnored="false" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
    <body>
        <h1>login</h1>
            <form method = "post" action = "/login">
                Usuari:
                <br>
                <input type = "text" name = "user">
                <br>
                Password:
                <br>
                <input type = "password" name = "password">
                <br>
                <input type = "submit">
            </form>
            <div>
                <c:if test = "${not empty message}">
                    ${message}
                </c:if>
            </div>
    </body>
</html>