$( document ).ready(function(){
    $("#button-menu").sideNav();
    $('select').material_select();

    var data = localStorage.getItem('detalhesConta');
    if(data!==null){
        var datajson = JSON.parse(data);

        var table = document.getElementById("tabelaDetalhesConta");
        
        for (var i = 0; i < datajson.linhas.length; i++) {
            // item.nroQuarto --> hidden
            // item.seqHospedagem --> hidden
            // item.seqServico
            // item.desServico
            // item.datConsumo
            // item.vlrUnit
            // item.qtdConsumo
            item = datajson.linhas[i];
            var row = table.insertRow(i+1);
            var cell01 = row.insertCell(0);
            cell01.innerHTML = item.desServico;
            cell01.firstChild.value = item.seqServico;
            var cell02 = row.insertCell(1);
            cell02.innerHTML = item.qtdConsumo;
            cell02.firstChild.value = item.qtdConsumo;
            var cell03 = row.insertCell(2);
            cell03.innerHTML = item.datConsumo;
            cell03.firstChild.value = item.datConsumo;
            var cell04 = row.insertCell(3);
            cell04.innerHTML = item.vlrUnit;
            cell04.firstChild.value = item.vlrUnit;
            var cell05 = row.insertCell(4);
            cell05.innerHTML = "<center><a href='#' class='modal-trigger' onclick= 'showEditDialog(" + ");'><i class=material-icons table-icon-edit>edit</i></a>" +
                               "<a href='#' class='modal-trigger' onclick='showDeleteDialog(" + ");'><i class='material-icons table-icon-delete'>delete</i></a>" +
                               "</center>";
        }

    }
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
		url: "http://localhost:8080/cefet-lpii-tp2/despesas",
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
function showEditDialog (ANroQuarto, ASeqHospedagem, ASeqServico, AQtdConsumo, ADatConsumo) {
    //
    // parâmetros
    // 
    // nroQuarto
    // seqHospedagem
    // seqServico
    // qtdConsumo
    // datConsumo
    // 
    // 
    // envia a requisição para o servlet
    $.ajax({
            url: "http://localhost:8080/cefet-lpii-tp2/despesas",
            type: "POST",
            // manda como parâmetro de operação 1 --> retornarDadosRegistro
            data: "operacaoItem=1" + "&nroQuarto="+ANroQuarto + "&seqHospedagem="+ASeqHospedagem + 
                  "&seqServico="+ASeqServico + "&qtdConsumo="+AQtdConsumo + "&datConsumo="+ADatConsumo,
            success: function(responseText){
                    // abre o modal
                    $('#modal-edit-item').modal('open');

                    // modifica o valor dos inputs no formulário para os dados existentes
                    $("#frmEditItem #seqServico").val(responseText.seqServico);
                    $("#frmEditItem #qtdConsumo").val(responseText.qtdConsumo);
            }
    });
}

// método para gravação dos dados alterados
function saveEditDialog () { 
	var dados = $( "#frmEditItem" ).serialize();
	
	$.ajax({
		url: "http://localhost:8080/cefet-lpii-tp2/despesas",
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
