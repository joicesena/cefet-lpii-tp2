<%@page import="br.cefetmg.inf.model.bd.dao.ItemConfortoDAO"%>
<%@page import="br.cefetmg.inf.model.pojo.ItemConforto"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   ItemConforto[] itemConfortoEncontrados = null;
   itemConfortoEncontrados = (ItemConforto []) request.getAttribute("listaItens");
 
    if (itemConfortoEncontrados == null) {
        ItemConfortoDAO itemDAO = ItemConfortoDAO.getInstance();
        itemConfortoEncontrados = itemDAO.busca();
    }
%>

<html>
    <body>        
        <table class="striped">
            <thead>
                <tr>
                    <th>
                        Código
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th>
                        Descrição
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th><center>Ações</center></th>
                </tr>
            </thead>
            <tbody>
                <% 
                    for(int i = 0; i < itemConfortoEncontrados.length; ++i) {
                        String codItem = itemConfortoEncontrados[i].getCodItem();
                        String desItem = itemConfortoEncontrados[i].getDesItem();
                %>
                <tr>
                    <td><% out.print(codItem); %></td>
                    <td><% out.print(desItem); %></td>
                    <td>
                        <center>
							<!-- CHAMADA DOS MÉTODOS DE EXIBIÇÃO DO MODAL DE EDIÇÃO E EXCLUSÃO-->
                            <a href="#" class="modal-trigger" onclick="showEditDialog('<% out.print(codItem); %>');"><i class="material-icons table-icon-edit">edit</i></a>
                            <a href="#" class="modal-trigger" onclick="showDeleteDialog('<% out.print(codItem); %>');"><i class="material-icons table-icon-delete">delete</i></a>
                        </center>    
                    </td>
                </tr>
                <% } // for  %>
            </tbody>
        </table>
	</body>
</html>



