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

const widthInput = document.getElementById("input-width");
const heightInput = document.getElementById("input-height");
const formWidthInput = document.getElementById("formCanvasWidth");
const formHeightInput = document.getElementById("formCanvasHeight");

const scaleUpBtn = document.getElementById("scale-up-btn");
const scaleDownBtn = document.getElementById("scale-down-btn");
const objectControls = document.getElementById("object-controls");

const fillButton = document.getElementById("fill-btn");
const undoButton = document.getElementById("undo-btn");
const redoButton = document.getElementById("redo-btn");

let isFilled = false;
let isDrawing = false;
let currentTool = "freehand";
let currentColor = colorPicker.value;
let currentSize = sizeSlider.value;
let startX, startY;

let isDragging = false;
let selectedObjectIndex = -1;
let lastMouseX, lastMouseY;

let objects = [];
let history = [[]];
let historyIndex = 0;

function redrawCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    objects.forEach((obj, index) => {
        drawObject(obj);
        if (index === selectedObjectIndex) {
            drawSelectionBox(obj);
        }
    });
}

function definePath(obj) {
    ctx.beginPath();
    switch(obj.type) {
        case "freehand":
            if(obj.points.length > 0) {
                ctx.moveTo(obj.points[0].x, obj.points[0].y);
                obj.points.forEach(p => ctx.lineTo(p.x, p.y));
            }
            break;
        case "triangle":
            const p1x = obj.x1 || obj.x;
            const p1y = obj.y1 || obj.y;
            const p2x = obj.x2;
            const p2y = obj.y2;
            const p3x = obj.x3 || (2 * p1x - p2x);
            const p3y = obj.y3 || obj.y2;
            ctx.moveTo(p1x, p1y);
            ctx.lineTo(p2x, p2y);
            ctx.lineTo(p3x, p3y);
            ctx.closePath();
            break;
        case "circle":
            ctx.ellipse(obj.x, obj.y, Math.abs(obj.radiusX), Math.abs(obj.radiusY), 0, 0, Math.PI * 2);
            break;
        case "square":
            ctx.rect(obj.x, obj.y, obj.width, obj.height);
            break;
        case "star":
            const spikes = 7;
            const outerRadius = obj.radius;
            const innerRadius = obj.radius * 0.4;
            let rot = Math.PI / 2 * 3;
            let x = obj.x;
            let y = obj.y;
            let step = Math.PI / spikes;
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
            break;
    }
}

function drawObject(obj) {
    ctx.strokeStyle = obj.color;
    ctx.fillStyle = obj.color;
    ctx.lineWidth = obj.size;
    ctx.lineCap = "round";
    ctx.lineJoin = "round";

    definePath(obj); 

    if (obj.type === "freehand") {
        ctx.stroke();
    } else {
        if (obj.isFilled) ctx.fill(); else ctx.stroke();
    }
}

function drawSelectionBox(obj) {
    ctx.save();
    ctx.strokeStyle = "#00AAFF";
    ctx.lineWidth = 2;
    ctx.setLineDash([5, 5]);
    definePath(obj);
    ctx.stroke();
    ctx.restore();
}

function getObjectAt(x, y) {
    for (let i = objects.length - 1; i >= 0; i--) {
        const obj = objects[i];
        ctx.lineWidth = Math.max(10, obj.size); 
        definePath(obj);
        
        if (obj.isFilled && obj.type !== "freehand") {
            if (ctx.isPointInPath(x, y)) return i;
        } else {
            if (ctx.isPointInStroke(x, y)) return i;
        }
    }
    return -1;
}

function selectObject(index) {
    selectedObjectIndex = index;
    
    if (index !== -1) {
        const obj = objects[index];
        colorPicker.value = obj.color;
        currentColor = obj.color;
        sizeSlider.value = obj.size;
        currentSize = obj.size;
        sizeValueSpan.textContent = obj.size;
        
        isFilled = obj.isFilled || false;
        if (isFilled) fillButton.classList.add("active");
        else fillButton.classList.remove("active");
        
        setTool("select");
        objectControls.classList.remove("d-none");
    } else {
        objectControls.classList.add("d-none");
    }
    
    redrawCanvas();
    updateObjectList();
}

