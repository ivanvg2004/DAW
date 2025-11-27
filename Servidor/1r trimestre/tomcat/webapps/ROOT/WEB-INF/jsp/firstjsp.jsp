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
        <h1>Hello from firstjsp!</h1>

        <div> Num aleatori:
            <b> ${aleatori} </b>
        </div>

        <div> LLista primers ${primers} </div>

        <ul>
            <c:forEach var="i" items= "${primers}">
                <li> ${i} </li>
            </c:forEach>
        </ul>

        <table border = "1">
            <th>
                Llista
            </th>
                <c:forEach var="i" items= "${primers}">
                    <tr>
                        <c:choose>
                            <c:when test = "${i < 50}">
                                <td class = "menor">
                            </c:when>
                            <c:otherwise>
                                <td>
                            </c:otherwise>
                        </c:choose>
                        ${i}
                        </td>
                    </tr>
                </c:forEach>
        </table>
    </body>
</html>