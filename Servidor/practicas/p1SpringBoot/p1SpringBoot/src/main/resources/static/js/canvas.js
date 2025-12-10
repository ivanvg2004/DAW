const canvas = document.getElementById("mycanvas");
const ctx = canvas.getContext("2d");

const toolButtons = document.querySelectorAll(".tool-btn");
const colorPicker = document.getElementById("color-picker");
const sizeSlider = document.getElementById("size-slider");
const sizeValueSpan = document.getElementById("size-value");
const clearButton = document.getElementById("clear-canvas-btn");
const objectListUl = document.getElementById("object-list");
const saveForm = document.getElementById("save-form");
const drawingContentInput = document.getElementById("drawingContent");

const fillButton = document.getElementById("fill-btn");
const undoButton = document.getElementById("undo-btn");
const redoButton = document.getElementById("redo-btn");

let isFilled = false;
let isDrawing = false;
let currentTool = "freehand";
let currentColor = colorPicker.value;
let currentSize = sizeSlider.value;
let startX, startY;

let objects = [];
let history = [[]];
let historyIndex = 0;

function redrawCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    objects.forEach(obj => {
        drawObject(obj);
    });
}

function drawObject(obj) {
    ctx.strokeStyle = obj.color;
    ctx.fillStyle = obj.color;
    ctx.lineWidth = obj.size;
    ctx.lineCap = "round";
    ctx.lineJoin = "round";

    ctx.beginPath();

    switch(obj.type) {
        case "freehand":
            ctx.moveTo(obj.points[0].x, obj.points[0].y);
            obj.points.forEach(p => ctx.lineTo(p.x, p.y));
            ctx.stroke();
            break;
        case "line":
            ctx.moveTo(obj.x1 || obj.x, obj.y1 || obj.y);
            ctx.lineTo(obj.x2, obj.y2);
            ctx.stroke();
            break;
        case "circle":
            ctx.arc(obj.x, obj.y, obj.radius, 0, Math.PI * 2);
            if (obj.isFilled) ctx.fill(); else ctx.stroke();
            break;
        case "square":
            ctx.rect(obj.x - obj.radius, obj.y - obj.radius, obj.radius * 2, obj.radius * 2);
            if (obj.isFilled) ctx.fill(); else ctx.stroke();
            break;
        case "star":
            const spikes = 7;
            const outerRadius = obj.radius;
            const innerRadius = obj.radius * 0.4;
            let rot = Math.PI / 2 * 3;
            let x = obj.x;
            let y = obj.y;
            let step = Math.PI / spikes;

            ctx.beginPath();
            ctx.moveTo(obj.x, obj.y - outerRadius);
            for (let i = 0; i < spikes; i++) {
                x = obj.x + Math.cos(rot) * outerRadius;
                y = obj.y + Math.sin(rot) * outerRadius;
                ctx.lineTo(x, y);
                rot += step;
                x = obj.x + Math.cos(rot) * innerRadius;
                y = obj.y + Math.sin(rot) * innerRadius;
                ctx.lineTo(x, y);
                rot += step;
            }
            ctx.lineTo(obj.x, obj.y - outerRadius);
            ctx.closePath();
            if (obj.isFilled) ctx.fill(); else ctx.stroke();
            break;
    }
}

function updateObjectList() {
    objectListUl.innerHTML = "";
    
    objects.forEach((obj, index) => {
        const li = document.createElement("li");
        li.className = "list-group-item";
        li.style.backgroundColor = "#2c2c31"; // Estilo oscuro
        li.style.color = "#e0e0e0";
        
        const text = document.createElement("span");
        const fillText = obj.isFilled ? " (Filled)" : "";
        text.textContent = `${index + 1}: ${obj.name}${fillText}`;
        
        const deleteBtn = document.createElement("button");
        deleteBtn.className = "btn btn-outline-danger btn-sm";
        deleteBtn.innerHTML = "&times;";
        deleteBtn.onclick = () => {
            deleteObject(index);
        };
        
        li.appendChild(text);
        li.appendChild(deleteBtn);
        objectListUl.appendChild(li);
    });
}

