<%@page import="br.cefetmg.inf.model.bd.dao.ServicoDAO"%>
<%@page import="br.cefetmg.inf.model.pojo.Servico"%>
<%@page import="br.cefetmg.inf.model.bd.dao.ServicoAreaDAO"%>
<%@page import="br.cefetmg.inf.model.pojo.ServicoArea"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   Servico[] servicosEncontrados = null;
   servicosEncontrados = (Servico []) request.getAttribute("listaServicos");
 
    if (servicosEncontrados == null) {
        ServicoDAO servicoDAO = ServicoDAO.getInstance();
        servicosEncontrados = servicoDAO.busca();
    }
%>

<html>
    <body>
       <table class="striped">
            <thead>
                <tr>
                    <th>
                        Descrição
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th>
                        Valor
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th>
                        Área
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th><center>Ações</center></th>
                </tr>
            </thead>
            <tbody>
                <% 
                    for(int i = 0; i < servicosEncontrados.length; ++i) {
                        int seqServico = servicosEncontrados[i].getSeqServico();
                        String desServico = servicosEncontrados[i].getDesServico();
                        Double vlrUnit = servicosEncontrados[i].getVlrUnit();
                        String codServicoArea = servicosEncontrados[i].getCodServicoArea();
                        
                        ServicoAreaDAO areaDAO = ServicoAreaDAO.getInstance();
                        ServicoArea [] areas = areaDAO.busca("codservicoarea", codServicoArea);
                        String nomServicoArea = areas[0].getNomServicoArea();
                %>
                <tr>
                    <td><% out.print(desServico); %></td>
                    <td>R$ <% out.print(vlrUnit); %></td>
                    <td><% out.print(nomServicoArea); %></td>
                    <td>
                        <center>
                            <!-- CHAMADA DOS MÉTODOS DE EXIBIÇÃO DO MODAL DE EDIÇÃO E EXCLUSÃO-->
                            <a href="#" class="modal-trigger" onclick="showEditDialog('<% out.print(seqServico); %>');"><i class="material-icons table-icon-edit">edit</i></a>
                            <a href="#" class="modal-trigger" onclick="showDeleteDialog('<% out.print(seqServico); %>');"><i class="material-icons table-icon-delete">delete</i></a>
                        </center>    
                    </td>
                </tr>
                <% } // for  %>
            </tbody>
        </table> 
    </body>
</html>