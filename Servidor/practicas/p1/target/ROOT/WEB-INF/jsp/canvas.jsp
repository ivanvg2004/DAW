<%@ page isELIgnored="false" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Paint</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    
    <div class="container-fluid mt-4">
        <div class="row">

            <div class="col-lg-3">
                <div class="welcome-card p-3">
                    <h5 class="text-warning">Eines</h5>
                    
                    <div id="tool-select" class="btn-group w-100 mb-2" role="group">
                        <button type="button" class="btn btn-outline-warning tool-btn active" data-tool="freehand">
                            <i class="bi bi-brush"></i>
                        </button>
                        <button type="button" class="btn btn-outline-warning tool-btn" data-tool="line">
                            <i class="bi bi-dash-lg"></i>
                        </button>
                        <button type="button" class="btn btn-outline-warning tool-btn" data-tool="circle">
                            <i class="bi bi-circle"></i>
                        </button>
                        <button type="button" class="btn btn-outline-warning tool-btn" data-tool="square">
                            <i class="bi bi-square"></i>
                        </button>
                        <button type="button" class="btn btn-outline-warning tool-btn" data-tool="star">
                            <i class="bi bi-star"></i>
                        </button>
                    </div>

                    <div class="d-grid mb-3">
                        <button type="button" class="btn btn-outline-warning toggle-btn" id="fill-btn">
                            <i class="bi bi-paint-bucket"></i> Omplir Figura
                        </button>
                    </div>
                    
                    <div class="mb-3">
                        <label for="color-picker" class="form-label">Color</label>
                        <input type="color" class="form-control form-control-color w-100" id="color-picker" value="#FF0000" title="Tria un color">
                    </div>

                    <div class="mb-3">
                        <label for="size-slider" class="form-label">Grandaria: <span id="size-value">5</span>px</label>
                        <input type="range" class="form-range" min="1" max="50" value="5" id="size-slider">
                    </div>

                    <div class="d-grid gap-2 d-md-flex justify-content-md-start mb-3">
                        <button type="button" class="btn btn-danger flex-grow-1" id="clear-canvas-btn">Netejar tot</button>
                        <div class="btn-group">
                            <button type="button" class="btn btn-outline-secondary" id="undo-btn" title="Desfer">
                                <i class="bi bi-arrow-counterclockwise"></i>
                            </button>
                            <button type="button" class="btn btn-outline-secondary" id="redo-btn" title="Refer">
                                <i class="bi bi-arrow-clockwise"></i>
                            </button>
                        </div>
                    </div>

                    <hr>

                    <h5 class="text-warning mt-3">Objectes</h5>
                    <ul id="object-list" class="list-group" style="max-height: 250px; overflow-y: auto;">
                    </ul>

                    <hr>
                    <form action="/private" method="get">
                        <button type="submit" class="btn btn-warning btn-lg w-100">Sortir</button>
                    </form>
                </div>
            </div>

            <div class="col-lg-9">
                <div class="welcome-card p-3">
                    <canvas id="mycanvas" width="800" height="600" style="width: 100%; height: auto; max-width: 800px;">
                        El teu navegador no suporta canvas.
                    </canvas>
                    
                    <form action="${pageContext.request.contextPath}/canvas" method="post" id="save-form" class="mt-3">
                        
                        <div class="form-floating mb-3">
                            <input type="text" class="form-control" id="drawingName" name="drawingName" placeholder="Nom del dibuix">
                            <label for="drawingName">Nom del dibuix</label>
                        </div>

                        <input type="hidden" id="drawingContent" name="drawingContent">
        
                        <div class="d-grid">
                            <button type="submit" class="btn btn-warning btn-lg w-100" id="submit-btn">Guardar dibuix</button>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/canvas.js"></script>
</body>
</html>