<%@ page isELIgnored="false" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Paint</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="${pageContext.request.contextPath}/js/script.js" defer></script>
</head>
<body>
    <div class="main-container">
        
        <div class="welcome-card col-md-5 col-lg-4">
            
            <h1 class="display-5 mb-3">Benvingut al meu paint!</h1>
            <h2 class="h4 fw-normal mb-4">Que vols fer?</h2>
            
            <div class="d-grid gap-3">
                <form action="/login" method="get">
                    <button type="submit" class="btn btn-warning btn-lg w-100">Login</button>
                </form>
                
                <form action="/register" method="get">
                    <button type="submit" class="btn btn-warning btn-lg w-100">Register</button>
                </form>
            </div>
            
        </div>
        
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>