function setTool(toolName) {
    currentTool = toolName;
    document.querySelector(".tool-btn.active")?.classList.remove("active");
    const btn = document.querySelector(`.tool-btn[data-tool="${toolName}"]`);
    if (btn) btn.classList.add("active");
}

function updateObjectList() {
    objectListUl.innerHTML = "";
    
    objects.forEach((obj, index) => {
        const li = document.createElement("li");
        li.className = `list-group-item ${index === selectedObjectIndex ? 'active' : ''}`;
        
        li.onclick = (e) => {
            if (e.target.tagName === 'BUTTON' || e.target.parentElement.tagName === 'BUTTON') return;
            selectObject(index);
        };
        
        const text = document.createElement("span");
        const fillText = obj.isFilled ? " (Ple)" : "";
        text.textContent = `${index + 1}: ${obj.name}${fillText}`;
        
        const deleteBtn = document.createElement("button");
        deleteBtn.className = "btn btn-outline-danger btn-sm";
        deleteBtn.innerHTML = '&times;';
        deleteBtn.onclick = (e) => {
            e.stopPropagation();
            deleteObject(index);
        };
        
        li.appendChild(text);
        li.appendChild(deleteBtn);
        objectListUl.appendChild(li);
    });
}

function addObject(obj) {
    objects.push(obj);
    selectedObjectIndex = -1;
    objectControls.classList.add("d-none");
}

function deleteObject(index) {
    if (index === selectedObjectIndex) {
        selectedObjectIndex = -1;
        objectControls.classList.add("d-none");
    }
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
        selectedObjectIndex = -1;
        objectControls.classList.add("d-none");
        redrawCanvas();
        updateObjectList();
        updateUndoRedoButtons();
    }
}

function redo() {
    if (historyIndex < history.length - 1) {
        historyIndex++;
        objects = JSON.parse(JSON.stringify(history[historyIndex]));
        selectedObjectIndex = -1;
        objectControls.classList.add("d-none");
        redrawCanvas();
        updateObjectList();
        updateUndoRedoButtons();
    }
}

function updateUndoRedoButtons() {
    undoButton.disabled = (historyIndex <= 0);
    redoButton.disabled = (historyIndex >= history.length - 1);
}

toolButtons.forEach(btn => {
    btn.addEventListener("click", () => {
        setTool(btn.dataset.tool);
        if (currentTool !== "select") {
            selectedObjectIndex = -1;
            objectControls.classList.add("d-none");
            redrawCanvas();
            updateObjectList();
        }
    });
});

fillButton.addEventListener("click", () => {
    isFilled = !isFilled;
    fillButton.classList.toggle("active");
    if (selectedObjectIndex !== -1) {
        objects[selectedObjectIndex].isFilled = isFilled;
        redrawCanvas();
        updateObjectList();
        saveState();
    }
});

colorPicker.addEventListener("input", (e) => {
    currentColor = e.target.value;
    if (selectedObjectIndex !== -1) {
        objects[selectedObjectIndex].color = currentColor;
        redrawCanvas();
    }
});
colorPicker.addEventListener("change", () => { if (selectedObjectIndex !== -1) saveState(); });

sizeSlider.addEventListener("input", (e) => {
    currentSize = e.target.value;
    sizeValueSpan.textContent = currentSize;
    if (selectedObjectIndex !== -1) {
        objects[selectedObjectIndex].size = currentSize;
        redrawCanvas();
    }
});
sizeSlider.addEventListener("change", () => { if (selectedObjectIndex !== -1) saveState(); });

