<html>
    <body>
        <h1>Calendari:</h1>
        <table border = "1">
        <%
            int diesTotals = 30;
            int primerDia = 3;
            int comptador = 1;
            int setmana = 0;
            out.println("<tr>");
            for(int i = 0; i<primerDia; i++){
                out.println("<td> </td>");
                setmana++;
            }

            while(comptador <= diesTotals){
                if(setmana % 7 == 0){
                    out.println("</tr><tr>");
                    setmana = 0;
                }
                out.println("<td>");
                out.println(comptador);
                out.println("</td>");
                comptador++;
                setmana++;
            }
            for(int i = setmana; i<7; i++){
                out.println("<td> </td>");
            }
            out.println("</tr>");
        %>
        </table>
    </body>
</html>