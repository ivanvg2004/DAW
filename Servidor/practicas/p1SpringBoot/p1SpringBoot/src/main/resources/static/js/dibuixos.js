document.addEventListener("DOMContentLoaded", () => {
    
    const baseURL = document.body.dataset.baseUrl || '';

    function procesarAccion(boton, urlAccion, mensajeConfirmacion) {
        if (mensajeConfirmacion && !confirm(mensajeConfirmacion)) {
            return;
        }

        const dibuixId = boton.dataset.id;
        const formData = new FormData();
        formData.append("id", dibuixId);

        fetch(`${baseURL}${urlAccion}`, {
            method: "POST",
            body: formData
        })
        .then(response => response.json())
        .then(result => {
            if (result.success) {
                document.getElementById(`fila-dibuix-${dibuixId}`).remove();
                
                if(document.querySelectorAll("tbody tr").length === 0){
                    location.reload();
                }
            } else {
                alert(result.message || "Error al procesar la acción.");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Ha ocurrido un error de red.");
        });
    }

    document.querySelectorAll(".btn-esborrar").forEach(btn => {
        btn.addEventListener("click", () => {
            procesarAccion(btn, "/esborrar", "Estàs segur que vols enviar aquest dibuix a la paperera?");
        });
    });

    document.querySelectorAll(".btn-restaurar").forEach(btn => {
        btn.addEventListener("click", () => {
            procesarAccion(btn, "/restaurar", "¿Vols restaurar aquest dibuix?");
        });
    });

    document.querySelectorAll(".btn-eliminar-def").forEach(btn => {
        btn.addEventListener("click", () => {
            procesarAccion(btn, "/eliminarDefinitiu", "ATENCIÓ: Això eliminarà el dibuix i tot el seu historial PER SEMPRE. Estàs segur?");
        });
    });

    document.querySelectorAll(".btn-clonar").forEach(btn => {
        btn.addEventListener("click", () => {
            procesarAccion(btn, "/clonar", "Vols crear una còpia d'aquest dibuix al teu perfil?");
        });
    });
});