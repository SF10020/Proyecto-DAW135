document.addEventListener('DOMContentLoaded', function() {
    console.log("simulaciones.js: Script cargado y DOM listo.");

    // --- Banner tipo alerta  ---
    function mostrarAlertaExito(mensaje) {
        const alerta = document.getElementById("alerta-exito");
        document.getElementById("mensaje-alerta").textContent = mensaje;
        alerta.style.display = "flex";
        clearTimeout(alerta._timeoutId);
        alerta._timeoutId = setTimeout(() => {
            alerta.style.display = "none";
        }, 2500);
    }

    window.cerrarAlertaExito = function() {
        const alerta = document.getElementById("alerta-exito");
        alerta.style.display = "none";
        clearTimeout(alerta._timeoutId);
    };

    const editSimulacionModal = document.getElementById('editSimulacionModal');
    const saveSimulacionChangesBtn = document.getElementById('saveSimulacionChanges');

    if (!editSimulacionModal) {
        console.error("simulaciones.js: Elemento 'editSimulacionModal' no encontrado.");
        return;
    }
    if (!saveSimulacionChangesBtn) {
        console.error("simulaciones.js: Botón 'saveSimulacionChanges' no encontrado.");
        return;
    }

    editSimulacionModal.addEventListener('show.bs.modal', function (event) {
        console.log("Modal de edición se va a mostrar.");
        const button = event.relatedTarget; 
        const simulacionId = button.getAttribute('data-simulacion-id');
        const tiempo = button.getAttribute('data-tiempo');
        const costo = button.getAttribute('data-costo');
        const calidad = button.getAttribute('data-calidad');

        console.log(`Datos del botón (precarga): ID=${simulacionId}, Tiempo=${tiempo}, Costo=${costo}, Calidad=${calidad}`);

        document.getElementById('simulacionId').value = simulacionId;
        document.getElementById('modalTiempoEstimado').value = tiempo;
        document.getElementById('modalCostoEstimado').value = costo;
        document.getElementById('modalCalidadEstimada').value = calidad;
        console.log("Formulario del modal precargado.");
    });

    saveSimulacionChangesBtn.addEventListener('click', function() {
        console.log("Botón 'Guardar Cambios' clickeado. Iniciando actualización.");

        const simulacionId = document.getElementById('simulacionId').value;
        const tiempoEstimado = document.getElementById('modalTiempoEstimado').value;
        const costoEstimado = document.getElementById('modalCostoEstimado').value;
        const calidadEstimada = document.getElementById('modalCalidadEstimada').value;

        if (!simulacionId || !tiempoEstimado || !costoEstimado || !calidadEstimada) {
            mostrarAlertaExito('Por favor, complete todos los campos.');
            console.warn('Faltan datos para la actualización.');
            return;
        }

        const data = {
            id: parseInt(simulacionId),
            tiempoEstimado: parseInt(tiempoEstimado),
            costoEstimado: parseFloat(costoEstimado), 
            calidadEstimada: parseInt(calidadEstimada)
        };
        console.log('Datos a enviar (JSON):', JSON.stringify(data));

        fetch('/api/simulaciones/actualizar', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            console.log('Respuesta cruda del servidor:', response);
            if (!response.ok) {
                return response.json().catch(() => {
                    throw new Error(`Error HTTP: ${response.status} ${response.statusText || 'Error desconocido'}. No JSON response.`);
                }).then(errorData => {
                    throw new Error(errorData.message || `Error en el servidor: ${response.status}`);
                });
            }
            return response.json();
        })
        .then(updatedSimulacion => {
            console.log('Simulación recibida del backend:', updatedSimulacion);

            const row = document.querySelector(`tr[data-simulacion-id="${simulacionId}"]`);
            if (row) {
                console.log('Fila de la tabla encontrada. Actualizando contenido...');
                const tiempoCell = row.querySelector('.tiempo-estimado');
                const costoCell = row.querySelector('.costo-estimado');
                const calidadCell = row.querySelector('.calidad-estimada');

                // Tiempo
                if (tiempoCell) {
                    tiempoCell.textContent = (updatedSimulacion.tiempoEstimado ?? "—");
                    console.log('Tiempo actualizado a:', updatedSimulacion.tiempoEstimado);
                }

                // Costo
                if (costoCell) {
                    let costo = updatedSimulacion.costoEstimado;
                    console.log('Tipo de costoEstimado:', typeof costo, costo);
                    if (typeof costo === 'object' && costo !== null) {
                        if ('value' in costo) costo = costo.value;
                        else if ('amount' in costo) costo = costo.amount;
                        else if ('_value' in costo) costo = costo._value;
                        else costo = Object.values(costo)[0];
                    }
                    if (costo === undefined || costo === null || costo === "") {
                        costoCell.textContent = "—";
                    } else if (isNaN(parseFloat(costo))) {
                        costoCell.textContent = costo;
                    } else {
                        costoCell.textContent = parseFloat(costo).toFixed(2);
                    }
                    console.log('Costo actualizado a:', costo);
                } else {
                    console.warn('Celda .costo-estimado no encontrada en la fila.');
                }

                // Calidad
                if (calidadCell) {
                    let calidad = updatedSimulacion.calidadEstimada;
                    console.log('Tipo de calidadEstimada:', typeof calidad, calidad);
                    if (calidad === undefined || calidad === null || calidad === "") {
                        calidadCell.textContent = "—";
                    } else {
                        calidadCell.textContent = calidad;
                    }
                    console.log('Calidad actualizada a:', calidad);
                } else {
                    console.warn('Celda .calidad-estimada no encontrada en la fila.');
                }

                mostrarAlertaExito('Simulación actualizada exitosamente!');
            } else {
                console.warn('Fila de la tabla NO encontrada para el ID:', simulacionId);
                mostrarAlertaExito('Simulación actualizada exitosamente, pero no se pudo actualizar la tabla automáticamente. Se recargará la página.');
                setTimeout(() => location.reload(), 2500);
            }

            // Cerrar el modal
            const modalInstance = bootstrap.Modal.getInstance(editSimulacionModal);
            if (modalInstance) {
                modalInstance.hide();
                console.log("Modal cerrado.");
            } else {
                console.warn("No se pudo obtener la instancia del modal para cerrarlo.");
            }
        })
        .catch(error => {
            console.error('Error durante la operación de actualización:', error);
            mostrarAlertaExito('Hubo un error al actualizar la simulación: ' + error.message);
        });
    });
});