function scaleSelectedObject(factor) {
    if (selectedObjectIndex === -1) return;
    const obj = objects[selectedObjectIndex];

    if (obj.type === "circle") {
        obj.radiusX *= factor;
        obj.radiusY *= factor;
    } else if (obj.type === "square") {
        const oldW = obj.width;
        const oldH = obj.height;
        obj.width *= factor;
        obj.height *= factor;
        obj.x -= (obj.width - oldW) / 2;
        obj.y -= (obj.height - oldH) / 2;
    } else if (obj.type === "star") {
        obj.radius *= factor;
    } else if (obj.type === "triangle") {
        const cx = (obj.x1 + obj.x2 + obj.x3) / 3;
        const cy = (obj.y1 + obj.y2 + obj.y3) / 3;
        obj.x1 = cx + (obj.x1 - cx) * factor;
        obj.y1 = cy + (obj.y1 - cy) * factor;
        obj.x2 = cx + (obj.x2 - cx) * factor;
        obj.y2 = cy + (obj.y2 - cy) * factor;
        obj.x3 = cx + (obj.x3 - cx) * factor;
        obj.y3 = cy + (obj.y3 - cy) * factor;
    } else if (obj.type === "freehand") {
        let minX = Infinity, minY = Infinity, maxX = -Infinity, maxY = -Infinity;
        obj.points.forEach(p => {
            if(p.x < minX) minX = p.x;
            if(p.x > maxX) maxX = p.x;
            if(p.y < minY) minY = p.y;
            if(p.y > maxY) maxY = p.y;
        });
        const cx = (minX + maxX) / 2;
        const cy = (minY + maxY) / 2;
        obj.points.forEach(p => {
            p.x = cx + (p.x - cx) * factor;
            p.y = cy + (p.y - cy) * factor;
        });
    }
    
    redrawCanvas();
    saveState();
}

scaleUpBtn.addEventListener("click", () => scaleSelectedObject(1.1));
scaleDownBtn.addEventListener("click", () => scaleSelectedObject(0.9));

widthInput.addEventListener("change", (e) => {
    const val = parseInt(e.target.value);
    if (val > 0) {
        canvas.width = val;
        formWidthInput.value = val;
        redrawCanvas();
    }
});

heightInput.addEventListener("change", (e) => {
    const val = parseInt(e.target.value);
    if (val > 0) {
        canvas.height = val;
        formHeightInput.value = val;
        redrawCanvas();
    }
});

clearButton.addEventListener("click", () => {
    objects = [];
    selectedObjectIndex = -1;
    objectControls.classList.add("d-none");
    redrawCanvas();
    updateObjectList();
    saveState();
});

undoButton.addEventListener("click", undo);
redoButton.addEventListener("click", redo);

saveForm.addEventListener("submit", (e) => {
    drawingContentInput.value = JSON.stringify(objects);
});

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
    const pos = getMousePos(e);
    startX = pos.x;
    startY = pos.y;
    
    if (currentTool === "select") {
        const index = getObjectAt(startX, startY);
        if (index !== -1) {
            selectObject(index);
            isDragging = true;
            lastMouseX = startX;
            lastMouseY = startY;
        } else {
            selectedObjectIndex = -1;
            objectControls.classList.add("d-none");
            redrawCanvas();
            updateObjectList();
        }
        return;
    }
    
    isDrawing = true;
    if (currentTool === "freehand") {
        const freehandObj = {
            type: "freehand",
            name: `Dibuix ${objects.length + 1}`,
            color: currentColor,
            size: currentSize,
            points: [{ x: startX, y: startY }],
            isFilled: isFilled
        };
        addObject(freehandObj);
    }
});

