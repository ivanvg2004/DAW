<%@ page isELIgnored="false" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
    <body>
        <h1>Formulari</h1>

        <c:if test = "${not empty message}">
            <div>
                Bon intent, pero: ${message}
                Et queden ${intents}
            </div>
        </c:if>
        <c:if test = "${not gameover}">
            <form method = "post" action = "/game">
                Endevina el numero:
                <input type = "number" name = "number">
                <br>
                <input type = "submit">
            </form
        </c:if>
    </body>
</html>