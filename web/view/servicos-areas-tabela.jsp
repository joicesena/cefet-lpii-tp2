<%@page import="br.cefetmg.inf.model.bd.dao.ServicoAreaDAO"%>
<%@page import="br.cefetmg.inf.model.pojo.ServicoArea"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   ServicoArea[] servicoAreaEncontradas = null;
   servicoAreaEncontradas = (ServicoArea []) request.getAttribute("listaAreas");
 
    if (servicoAreaEncontradas == null) {
        ServicoAreaDAO areaDAO = ServicoAreaDAO.getInstance();
        servicoAreaEncontradas = areaDAO.busca();
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
                    <th><center>Ações</center></th>
                </tr>
            </thead>
            <tbody>
                <% 
                    for(int i = 0; i < servicoAreaEncontradas.length; ++i) {
                        String codServicoArea = servicoAreaEncontradas[i].getCodServicoArea();
                        String nomServicoArea = servicoAreaEncontradas[i].getNomServicoArea();
                %>
                <tr>
                    <td><% out.print(codServicoArea); %></td>
                    <td><% out.print(nomServicoArea); %></td>
                    <td>
                        <center>
                            <!-- CHAMADA DOS MÉTODOS DE EXIBIÇÃO DO MODAL DE EDIÇÃO E EXCLUSÃO-->
                            <a href="#" class="modal-trigger" onclick="showEditDialog('<% out.print(codServicoArea); %>');"><i class="material-icons table-icon-edit">edit</i></a>
                            <a href="#" class="modal-trigger" onclick="showDeleteDialog('<% out.print(codServicoArea); %>');"><i class="material-icons table-icon-delete">delete</i></a>
                        </center>      
                    </td>
                </tr>
                <% } // for  %>
            </tbody>
        </table>
    </body>
</html>
