<%@ page isELIgnored="false" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale-1.0">
    <title>Area Privada</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

    <div class="main-container">
        
        <div class="welcome-card col-11 col-md-5 col-lg-4">
            
            <h1 class="display-5 mb-3">Benvingut,</h1>
            <h2 class="h2 text-warning mb-4">${name}</h2>
            
            <p class="text-secondary">Que vols fer?</p>
            
            <div class="d-grid gap-3">
                <form action="/canvas" method="get">
                    <button type="submit" class="btn btn-warning btn-lg w-100">Fer un dibuix</button>
                </form>
                <form action="/dibuixos" method="get">
                    <button type="submit" class="btn btn-warning btn-lg w-100">Mostrar dibuixos</button>
                </form>
                <a href="${pageContext.request.contextPath}/" class="link-warning">Tancar sessio</a>
        
            </div>

            </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>