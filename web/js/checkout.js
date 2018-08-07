$( document ).ready(function(){
    $("#button-menu").sideNav();

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
    nroQuarto = datajson.linhas[0].nroQuarto;
    
    localStorage.setItem("nroQuarto", nroQuarto);

    localStorage.removeItem('detalhesConta');

});

function sortTableASC() {
    alert("Função para ordenar a coluna de forma crescente");
}

function sortTableDESC() {
    alert("Função para ordenar a coluna de forma decrescente");
}

function checkout() {
    nroQuarto = localStorage.getItem("nroQuartoCheckout");
    alert("nroquarto - " + nroQuarto);
    document.forms[0].elements["nroQuarto"].value = nroQuarto;
    document.getElementById("frmCheckOut").submit();
}