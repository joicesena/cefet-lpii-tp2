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

var codCPF;

function salvaCPF(ACodCPF) {
    codCPF = ACodCPF;
}

function executaCheckIn() {
    // a var dados contém os dados dos inputs no formulário
    var dados = $( "#frmCheckIn" ).serialize();
 
    // envia a requisição pro servlet
    $.ajax({
            url: "http://localhost:8080/cefet-lpii-tp2/check-in",
            type: "POST",
            // operação 2 --> efetuar o check-in
            data: dados + "&operacaoRegistro=2" + "&codCPF="+codCPF,
            // mostra mensagem pro usuário
            success: function(data) {
                alert(data.mensagem);
                window.location.replace("http://localhost:8080/cefet-lpii-tp2/view/quartos-estados.jsp");
            },
            error: function(data) {
                if (data.mensagem == null) {
                        alert("Não foi possível inserir o registro");
                } else {
                        alert(data.mensagem);
                }
                window.location.replace("http://localhost:8080/cefet-lpii-tp2/view/quartos-estados.jsp");
            }
    });
}