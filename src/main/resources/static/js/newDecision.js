document.addEventListener('DOMContentLoaded', () => {
    const selectProyecto = document.getElementById('proyectoSelect');
    const btnNuevaSimulacion = document.querySelector('.btnAddDecision');
    const inputProyectoId = document.getElementById('proyectoIdSeleccionado');

    btnNuevaSimulacion.addEventListener('click', () => {
        const proyectoId = selectProyecto.value;

        if (!proyectoId) {
            const errorModal = new bootstrap.Modal(document.getElementById('modalSeleccionProyecto'));
            errorModal.show();
            return;
        }

        inputProyectoId.value = proyectoId;

        const modal = new bootstrap.Modal(document.getElementById('nuevaSimulacionModal'));
        modal.show();
    });
});



document.getElementById("formSimulacion").addEventListener("submit", function (event) {
    event.preventDefault();

    const form = this;
    const formData = new FormData(form);

    fetch('/api/simulaciones/decisiones/crear', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text); });
            }
            return response.text();
        })
        .then(responseText => {
            // Mostrar modal de éxito
            const modalExito = new bootstrap.Modal(document.getElementById('modalExito'));
            modalExito.show();

            // Limpiar formulario
            form.reset();

            // Cerrar modal de creación
            const modalCrear = bootstrap.Modal.getInstance(document.getElementById('nuevaSimulacionModal'));
            if (modalCrear) {
                modalCrear.hide();
            }
        })
        .catch(err => {
            // Mostrar mensaje de error dinámicamente
            const modalErrorBody = document.getElementById("modalErrorBody");
            modalErrorBody.textContent = err.message || "Ocurrió un error al guardar la decisión.";

            const modalError = new bootstrap.Modal(document.getElementById('modalError'));
            modalError.show();
        });
});