function addObject(obj) {
    objects.push(obj);
}

function deleteObject(index) {
    objects.splice(index, 1);
    redrawCanvas();
    updateObjectList();
    saveState();
}

function saveState() {
    history = history.slice(0, historyIndex + 1);
    history.push(JSON.parse(JSON.stringify(objects)));
    historyIndex++;
    updateUndoRedoButtons();
}

function undo() {
    if (historyIndex > 0) {
        historyIndex--;
        objects = JSON.parse(JSON.stringify(history[historyIndex]));
        redrawCanvas();
        updateObjectList();
        updateUndoRedoButtons();
    }
}

function redo() {
    if (historyIndex < history.length - 1) {
        historyIndex++;
        objects = JSON.parse(JSON.stringify(history[historyIndex]));
        redrawCanvas();
        updateObjectList();
        updateUndoRedoButtons();
    }
}

function updateUndoRedoButtons() {
    undoButton.disabled = (historyIndex <= 0);
    redoButton.disabled = (historyIndex >= history.length - 1);
}

// === EVENT LISTENERS DE HERRAMIENTAS ===

toolButtons.forEach(btn => {
    btn.addEventListener("click", () => {
        document.querySelector(".tool-btn.active").classList.remove("active");
        btn.classList.add("active");
        currentTool = btn.dataset.tool;
    });
});

fillButton.addEventListener("click", () => {
    isFilled = !isFilled;
    fillButton.classList.toggle("active");
});

colorPicker.addEventListener("input", (e) => {
    currentColor = e.target.value;
});

sizeSlider.addEventListener("input", (e) => {
    currentSize = e.target.value;
    sizeValueSpan.textContent = currentSize;
});

clearButton.addEventListener("click", () => {
    objects = [];
    redrawCanvas();
    updateObjectList();
    saveState();
});

undoButton.addEventListener("click", undo);
redoButton.addEventListener("click", redo);

saveForm.addEventListener("submit", (e) => {
    drawingContentInput.value = JSON.stringify(objects);
});

// === LOGICA DEL MOUSE ===

function getMousePos(e) {
    const rect = canvas.getBoundingClientRect();
    const scaleX = canvas.width / rect.width;
    const scaleY = canvas.height / rect.height;
    return {
        x: (e.clientX - rect.left) * scaleX,
        y: (e.clientY - rect.top) * scaleY
    };
}

canvas.addEventListener("mousedown", (e) => {
    isDrawing = true;
    const pos = getMousePos(e);
    startX = pos.x;
    startY = pos.y;
    
    if (currentTool === "freehand") {
        const freehandObj = {
            type: "freehand",
            name: `Dibuix ${objects.length + 1}`,
            color: currentColor,
            size: currentSize,
            points: [{ x: startX, y: startY }],
            isFilled: false
        };
        addObject(freehandObj);
    }
});

canvas.addEventListener("mousemove", (e) => {
    if (!isDrawing) return;
    
    const pos = getMousePos(e);
    
    if (currentTool === "freehand") {
        const currentFreehand = objects[objects.length - 1];
        currentFreehand.points.push(pos);
        
        ctx.strokeStyle = currentFreehand.color;
        ctx.lineWidth = currentFreehand.size;
        ctx.lineCap = "round";
        ctx.lineJoin = "round";
        
        ctx.beginPath();
        ctx.moveTo(startX, startY);
        ctx.lineTo(pos.x, pos.y);
        ctx.stroke();
        startX = pos.x;
        startY = pos.y;
        
    } else if (currentTool === "line" || currentTool === "circle" || currentTool === "square" || currentTool === "star") {
        redrawCanvas();

        const previewObj = {
            type: currentTool,
            color: currentColor,
            size: currentSize,
            x: startX,
            y: startY,
            x2: pos.x,
            y2: pos.y,
            radius: Math.sqrt(Math.pow(pos.x - startX, 2) + Math.pow(pos.y - startY, 2)),
            isFilled: isFilled
        };
        drawObject(previewObj);
    }
});

