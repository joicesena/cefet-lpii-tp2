$( document ).ready(function(){
    $("#button-menu").sideNav();
    $('select').material_select();
});

function sortTableASC() {
    alert("Função para ordenar a coluna de forma crescente");
}

function sortTableDESC() {
    alert("Função para ordenar a coluna de forma decrescente");
}

function checkin() {
    alert("Redireciona para a tela de check-in");
}

function checkout() {
    alert("Redireciona para a tela de check-out");
}

function info() {
    alert("Redireciona para a tela de visualização de detalhes da conta");
}