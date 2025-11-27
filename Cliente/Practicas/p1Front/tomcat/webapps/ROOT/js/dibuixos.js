document.addEventListener("DOMContentLoaded", () => {
    
    const baseURL = document.body.dataset.baseUrl || '';
    const botonsEsborrar = document.querySelectorAll(".btn-esborrar");
    
    botonsEsborrar.forEach(boto => {
        boto.addEventListener("click", () => {
            
            if (confirm("Estas segur que vols esborrar aquest dibuix? Aquesta accio no es pot desfer.")) {
                
                const dibuixId = boto.dataset.id;
                
                const formData = new FormData();
                formData.append("id", dibuixId);

                fetch(`${baseURL}/esborrar`, {
                    method: "POST",
                    body: formData
                })
                .then(response => response.json().then(data => ({ok: response.ok, data})))
                .then(result => {
                    if (result.ok && result.data.success === true) {
                        document.getElementById(`fila-dibuix-${dibuixId}`).remove();
                    } else {
                        alert(result.data.message || "No s'ha pogut esborrar el dibuix.");
                    }
                })
                .catch(error => {
                    console.error("Error en la petició:", error);
                    alert("Ha ocorregut un error de xarxa.");
                });
            }
        });
    });
});