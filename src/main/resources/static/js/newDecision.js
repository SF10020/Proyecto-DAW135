let id = null;
// recuperamos el id de la simulacion seleccionada
$(document).on('click', '.btnAddDecision', function () {
    id = $(this).data('id');
    console.log(id);
});

// recuperamos todo el formulario
document.getElementById("formSimulacion").addEventListener("submit", function (event) {
    event.preventDefault();

    const form = this;
    // agregamos el id de la simulacion
    const formData = new FormData(form);
    formData.append("idDecision", id);

    console.log(formData);

    // hacemos la peticion al backend
    fetch('/decisiones/crearDecision', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text || 'Error inesperado'); });
        }
        return response.text();
    })
    // mostramos modales segun convenga
    .then(data => {

        const modal = bootstrap.Modal.getInstance(document.getElementById('nuevaSimulacionModal'));
        modal.hide();
        form.reset();
        const modalExito = new bootstrap.Modal(document.getElementById('modalExito'));
        modalExito.show();
    })
    .catch(error => {
        console.error('Error al guardar la simulación:', error.message);
        document.getElementById('modalErrorBody').textContent = "Error al crear nueva decisión: " + error.message;
        const modalError = new bootstrap.Modal(document.getElementById('modalError'));
        modalError.show();
    });
});
