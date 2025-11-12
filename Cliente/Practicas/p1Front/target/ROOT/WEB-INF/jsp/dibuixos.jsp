<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Area Privada - Dibuixos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>

<body data-base-url="${pageContext.request.contextPath}">

    <div class="main-container">
        
        <div class="welcome-card col-11 col-md-10 col-lg-8 text-start">
            
            <h1 class="display-5 mb-4 text-center">${titol}</h1>
            
            <div class="d-flex justify-content-center gap-3 mb-3">
                <a href="${pageContext.request.contextPath}/dibuixos" class="btn btn-outline-warning">Tots els Dibuixos</a>
                <a href="${pageContext.request.contextPath}/dibuixos?scope=my" class="btn btn-outline-warning">Els Meus Dibuixos</a>
            </div>

            <table class="table table-dark table-striped">
                <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Nom</th>
                        <th scope="col">Usuari</th>
                        <th scope="col">Data Creació</th>
                        <th scope="col" class="text-end">Accions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dibuix" items="${dibuixos}">
                        <tr id="fila-dibuix-${dibuix.id}">
                            <th scope="row">${dibuix.id}</th>
                            <td><c:out value="${dibuix.name}" /></td>
                            <td><c:out value="${dibuix.username}" /></td>
                            <td>
                                <fmt:formatDate value="${dibuix.createdAt}" pattern="dd/MM/yyyy HH:mm" />
                            </td>
                            <td class="text-end">
                                <a href="${pageContext.request.contextPath}/visualitzar?id=${dibuix.id}" class="btn btn-sm btn-info">Visualitzar</a>
                                
                                <c:if test="${sessionScope.user eq dibuix.username}">
                                    <button type="button" class="btn btn-sm btn-danger btn-esborrar" data-id="${dibuix.id}">
                                        Esborrar
                                    </button>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    
                    <c:if test="${empty dibuixos}">
                        <tr>
                            <td colspan="5" class="text-center text-secondary">No s'han trobat dibuixos.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
            
            <div class="text-center mt-4">
                 <a href="${pageContext.request.contextPath}/private" class="btn btn-warning">Tornar</a>
            </div>
            
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/dibuixos.js"></script>
</body>
</html>