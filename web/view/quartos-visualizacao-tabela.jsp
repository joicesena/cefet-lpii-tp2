<%@page import="br.cefetmg.inf.model.bd.dao.QuartoDAO"%>
<%@page import="br.cefetmg.inf.model.dto.Quarto"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   Quarto[] quartosEncontrados = null;
   quartosEncontrados = (Quarto []) request.getAttribute("listaItens");
 
    if (quartosEncontrados == null) {
        QuartoDAO itemDAO = QuartoDAO.getInstance();
        quartosEncontrados = itemDAO.busca();
    }
%>

<html>
    <body>
        <table class="striped">
            <thead>
                <tr>
                    <th>
                        Número
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th>
                        Categoria
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th><center>Ações</center></th>
                </tr>
            </thead>
            <tbody>
                <% 
                    for(int i = 0; i < quartosEncontrados.length; ++i) {
                        int nroQuarto = quartosEncontrados[i].getNroQuarto();
                        String codCategoria = quartosEncontrados[i].getCodCategoria();
                %>
                <tr>
                    <td><% out.print(nroQuarto); %></td>
                    <td><% out.print(codCategoria); %></td>
                    <td>
                        <center>
                            <!-- CHAMADA DOS MÉTODOS DE EXIBIÇÃO DO MODAL DE EDIÇÃO E EXCLUSÃO-->
                            <a href="#" class="modal-trigger" onclick="showEditDialog('<% out.print(nroQuarto); %>');"><i class="material-icons table-icon-edit">edit</i></a>
                            <a href="#" class="modal-trigger" onclick="showDeleteDialog('<% out.print(nroQuarto); %>');"><i class="material-icons table-icon-delete">delete</i></a>
                        </center>    
                    </td>
                </tr>
                <% } // for  %>
            </tbody>
        </table>        
    </body>
</html>