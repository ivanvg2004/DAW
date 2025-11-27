<html>
<body>
<h2>hola ivan!</h2>
<table border= "1">
<%
    int comptador = 1;
    int candidat = 1;
    while(comptador <= 20){
        boolean primer = true;
        for(int i = 2; i<candidat; i++){
            if(candidat % i == 0){
                primer = false;
            }
        }
        if(primer){
            out.println("<tr><td>");
            out.println(comptador);
            out.println("</td><td>");
            out.println(candidat);
            out.println("</td></tr>");
            comptador++;
        }
        candidat++;
    }
%>
</table>

</body>
</html>
