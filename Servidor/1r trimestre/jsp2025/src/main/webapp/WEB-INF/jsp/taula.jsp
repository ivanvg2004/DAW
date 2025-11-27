<%@ page isELIgnored="false" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
    <head>
        <style>
            .menor{
                background-color: yellow;
            }
        </style>
    </head>

    <body>

        <h1>Hello, ${bill.name}</h1>

        <div>Els dilluns hi ha ${dies.Dilluns} hores de clase </div>

        <table>
        <c:forEach var = "dia" items = "${dies}">
            <tr> <td> ${dia.key} </td> <td> ${dia.value} </td> </tr>
        </c:forEach>
        </table>
        hello1

        hello2



        <table border = "1">
            <c:forEach var = "row" items = "${taula}">
                <tr>
                    <c:forEach var = "el" items = "${row}">
                        <td>${el}</td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>