canvas.addEventListener("mousemove", (e) => {
    const pos = getMousePos(e);
    
    if (isDragging && currentTool === "select" && selectedObjectIndex !== -1) {
        const dx = pos.x - lastMouseX;
        const dy = pos.y - lastMouseY;
        const obj = objects[selectedObjectIndex];

        if (obj.type === "freehand") {
            obj.points.forEach(p => { p.x += dx; p.y += dy; });
        } else if (obj.type === "triangle") {
            obj.x1 += dx; obj.y1 += dy;
            obj.x2 += dx; obj.y2 += dy;
            obj.x3 += dx; obj.y3 += dy;
        } else {
            obj.x += dx;
            obj.y += dy;
        }

        lastMouseX = pos.x;
        lastMouseY = pos.y;
        redrawCanvas();
        return;
    }

    if (!isDrawing) return;
    
    if (currentTool === "freehand") {
        const currentFreehand = objects[objects.length - 1];
        currentFreehand.points.push(pos);
        
        ctx.strokeStyle = currentFreehand.color;
        ctx.lineWidth = currentFreehand.size;
        ctx.lineCap = "round";
        ctx.lineJoin = "round";
        
        ctx.beginPath();
        const prevPoint = currentFreehand.points[currentFreehand.points.length - 2] || {x: startX, y: startY};
        ctx.moveTo(prevPoint.x, prevPoint.y);
        ctx.lineTo(pos.x, pos.y);
        ctx.stroke();
        
    } else if (["triangle", "circle", "square", "star"].includes(currentTool)) {
        redrawCanvas();

        let previewObj = {
            type: currentTool,
            color: currentColor,
            size: currentSize,
            isFilled: isFilled
        };
        
        const width = pos.x - startX;
        const height = pos.y - startY;

        if (currentTool === "triangle") {
            Object.assign(previewObj, {
                x1: startX, y1: startY,
                x2: pos.x, y2: pos.y,
                x3: (2 * startX) - pos.x,
                y3: pos.y
            });
        } else if (currentTool === "circle") {
            Object.assign(previewObj, {
                x: startX + width / 2,
                y: startY + height / 2,
                radiusX: Math.abs(width / 2),
                radiusY: Math.abs(height / 2)
            });
        } else if (currentTool === "square") {
            Object.assign(previewObj, {
                x: startX,
                y: startY,
                width: width,
                height: height
            });
        } else if (currentTool === "star") {
            Object.assign(previewObj, {
                x: startX,
                y: startY,
                radius: Math.sqrt(width*width + height*height)
            });
        }

        drawObject(previewObj);
    }
});

canvas.addEventListener("mouseup", (e) => {
    if (isDragging) {
        isDragging = false;
        saveState();
        return;
    }

    if (!isDrawing) return;
    isDrawing = false;
    
    const pos = getMousePos(e);
    
    const newObj = {
        color: currentColor,
        size: currentSize,
        name: `${currentTool.charAt(0).toUpperCase() + currentTool.slice(1)} ${objects.length + 1}`,
        isFilled: isFilled
    };

    if (currentTool === "freehand") {
    } else if (currentTool === "triangle") {
        Object.assign(newObj, {
            type: "triangle",
            x1: startX,
            y1: startY,
            x2: pos.x,
            y2: pos.y,
            x3: (2 * startX) - pos.x,
            y3: pos.y
        });
        addObject(newObj);
    } else if (currentTool === "circle") {
        const width = pos.x - startX;
        const height = pos.y - startY;
        Object.assign(newObj, {
            type: "circle",
            x: startX + width / 2,
            y: startY + height / 2,
            radiusX: Math.abs(width / 2),
            radiusY: Math.abs(height / 2)
        });
        addObject(newObj);
    } else if (currentTool === "square") {
        Object.assign(newObj, {
            type: "square",
            x: startX,
            y: startY,
            width: pos.x - startX,
            height: pos.y - startY
        });
        addObject(newObj);
    } else if (currentTool === "star") {
        const radius = Math.sqrt(Math.pow(pos.x - startX, 2) + Math.pow(pos.y - startY, 2));
        Object.assign(newObj, {
            type: "star",
            x: startX,
            y: startY,
            radius: radius
        });
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
    if (isDragging) {
        isDragging = false;
        saveState();
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const existingContentEl = document.getElementById("existingDrawingContent");
    
    if (existingContentEl && existingContentEl.textContent.trim().length > 0) {
        try {
            const content = existingContentEl.textContent.trim();
            
            if (content && content !== "" && content !== "null") {
                objects = JSON.parse(content);
                redrawCanvas();
                updateObjectList();
                saveState();
            }
        } catch (e) {
            console.error("Error al carregar el dibuix existent:", e);
        }
    }
    updateUndoRedoButtons();
});