<%@ page isELIgnored="false" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Paint - Registre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <script src="js/script.js" defer></script>
</head>
<body>

    <div class="main-container">
        
        <div class="welcome-card col-11 col-md-5 col-lg-4">
            
            <h1 class="display-5 mb-4">Registre</h1>
            
            <form method="post" action="${pageContext.request.contextPath}/register" class="text-start" id = "mi-formulario">

                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="name" name="name" placeholder="Nom complet" required>
                    <label for="nom">Nom complet</label>
                </div>   
                
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="user" name="user" placeholder="Usuari" required>
                    <label for="user">Usuari</label>
                </div>
                
                <div class="form-floating mb-4">
                    <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
                    <label for="password">Password</label>
                </div>
     
                <div class="d-grid">
                    <button type="submit" class="btn btn-warning btn-lg w-100" id="submit-btn">Crear compte</button>
                </div>
            </form>
            
            <div class="error-message mt-4 ${empty message ? 'd-none' : ''}" id="password-error">
                <c:if test="${not empty message}">
                    ${message}
                </c:if>
            </div>

            <div class="mt-4 text-center">
                <span class="text-secondary">Ja tens compte?</span> 
                <a href="${pageContext.request.contextPath}/login" class="link-warning">Inicia sessio</a>
            </div>
            
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</body>
</html>