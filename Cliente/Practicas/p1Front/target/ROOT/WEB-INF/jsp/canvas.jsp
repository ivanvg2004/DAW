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
    
    <div class="app-layout">

        <div class="sidebar-panel p-3">
            <h4 class="text-warning mb-3"><i class="bi bi-palette"></i> Paint</h4>

            <form action="${pageContext.request.contextPath}/canvas" method="post" id="save-form">
                
                <div class="form-floating mb-3">
                    <input type="text" class="form-control" id="drawingName" name="drawingName" placeholder="Nom" value="${dibuixName}">
                    <label for="drawingName">Nom del dibuix</label>
                </div>

                <h6 class="text-secondary border-bottom pb-2 mb-3">Dimensions Canvas</h6>
                <div class="row g-2 mb-3">
                    <div class="col">
                        <label for="input-width" class="form-label text-secondary small">Amplada</label>
                        <input type="number" class="form-control form-control-sm" id="input-width" value="${canvasWidth}" min="100">
                    </div>
                    <div class="col">
                        <label for="input-height" class="form-label text-secondary small">Alçada</label>
                        <input type="number" class="form-control form-control-sm" id="input-height" value="${canvasHeight}" min="100">
                    </div>
                </div>

                <h6 class="text-secondary border-bottom pb-2 mb-3">Eines</h6>
                
                <div id="tool-select" class="btn-group w-100 mb-2" role="group">
                    <button type="button" class="btn btn-outline-warning tool-btn" data-tool="select" title="Moure/Editar">
                        <i class="bi bi-arrows-move"></i>
                    </button>

                    <button type="button" class="btn btn-outline-warning tool-btn active" data-tool="freehand" title="Pinzell">    
                        <i class="bi bi-brush"></i>
                    </button>
                    <button type="button" class="btn btn-outline-warning tool-btn" data-tool="triangle" title="Triangle">
                        <i class="bi bi-triangle"></i>
                    </button>
                    <button type="button" class="btn btn-outline-warning tool-btn" data-tool="circle" title="Cercle">
                        <i class="bi bi-circle"></i>
                    </button>
                    <button type="button" class="btn btn-outline-warning tool-btn" data-tool="square" title="Quadrat">
                        <i class="bi bi-square"></i>
                    </button>
                    <button type="button" class="btn btn-outline-warning tool-btn" data-tool="star" title="Estrella">
                        <i class="bi bi-star"></i>
                    </button>
                </div>

                <button type="button" class="btn btn-outline-warning w-100 mb-3 toggle-btn" id="fill-btn">
                    <i class="bi bi-paint-bucket"></i> Omplir Figura
                </button>
                
                <div class="mb-2">
                    <label for="color-picker" class="form-label small text-secondary">Color</label>
                    <input type="color" class="form-control form-control-color w-100" id="color-picker" value="#FF0000">
                </div>

                <div class="mb-3">
                        <label for="size-slider" class="form-label small text-secondary">Gruix Traç: <span id="size-value">5</span>px</label>
                        <input type="range" class="form-range" min="1" max="50" value="5" id="size-slider">
                </div>

                <div id="object-controls" class="d-none">
                    <h6 class="text-secondary border-bottom pb-2 mb-3">Mida Figura</h6>
                    <div class="row g-2 mb-3">
                        <div class="col">
                            <button type="button" class="btn btn-outline-light w-100 btn-sm" id="scale-down-btn" title="Fer més petit">
                                <i class="bi bi-dash-lg"></i> Mida
                            </button>
                        </div>
                        <div class="col">
                            <button type="button" class="btn btn-outline-light w-100 btn-sm" id="scale-up-btn" title="Fer més gran">
                                Mida <i class="bi bi-plus-lg"></i>
                            </button>
                        </div>
                    </div>
                </div>

                <div class="d-grid gap-2 d-md-flex mb-3">
                    <button type="button" class="btn btn-danger btn-sm flex-grow-1" id="clear-canvas-btn">Netejar</button>
                    <div class="btn-group">
                        <button type="button" class="btn btn-outline-secondary btn-sm" id="undo-btn" title="Desfer">
                            <i class="bi bi-arrow-counterclockwise"></i>
                        </button>
                        <button type="button" class="btn btn-outline-secondary btn-sm" id="redo-btn" title="Refer">
                            <i class="bi bi-arrow-clockwise"></i>
                        </button>
                    </div>
                </div>

                <h6 class="text-secondary border-bottom pb-2 mb-2">Capes / Objectes</h6>
                <ul id="object-list" class="list-group mb-3" style="max-height: 150px; overflow-y: auto;">
                </ul>

                <input type="hidden" id="drawingId" name="drawingId" value="${dibuixId}">
                <input type="hidden" id="formCanvasWidth" name="canvasWidth" value="${canvasWidth}">
                <input type="hidden" id="formCanvasHeight" name="canvasHeight" value="${canvasHeight}">
                <input type="hidden" id="drawingContent" name="drawingContent">
                
                <div id="existingDrawingContent" style="display: none;">${dibuixContent}</div>

                <div class="d-grid gap-2 mt-auto">
                    <button type="submit" class="btn btn-warning" id="submit-btn">
                        <c:choose>
                            <c:when test="${not empty dibuixId}">Actualitzar</c:when>
                            <c:otherwise>Guardar Nou</c:otherwise>
                        </c:choose>
                    </button>
                </div>
            </form>
            
            <div class="mt-2">
                 <form action="/private" method="get">
                    <button type="submit" class="btn btn-outline-secondary w-100">Sortir</button>
                </form>
            </div>

        </div>

        <div class="main-workspace">
            <div class="canvas-scroll-area">
                <div class="canvas-wrapper">
                    <canvas id="mycanvas" width="${canvasWidth}" height="${canvasHeight}">
                        El teu navegador no suporta canvas.
                    </canvas>
                </div>
            </div>
        </div>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="${pageContext.request.contextPath}/js/canvas.js"></script>
</body>
</html>