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
	// validação do formulário
	$('#frmInsertItem').validate({
		rules: {
			// regras para validação do formulário
			codigoItem: { required: true, minlength: 3 },
			descricaoItem: { required: true, minlength: 3 }
		},
		messages: {
			// mensagens caso uma das regras seja desrespeitada
			codigoItem: { required: 'Preencha o campo código', minlength: 'Informe 3 caracteres' },
			descricaoItem: { required: 'Informe a descrição do item', minlength: 'Informe pelo menos 3 caracteres'  }
		},
		submitHandler: function( form ){
			// a var dados armazena todos os inputs do form
			var dados = $( form ).serialize();

			$.ajax({
				// envia a requisição para o servlet fazer o controle
				url: "http://localhost:8080/cefet-lpii-tp2/item-de-conforto",
				type: "POST",
				data: dados,
				success: function(responseData)
				{
					// mensagem de retorno --> se a operação foi realizada ou não
					alert(responseData.mensagem);
				}
			});

			return false;
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
	$.ajax({
		// envia a requisição para o servlet
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
	$('#frmEditItem').validate({
		rules: {
			codigoItem: { required: true, minlength: 3 },
			descricaoItem: { required: true, minlength: 3 }
		},
		messages: {
			codigoItem: { required: 'Preencha o campo código', minlength: 'Informe 3 caracteres' },
			descricaoItem: { required: 'Informe a descrição do item', minlength: 'Informe pelo menos 3 caracteres'  }
		},
		submitHandler: function( form ){
			var dados = $( form ).serialize();
			
			$.ajax({
				url: "http://localhost:8080/cefet-lpii-tp2/item-de-conforto",
				type: "POST",
				data: dados,
				success: function(responseData)
				{
					alert(responseData.mensagem);
				}
			});

			return false;
		}
	});
//	alert("Edição gravada");
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
	alert("Exibir diálogo de exclusão");
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
//	alert("exclusão executada");
	$('#frmDeleteItem').validate({
		rules: {
			codigoItem: { required: true, minlength: 3 },
			senhaFuncionario: { required: true}
		},
		messages: {
			codigoItem: { required: 'Preencha o campo código', minlength: 'Informe 3 caracteres' },
			senhaFuncionario: { required: 'Informe sua senha'}
		},
		submitHandler: function( form ){
			var dados = $( form ).serialize();

			$.ajax({
				url: "http://localhost:8080/cefet-lpii-tp2/item-de-conforto",
				type: "POST",
				data: dados,
				success: function(responseData)
				{
					alert(responseData.mensagem);
				}
			});

			return false;
		}
	});
}
// método para fechar o modal
function cancelDeleteDialog () {
    $('#modal-delete-item').modal('close');
}		
