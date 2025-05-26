const selectProyect = document.getElementById('proyectoSelect')
selectProyect.addEventListener('change', () => {
    const proyectoId = selectProyect.value
    fetch('/decisiones/filter?proyectoId=' + proyectoId)
        .then(response => {

            return response.json();
        })
        .then(data => {
            let tableBody = document.getElementById("decisionesTableBody");
            tableBody.innerHTML = '';
            const btnVerTareas = document.getElementById("btnVerTareas");

            if (data.length > 0) {
                let idSimulacion = data[0].idSimulacion;
                btnVerTareas.classList.remove("d-none");
                btnVerTareas.setAttribute("data-simulacion-id", idSimulacion);
                data.forEach(decision => {
                    let row = document.createElement("tr");
                    row.innerHTML = `
                                <td>${decision.idDecision}</td>
                                <td>${decision.nombreProyecto}</td>
                                <td>${decision.descripcion}</td>
                                <td>
                                    <button type="button"
                                    class="btn btn-sm btn-outline-danger btnDeleteDecision"
                                    th:data-id=${decision.idDecision}
                                    data-bs-toggle="modal"
                                    data-bs-target="#confirmDeleteModal"
                                    title="Eliminar">
                                <i class="bi bi-trash"></i>
                            </button>
                              
                            </td>
                            
                            `;
                    tableBody.appendChild(row);
                });


            } else {
                btnVerTareas.classList.add("d-none");
                btnVerTareas.removeAttribute("data-simulacion-id");
            }
        })
        .catch(error => console.error('Error:', error));
})




let idToDelete = null;

$(document).on('click', '.btnDeleteDecision', function () {
    idToDelete = $(this).data('id');
    const modal = new bootstrap.Modal(document.getElementById('confirmDeleteModal'));
    modal.show();
});


$('#btnConfirmDelete').on('click', function () {
    if (!idToDelete) return;

    fetch(`/decisiones/eliminar/${idToDelete}`, {
        method: 'DELETE'
    })
        .then(res => {
            if (res.ok) {
                location.reload();
            } else {
                alert("Error al eliminar la decisión.");
            }
        });
});

const boton = document.getElementById('btnVerTareas');
boton.addEventListener('click', async function () {
    const idSimulacion = boton.dataset.simulacionId;
    const cuerpoTabla = document.getElementById('tareasTableBody');
    try {
        const response = await fetch(`/api/tareas/simulacion/${idSimulacion}`);
        const tareas = await response.json();

        cuerpoTabla.innerHTML = '';
        if (tareas.length === 0) {
            cuerpoTabla.innerHTML = `<tr><td colspan="5" class="text-center">No hay tareas registradas</td></tr>`;
            return;
        }

        tareas.forEach(tarea => {
            const row = `
                    <tr>
                        <td>${tarea.id}</td>
                        <td>${tarea.descripcion}</td>
                        <td>${tarea.estado}</td>
                        <td>${tarea.fechaCreacion.replace('T', ' ').substring(0, 16)}</td>
                        <td>${tarea.nombre}</td>
                    </tr>
                `;
            cuerpoTabla.insertAdjacentHTML('beforeend', row);
        });

    } catch (error) {
        console.error('Error al cargar tareas:', error);
        cuerpoTabla.innerHTML = `<tr><td colspan="5" class="text-center text-danger">Error al cargar las tareas</td></tr>`;
    }
})