$(document).ready(function () {
    $("#button-menu").sideNav();
    $('select').material_select();
});

function sortTableASC() {
    alert("Função para ordenar a coluna de forma crescente");
}

function sortTableDESC() {
    alert("Função para ordenar a coluna de forma decrescente");
}

var seqServico;

function salvaSeqServico(ASeqServico) {
    seqServico = ASeqServico;
}
//
// INSERÇÃO DE ITEM
//

// método para gravação dos dados inseridos
function saveInsertDialog() {
    var nroQuarto = localStorage.getItem("nroQuarto");
    var seqHospedagem = localStorage.getItem("seqHospedagem");

    // a var dados contém os dados dos inputs no formulário
    var dados = $("#frmInsertItem").serialize();
    
    alert("dados: " + dados);

    // envia a requisição pro servlet
    $.ajax({
        url: "http://localhost:8080/cefet-lpii-tp2/despesas",
        type: "POST",
        data: dados + "&operacaoItem=2" + "&nroQuarto="+nroQuarto + "&seqHospedagem="+seqHospedagem + "&seqServico="+seqServico,
        // mostra mensagem pro usuário
        success: function (data) {
            alert(data.mensagem);
            window.location.replace("http://localhost:8080/cefet-lpii-tp2/view/conta-detalhes.jsp");
        },
        error: function (data) {
            alert("Não foi possível inserir o registro");
            window.location.replace("http://localhost:8080/cefet-lpii-tp2/view/conta-detalhes.jsp");
        }
    });
}

// método para fechar o modal
function cancelInsertDialog() {
    $('#modal-add-item').modal('close');
}

//
// ALTERAÇÃO DOS DADOS DE UM ITEM
//

// método para exibir o modal com os dados
function showEditDialog(ANroQuarto, ASeqHospedagem, ASeqServico, AQtdConsumo) {
    // envia a requisição para o servlet
    $.ajax({
        url: "http://localhost:8080/cefet-lpii-tp2/despesas",
        type: "POST",
        // manda como parâmetro de operação 1 --> retornarDadosRegistro
        data: "operacaoItem=1" + "&nroQuarto=" + ANroQuarto + "&seqHospedagem=" + ASeqHospedagem
                + "&seqServico=" + ASeqServico + +"&qtdConsumo=" + AQtdConsumo,
        success: function (responseText) {
            // abre o modal
            $('#modal-edit-item').modal('open');
            $("#frmEditItem #qtdConsumo").val(responseText.qtdConsumo);
        }
    });
}

// método para gravação dos dados alterados
function saveEditDialog() {
    var dados = $("#frmEditItem").serialize();

    $.ajax({
        url: "http://localhost:8080/cefet-lpii-tp2/despesas",
        type: "POST",
        data: dados,
        // mostra mensagem pro usuário
        success: function (data) {
            alert(data.mensagem);
        },
        error: function (data) {
            if (data.mensagem == null) {
                alert("Não foi possível alterar o registro");
            } else {
                alert(data.mensagem);
            }
        }
    });
}

// método para fechar o modal
function cancelEditDialog() {
    $('#modal-edit-item').modal('close');
}

//
// EXCLUSÃO DE ITEM
//

// método para exibir modal de exclusão
function showDeleteDialog() {
    $.ajax({
        url: "http://localhost:8080/cefet-lpii-tp2/despesas",
        type: "POST",
        data: "operacaoItem=1",
        success: function (responseText) {
            $('#modal-delete-item').modal('open');
        }
    });
}

// método para execução da exclusão
function executeDeleteDialog() {
    var dados = $("#frmDeleteItem").serialize();

    $.ajax({
        url: "http://localhost:8080/cefet-lpii-tp2/despesas",
        type: "POST",
        data: dados,
        // mostra mensagem pro usuário
        success: function (data) {
            alert(data.mensagem);
        },
        error: function (data) {
            if (data.mensagem == null) {
                alert("Não foi possível excluir o registro");
            } else {
                alert(data.mensagem);
            }
        }
    });
}

// método para fechar o modal
function cancelDeleteDialog() {
    $('#modal-delete-item').modal('close');
}
