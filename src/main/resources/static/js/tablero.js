document.addEventListener("DOMContentLoaded", function () {
    const simulacionId = document.getElementById("simulacionId").value;
    const modal = new bootstrap.Modal(document.getElementById("modalAgregarTarea"));
    const form = document.getElementById("formAgregarTarea");

    // Cargar tareas dinámicamente
    fetch(`/api/tareas/simulacion/${simulacionId}`)
        .then(response => response.json())
        .then(tareas => {
            tareas.forEach(tarea => {
                const tarjeta = document.createElement('div');
                tarjeta.className = 'kanban-card mb-2 p-2 shadow-sm';
                tarjeta.setAttribute('draggable', 'true');
                tarjeta.setAttribute('data-id', tarea.id);
                tarjeta.innerHTML = `
                <div class="d-flex justify-content-between align-items-start">
                    <div class="flex-grow-1">
                        <h6 class="mb-1">${tarea.nombre}</h6>
                        <p class="mb-1">${tarea.descripcion || ''}</p>
                    </div>
                    <button type="button" class="btn btn-sm btn-outline-danger btn-eliminar-tarea" title="Eliminar tarea">
                        <i class="bi bi-trash"></i>
                    </button>
                </div>
            `;

                const estado = tarea.estado;
                let columnaId = "";

                if (estado === "To Do") columnaId = "todo";
                else if (estado === "Doing") columnaId = "doing";
                else if (estado === "Done") columnaId = "done";
                else if (estado === "Backlog") columnaId = "backlog";
                else if (estado === "Sprint") columnaId = "sprint";
                else if (estado === "Requisitos") columnaId = "requisitos";
                else if (estado === "Diseño") columnaId = "diseno";
                else if (estado === "Implementación") columnaId = "implementacion";
                else if (estado === "Pruebas") columnaId = "pruebas";
                else if (estado === "Mantenimiento") columnaId = "mantenimiento";


                const columna = document.getElementById(columnaId);
                if (columna) {
                    columna.insertBefore(tarjeta, columna.querySelector('.add-task-btn'));
                }
            });
        });


    // Manejo de creación de tarea desde el modal
    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const nombre = document.getElementById("nombreTarea").value;
        const descripcion = document.getElementById("descripcionTarea").value;
        const estado = document.getElementById("estadoTarea").value;

        const nuevaTarea = {
            nombre,
            descripcion,
            estado,
            simulacion: { id: simulacionId } // ⚠️ Asegúrate que simulacionId esté disponible arriba
        };

        fetch("/api/tareas", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(nuevaTarea)
        })
            .then(res => {
                if (res.ok) {
                    form.reset();
                    modal.hide();
                    location.reload(); // ✅ evita duplicación visual
                } else {
                    alert("Error al crear la tarea.");
                }
            });
    });

    // Botón Agregar Tarea
    document.querySelectorAll('.add-task-btn').forEach(btn => {
        btn.addEventListener('click', (e) => {
            const columnaId = e.currentTarget.closest('.scrum-column, .kanban-column, .waterfall-column').id;

            const estado =
                columnaId === 'backlog' ? 'Backlog' :
                    columnaId === 'sprint' ? 'Sprint' :
                        columnaId === 'todo' ? 'To Do' :
                            columnaId === 'doing' ? 'Doing' :
                                columnaId === 'done' ? 'Done' :
                                    columnaId === 'requisitos' ? 'Requisitos' :
                                        columnaId === 'diseno' ? 'Diseño' :
                                            columnaId === 'implementacion' ? 'Implementación' :
                                                columnaId === 'pruebas' ? 'Pruebas' :
                                                    columnaId === 'mantenimiento' ? 'Mantenimiento' :
                                                        'Requisitos'; // fallback para Waterfall

            document.getElementById('estadoTarea').value = estado;
            modal.show();
        });
    });

    // Inicializar SortableJS
    const columns = ['backlog', 'sprint', 'todo', 'doing', 'done', 'requisitos', 'diseno', 'implementacion', 'pruebas', 'mantenimiento'];
    columns.forEach(columnId => {
        const colElement = document.getElementById(columnId);
        if (colElement) {
            new Sortable(colElement, {
                group: 'scrum',
                animation: 150,
                ghostClass: 'ghost',
                onEnd: function (evt) {
                    const item = evt.item;
                    const col = evt.to;
                    const tareaId = item.getAttribute('data-id');

                    const nuevoEstado =
                        col.id === 'backlog' ? 'Backlog' :
                            col.id === 'sprint' ? 'Sprint' :
                                col.id === 'todo' ? 'To Do' :
                                    col.id === 'doing' ? 'Doing' :
                                        col.id === 'done' ? 'Done' :
                                            col.id === 'requisitos' ? 'Requisitos' :
                                                col.id === 'diseno' ? 'Diseño' :
                                                    col.id === 'implementacion' ? 'Implementación' :
                                                        col.id === 'pruebas' ? 'Pruebas' :
                                                            col.id === 'mantenimiento' ? 'Mantenimiento' :
                                                                'Requisitos'; // fallback Waterfall

                    fetch(`/api/tareas/${tareaId}/estado`, {
                        method: 'PUT',
                        headers: { 'Content-Type': 'application/json' },
                        body: JSON.stringify({ estado: nuevoEstado })
                    }).then(response => {
                        if (!response.ok) {
                            alert('Error al actualizar el estado de la tarea');
                        }
                    });
                }
            });
        }
    });

    // Mensaje de confirmación visual para el botón "Guardar Cambios"
    document.getElementById("guardarCambiosBtn").addEventListener("click", function () {
        const toast = document.createElement('div');
        toast.className = 'alert alert-success position-fixed bottom-0 end-0 m-4 shadow';
        toast.role = 'alert';
        toast.style.zIndex = 9999;
        toast.innerText = '✅ Los cambios del tablero se han guardado correctamente.';
        document.body.appendChild(toast);
        setTimeout(() => { toast.remove(); }, 3000);
    });
});

// Eliminar tarea con botón
let tareaAEliminar = null;
const modalEliminar = new bootstrap.Modal(document.getElementById('modalConfirmarEliminacion'));

document.addEventListener('click', function (e) {
    if (e.target.closest('.btn-eliminar-tarea')) {
        const tarjeta = e.target.closest('.kanban-card');
        tareaAEliminar = tarjeta;
        modalEliminar.show();
    }
});

document.getElementById("btnConfirmarEliminar").addEventListener("click", function () {
    if (tareaAEliminar) {
        const tareaId = tareaAEliminar.getAttribute('data-id');

        fetch(`/api/tareas/${tareaId}`, {
            method: 'DELETE'
        }).then(res => {
            if (res.ok) {
                tareaAEliminar.remove();
            } else {
                alert("Error al eliminar la tarea.");
            }
            modalEliminar.hide();
            tareaAEliminar = null;
        });
    }
});