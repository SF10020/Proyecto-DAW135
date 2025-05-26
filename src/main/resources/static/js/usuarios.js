    document.addEventListener('DOMContentLoaded', function () {
        const modalCrear = new bootstrap.Modal(document.getElementById('modalCrearUsuario'));
        const modalEditar = new bootstrap.Modal(document.getElementById('modalEditarUsuario'));
        document.querySelectorAll('.btn-abrir-crear-usuario').forEach(boton => {
            boton.addEventListener('click', function () {
                modalCrear.show();
            });
        });

        // Abrir modal de EDITAR usuario
        document.querySelectorAll('.btn-editar-usuario').forEach(boton => {
            boton.addEventListener('click', function (e) {
                e.preventDefault();
                document.getElementById('editarUsuarioId').value = this.dataset.id;
                document.getElementById('editarUsuarioNombre').value = this.dataset.nombre;
                document.getElementById('editarUsuarioCorreo').value = this.dataset.correo;

                modalEditar.show();
            });
        });
        document.querySelectorAll('.btn-eliminar-usuario').forEach(boton => {
            boton.addEventListener('click', function () {
                document.getElementById('eliminarUsuarioId').value = this.dataset.id;
                const modalEliminar = new bootstrap.Modal(document.getElementById('modalEliminarUsuario'));
                modalEliminar.show();
            });
        });
    });