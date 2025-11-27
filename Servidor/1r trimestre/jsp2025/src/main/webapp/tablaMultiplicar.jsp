<html>
    <body>
        <center>
        <%
        for (int i = 1; i <= 10; i++) {
            out.println("<h3>Taula del " + i + "</h3>");
            out.println("<table border=\"1\">");
            out.println("<tr><th>Operacio</th><th>Resultat</th></tr>");

            for (int j = 1; j <= 10; j++) {
                out.println("<tr><td>" + i + " x " + j + "</td><td>" + (i * j) + "</td></tr>");
            }
            out.println("</table>");
        }
        %>
        </center>
    </body>
</html>