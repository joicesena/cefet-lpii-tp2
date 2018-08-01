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

// método que mostra o modal para cadastro de item
function showInsertDialog () {
	$('#modal-insert-item').modal('open');
}

// método para gravação dos dados inseridos
function saveInsertDialog () {
	// a var dados contém os dados dos inputs no formulário
	var dados = $( "#frmInsertItem" ).serialize();
	
	// envia a requisição pro servlet
	$.ajax({
		url: "http://localhost:8080/cefet-lpii-tp2/item-de-conforto",
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
function showEditDialog (ACodItem) {
	// envia a requisição para o servlet
	$.ajax({
		url: "http://localhost:8080/cefet-lpii-tp2/item-de-conforto",
		type: "POST",
		// manda como parâmetro de operação 1 --> retornarDadosRegistro
		data: "operacaoItem=1" + "&codItem="+ACodItem,
		success: function(responseText){
			// abre o modal
			$('#modal-edit-item').modal('open');
//			$('#frmEditItem').form('load', responseText);
			
			// modifica o valor dos inputs no formulário para os dados existentes
			$("#frmEditItem #codigoItem").val(responseText.codigoItem);
			$("#frmEditItem #descricaoItem").val(responseText.descricaoItem);
		}
	});
}

// método para gravação dos dados alterados
function saveEditDialog () { 
	var dados = $( "#frmEditItem" ).serialize();
	
	$.ajax({
		url: "http://localhost:8080/cefet-lpii-tp2/item-de-conforto",
		type: "POST",
		data: dados,
		// mostra mensagem pro usuário
		success: function(data) {
			alert(data.mensagem);
		},
		error: function(data) {
			if (data.mensagem == null) {
				alert("Não foi possível editar o registro");
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

//
// EXCLUSÃO DE ITEM
//

// método para exibir modal de exclusão
function showDeleteDialog (ACodItem) {
	$.ajax({
		url: "http://localhost:8080/cefet-lpii-tp2/item-de-conforto",
		type: "POST",
		data: "operacaoItem=1" + "&codItem="+ACodItem,
		success: function(responseText){
			$('#modal-delete-item').modal('open');
			$("#frmDeleteItem #codigoItem").val(responseText.codigoItem);
		}
	});
}

// método para execução da exclusão
function executeDeleteDialog () {
	var dados = $( "#frmDeleteItem" ).serialize();
	
	$.ajax({
		url: "http://localhost:8080/cefet-lpii-tp2/item-de-conforto",
		type: "POST",
		data: dados,
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
function cancelDeleteDialog () {
    $('#modal-delete-item').modal('close');
}		
