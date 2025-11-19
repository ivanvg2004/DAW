document.addEventListener("DOMContentLoaded", () => {
    const canvas = document.getElementById("readOnlyCanvas");
    if (!canvas) return;
    
    const ctx = canvas.getContext("2d");
    const jsonDataElement = document.getElementById("jsonData");

    if (!jsonDataElement || !jsonDataElement.textContent) {
        console.error("No s'ha trobat el contingut del dibuix.");
        return;
    }

    try {
        const objects = JSON.parse(jsonDataElement.textContent);
        objects.forEach(obj => {
            drawObject(ctx, obj);
        });
    } catch (e) {
        console.error("Error al processar el JSON del dibuix:", e);
    }
});

function drawObject(ctx, obj) {
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
            if (obj.isFilled) ctx.fill(); else ctx.stroke();
            break;
        case "circle":
            ctx.ellipse(obj.x, obj.y, obj.radiusX, obj.radiusY, 0, 0, Math.PI * 2);
            if (obj.isFilled) ctx.fill(); else ctx.stroke();
            break;
        case "square":
            ctx.rect(obj.x, obj.y, obj.width, obj.height);
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