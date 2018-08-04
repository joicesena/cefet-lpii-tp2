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

//
// INSERÇÃO DE ITEM
//

// método para gravação dos dados inseridos
function saveInsertDialog () {
	// a var dados contém os dados dos inputs no formulário
	var dados = $( "#frmInsertItem" ).serialize();
	
	// envia a requisição pro servlet
	$.ajax({
		url: "http://localhost:8080/cefet-lpii-tp2/hospede",
		type: "POST",
		data: dados,
		// mostra mensagem pro usuário
		success: function(data) {
			alert(data.mensagem);
		},
		error: function(data) {
			if (data.mensagem == null) {
				alert("Não foi possível inserir o registro");
			} else {
				alert(data.mensagem);
			}
		}
	});
}

// método para fechar o modal
function cancelInsertDialog () {
    $('#modal-add-item').modal('close');
}

//
// ALTERAÇÃO DOS DADOS DE UM ITEM
//

// método para exibir o modal com os dados
function showEditDialog (ACodCPF) {
	// envia a requisição para o servlet
	$.ajax({
		url: "http://localhost:8080/cefet-lpii-tp2/hospede",
		type: "POST",
		// manda como parâmetro de operação 1 --> retornarDadosRegistro
		data: "operacaoItem=1" + "&codCPF="+ACodCPF,
		success: function(responseText){
			// abre o modal
			$('#modal-edit-item').modal('open');
//			$('#frmEditItem').form('load', responseText);
			
			// modifica o valor dos inputs no formulário para os dados existentes
			$("#frmEditItem #codCPF").val(responseText.codCPF);
			$("#frmEditItem #nomHospede").val(responseText.nomHospede);
			$("#frmEditItem #desEmail").val(responseText.desEmail);
			$("#frmEditItem #desTelefone").val(responseText.desTelefone);
		}
	});
}

// método para gravação dos dados alterados
function saveEditDialog () { 
	var dados = $( "#frmEditItem" ).serialize();
	
	$.ajax({
		url: "http://localhost:8080/cefet-lpii-tp2/hospede",
		type: "POST",
		data: dados,
//		success: function(data) {
//			var rst = JSON.parse(data);
//			alert(rst.mensagem);
//		}
		// mostra mensagem pro usuário
		success: function(data) {
			alert(data.mensagem);
		},
		error: function(data) {
			if (data.mensagem == null) {
				alert("Não foi possível excluir o registro");
			} else {
				alert(data.mensagem);
			}
		}
	});
}

// método para fechar o modal
function cancelEditDialog () {
    $('#modal-edit-item').modal('close');
}		