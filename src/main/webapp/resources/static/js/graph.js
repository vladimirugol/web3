
document.addEventListener('DOMContentLoaded', function () {
    const canvas = document.getElementById('graph');
    if (!canvas) {
        return;
    }
    const ctx = canvas.getContext('2d');
    const width = canvas.width;
    const height = canvas.height;

    const AXIS_COLOR = '#000000';
    const AREA_COLOR = '#e553cd';
    const HIT_COLOR = '#0cb315';
    const MISS_COLOR = '#e80505';

    function getCurrentR() {
        const rElement = document.getElementById('mainForm:r-hidden');
        if (rElement && rElement.value) {
            const r = parseFloat(rElement.value);
            return isNaN(r) || r <= 0 ? null : r;
        }
        return null;
    }

    function defaultR() {
        const rElement = document.getElementById('mainForm:r-hidden');
        if (rElement && !rElement.value) {
            rElement.value = '1';
        }
    }


    window.drawGraph = function() {
        const R = getCurrentR();
        ctx.clearRect(0, 0, width, height);

        if (R === null) {
            return;
        }

        const scale = width / (3.2 * R);
        const centerX = width / 2;
        const centerY = height / 2;

        drawArea(ctx, R, scale, centerX, centerY);
        drawAxes(R, scale, centerX, centerY);
        drawPoints(ctx, scale, centerX, centerY);
    }

    function drawArea(ctx, R, scale, centerX, centerY) {
        ctx.fillStyle = AREA_COLOR;
        ctx.strokeStyle = AREA_COLOR;
        ctx.beginPath();
        ctx.rect(centerX - (R / 2) * scale, centerY - R * scale, (R/2) * scale, R * scale);
        ctx.moveTo(centerX, centerY);
        ctx.lineTo(centerX + (R / 2) * scale, centerY);
        ctx.lineTo(centerX, centerY - (R / 2) * scale);
        ctx.closePath();

        ctx.moveTo(centerX, centerY);
        ctx.arc(centerX, centerY, (R / 2) * scale, Math.PI / 2, Math.PI, false);

        ctx.fill();
        ctx.stroke();
    }

    function drawAxes(R, scale, centerX, centerY) {
        ctx.strokeStyle = AXIS_COLOR;
        ctx.fillStyle = AXIS_COLOR;
        ctx.font = '14px Arial';
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.beginPath();
        ctx.moveTo(0, centerY);
        ctx.lineTo(width, centerY);
        ctx.lineTo(width - 10, centerY - 5);
        ctx.moveTo(width, centerY);
        ctx.lineTo(width - 10, centerY + 5);
        ctx.stroke();
        ctx.fillText('x', width - 15, centerY - 20);

        ctx.beginPath();
        ctx.moveTo(centerX, height);
        ctx.lineTo(centerX, 0);
        ctx.lineTo(centerX - 5, 10);
        ctx.moveTo(centerX, 0);
        ctx.lineTo(centerX + 5, 10);
        ctx.stroke();
        ctx.fillText('y', centerX + 20, 15);

        const labels = [-R, -R / 2, R / 2, R];
        labels.forEach(label => {
            if (label === 0) return;
            const labelText = Number.isInteger(label) ? label.toString() : label.toFixed(1)
            const xPos = centerX + label * scale;
            ctx.beginPath();
            ctx.moveTo(xPos, centerY - 5);
            ctx.lineTo(xPos, centerY + 5);
            ctx.stroke();
            ctx.fillText(labelText, xPos, centerY + 20);
            const yPos = centerY - label * scale;
            ctx.beginPath();
            ctx.moveTo(centerX - 5, yPos);
            ctx.lineTo(centerX + 5, yPos);
            ctx.stroke();
            ctx.fillText(labelText, centerX - 30, yPos);
        });
    }

    function drawPoints(ctx, scale, centerX, centerY) {
        const tableRows = document.querySelectorAll('#resultsTable .ui-datatable-data tr');
        tableRows.forEach(row => {
            const cells = row.getElementsByTagName('td');
            if (cells.length < 4) return;

            const x = parseFloat(cells[0].innerText.replace(',', '.'));
            const y = parseFloat(cells[1].innerText.replace(',', '.'));
            const hit = cells[3].innerText.trim() === 'true';

            if (isNaN(x) || isNaN(y)) return;

            ctx.fillStyle = hit ? HIT_COLOR : MISS_COLOR;
            ctx.beginPath();
            const canvasX = centerX + x * scale;
            const canvasY = centerY - y * scale;
            ctx.arc(canvasX, canvasY, 4, 0, 2 * Math.PI);
            ctx.fill();
        });
    }

    function handleCanvasClick(event) {
        const R = getCurrentR();
        if (R === null) {
            return;
        }
        let pos = getCursorPosition(canvas, event);
        document.getElementById('mainForm:x-hidden').value = pos.x;
        document.getElementById('mainForm:y-input').value = pos.y;
        const sendButton = document.querySelector('#mainForm button[type="submit"]');
        if (sendButton) {
            sendButton.click();
        }
    }

    function getCursorPosition(canvas, event) {
        const rect = canvas.getBoundingClientRect();
        const R = getCurrentR();

        if (R === null) {
            console.warn("R is not defined, cannot get cursor position accurately.");
            return { x: 0, y: 0 };
        }
        const canvasAttrWidth = canvas.width;
        const canvasAttrHeight = canvas.height;
        const scaleX_cssToAttr = canvasAttrWidth / rect.width;
        const scaleY_cssToAttr = canvasAttrHeight / rect.height;
        const centerX = canvasAttrWidth / 2;
        const centerY = canvasAttrHeight / 2;
        const clientX_css = event.clientX - rect.left;
        const clientY_css = event.clientY - rect.top;
        const canvasPxX = clientX_css * scaleX_cssToAttr;
        const canvasPxY = clientY_css * scaleY_cssToAttr;
        const graphScale = canvasAttrWidth / (3.2 * R);
        const graphX = (canvasPxX - centerX) / graphScale;
        const graphY = (centerY - canvasPxY) / graphScale;

        return {x: graphX, y: graphY};
    }

    canvas.addEventListener('click', handleCanvasClick);
    defaultR();
    drawGraph();
});