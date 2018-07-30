$( document ).ready(function(){
    $("#button-menu").sideNav();
    $('select').material_select();
    $('.modal').modal();
});

function sortTableASC() {
    alert("Função para ordenar a coluna de forma crescente");
}

function sortTableDESC() {
    alert("Função para ordenar a coluna de forma decrescente");
}

// Modals
function closeAddModal() {
    $('#modal-add-item').modal('close');
}
function closeEditModal() {
    $('#modal-edit-item').modal('close');
}
function closeDeleteModal() {
    $('#modal-delete-item').modal('close');
}