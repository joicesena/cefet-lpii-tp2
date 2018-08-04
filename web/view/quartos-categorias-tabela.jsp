<%@page import="br.cefetmg.inf.model.pojo.ItemConforto"%>
<%@page import="br.cefetmg.inf.model.bd.dao.ItemConfortoDAO"%>
<%@page import="br.cefetmg.inf.model.bd.dao.CategoriaQuartoDAO"%>
<%@page import="br.cefetmg.inf.model.pojo.CategoriaQuarto"%>
<%@page import="br.cefetmg.inf.model.bd.dao.rel.impl.CategoriaItemConfortoDAOImpl"%>
<%@page import="br.cefetmg.inf.model.pojo.rel.CategoriaItemConforto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   CategoriaQuarto[] categoriaQuartoEncontradas = null;
   categoriaQuartoEncontradas = (CategoriaQuarto []) request.getAttribute("listaCategorias");
 
    if (categoriaQuartoEncontradas == null) {
        CategoriaQuartoDAO categoriaDAO = CategoriaQuartoDAO.getInstance();
        categoriaQuartoEncontradas = categoriaDAO.busca();
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
                        Nome
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th>
                        Valor da Diária
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th>Itens de Conforto</th>
                    <th><center>Ações</center></th>
                </tr>
            </thead>
            <tbody>
                <% 
                    for(int i = 0; i < categoriaQuartoEncontradas.length; ++i) {
                        String codCategoria = categoriaQuartoEncontradas[i].getCodCategoria();
                        String nomCategoria = categoriaQuartoEncontradas[i].getNomCategoria();
                        Double vlrDiaria = categoriaQuartoEncontradas[i].getVlrDiaria();
                        
                        CategoriaItemConfortoDAOImpl categoriaItemDAO = CategoriaItemConfortoDAOImpl.getInstance();
                        CategoriaItemConforto [] codItens = categoriaItemDAO.busca(codCategoria, "codcategoria"); 
                %>
                <tr>
                    <td><% out.print(codCategoria); %></td>
                    <td><% out.print(nomCategoria); %></td>
                    <td>R$ <% out.print(vlrDiaria); %></td>
                    <td>
                        <% 
                            for(int j = 0; j < codItens.length; ++j) {
                                String codItem = codItens[j].getCodItem();
                                
                                ItemConfortoDAO itemDAO = ItemConfortoDAO.getInstance(); 
                                ItemConforto [] itens = itemDAO.busca("coditem", codItem); 
                                String desItem = itens[0].getDesItem(); 
                                out.print(desItem + "<br>");
                            }
                        %>
                    </td>
                    <td>
                        <center>
                            <!-- CHAMADA DOS MÉTODOS DE EXIBIÇÃO DO MODAL DE EDIÇÃO E EXCLUSÃO-->
                            <a href="#" class="modal-trigger" onclick="showEditDialog('<% out.print(codCategoria); %>');"><i class="material-icons table-icon-edit">edit</i></a>
                            <a href="#" class="modal-trigger" onclick="showDeleteDialog('<% out.print(codCategoria); %>');"><i class="material-icons table-icon-delete">delete</i></a>
                        </center>    
                    </td>
                </tr>
                <% } // for  %>
            </tbody>
        </table>    
    </body>
</html>