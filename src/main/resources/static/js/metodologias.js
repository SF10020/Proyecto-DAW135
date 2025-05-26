document.addEventListener('DOMContentLoaded', function () {
    const editarModal = document.getElementById('modalEditarMetodologia');

    editarModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const id = button.getAttribute('data-id');
        const nombre = button.getAttribute('data-nombre');

        document.getElementById('editIdMetodologia').value = id;
        document.getElementById('editNombreMetodologia').value = nombre;
    });
});

document.addEventListener('DOMContentLoaded', () => {
    const modalProyectos = document.getElementById('modalProyectosMetodologia');

    modalProyectos.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const metodologiaId = button.getAttribute('data-id');
        const metodologiaNombre = button.getAttribute('data-nombre');

        document.getElementById('tituloMetodologia').textContent = metodologiaNombre;

        fetch(`/proyectos/por-metodologia?id=${metodologiaId}`)
            .then(response => response.json())
            .then(data => {
                const tbody = document.getElementById('tablaProyectosBody');
                tbody.innerHTML = '';

                if (data.length === 0) {
                    tbody.innerHTML = '<tr><td colspan="3" class="text-center">No hay proyectos asociados.</td></tr>';
                    return;
                }

                data.forEach(proyecto => {
                    const fila = `<tr>
                        <td>${proyecto.nombre}</td>
                        <td>${proyecto.usuario ? proyecto.usuario.nombre : 'Sin asignar'}</td>
                        <td>${proyecto.fechaCreacion ? proyecto.fechaCreacion.replace('T', ' ').substring(0, 16) : ''}</td>
                    </tr>`;
                    tbody.innerHTML += fila;
                });
            })
            .catch(error => {
                console.error('Error al obtener proyectos:', error);
            });
    });
});