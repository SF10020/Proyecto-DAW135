// Pass the project ID to the modal when the delete button is clicked
const deleteProyectoModal = document.getElementById('deleteProyectoModal');
deleteProyectoModal.addEventListener('show.bs.modal', function (event) {
    const button = event.relatedTarget;
    const projectId = button.getAttribute('data-id');
    const inputField = document.getElementById('deleteProyectoId');
    inputField.value = projectId;
});