canvas.addEventListener("mouseup", (e) => {
    if (!isDrawing) return;
    isDrawing = false;
    
    const pos = getMousePos(e);
    
    const newObj = {
        color: currentColor,
        size: currentSize,
        name: `${currentTool} ${objects.length + 1}`,
        isFilled: isFilled
    };

    // Lógica para añadir formas cerradas
    if (currentTool !== "freehand") {
         if (currentTool === "line") {
            Object.assign(newObj, {
                type: "line",
                x1: startX,
                y1: startY,
                x2: pos.x,
                y2: pos.y,
                isFilled: false
            });
        } else if (currentTool === "circle") {
            Object.assign(newObj, {
                type: "circle",
                x: startX,
                y: startY,
                radius: Math.sqrt(Math.pow(pos.x - startX, 2) + Math.pow(pos.y - startY, 2))
            });
        } else if (currentTool === "square") {
            const dist = Math.max(Math.abs(pos.x - startX), Math.abs(pos.y - startY));
            Object.assign(newObj, {
                type: "square",
                x: startX,
                y: startY,
                radius: dist
            });
        } else if (currentTool === "star") {
            const radius = Math.sqrt(Math.pow(pos.x - startX, 2) + Math.pow(pos.y - startY, 2));
            Object.assign(newObj, {
                type: "star",
                x: startX,
                y: startY,
                radius: radius
            });
        }
        addObject(newObj);
    }
    
    redrawCanvas();
    updateObjectList();
    saveState();
});

canvas.addEventListener("mouseleave", () => {
    if (isDrawing) {
        isDrawing = false;
        canvas.dispatchEvent(new MouseEvent('mouseup', { bubbles: true }));
    }
});

//CARGAR DIBUIX SI EXISTEIX
document.addEventListener("DOMContentLoaded", () => {
    const initialDataElement = document.getElementById("initialJSON");
    
    if (initialDataElement && initialDataElement.textContent) {
        const jsonContent = initialDataElement.textContent.trim();
        
        if (jsonContent && jsonContent !== "[]") {
            try {
                objects = JSON.parse(jsonContent);
                
                redrawCanvas();
                updateObjectList();
                
                history = [JSON.parse(JSON.stringify(objects))];
                historyIndex = 0;
                updateUndoRedoButtons();
                
            } catch (e) {
                console.error("Error cargando el dibujo:", e);
            }
        }
    }
    
    updateUndoRedoButtons();
});

function autoSave() {
    const idInput = document.querySelector('input[name="id"]');
    const id = idInput ? idInput.value : null;
    
    const name = document.getElementById("name").value;
    const permissionInput = document.querySelector('input[name="permission"]:checked');
    
    const permission = permissionInput ? permissionInput.value : "private";
    
    const content = JSON.stringify(objects);

    if (objects.length === 0) return;

    const formData = new FormData();
    if(id) formData.append("id", id);
    formData.append("name", name);
    formData.append("permission", permission);
    formData.append("content", content);

    fetch('/api/autosave', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        console.log("Auto-save:", data.status, new Date().toLocaleTimeString());
        
        if(data.status === 'success') {
            if(data.id){
                const idInput = document.querySelector('input[name="id"]');
                if (idInput) {
                    idInput.value = data.id;
                    console.log("ID actualizado:", data.id);
                }
            }
            if (data.generatedName) {
                const nameInput = document.querySelector("#name");
                if(nameInput && nameInput.value == ""){
                    nameInput.value = data.generatedName;
                }
            }
            const originalBorder = canvas.style.border;
            canvas.style.border = "2px solid #28a745"; // Verde
            setTimeout(() => { canvas.style.border = originalBorder; }, 1000);
        }
    })
    .catch(error => console.error("Error en auto-save:", error));
}

setInterval(autoSave, 60000);