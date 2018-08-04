<%@page import="br.cefetmg.inf.model.bd.dao.HospedeDAO"%>
<%@page import="br.cefetmg.inf.model.pojo.Hospede"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   Hospede[] hospedesEncontrados = null;
   hospedesEncontrados = (Hospede []) request.getAttribute("listaHospedes");
 
    if (hospedesEncontrados == null) {
        HospedeDAO hospedeDAO = HospedeDAO.getInstance();
        hospedesEncontrados = hospedeDAO.busca();
    }
%>

<html>
    <body>
        <table class="striped">
            <thead>
                <tr>
                    <th>
                        CPF
                    </th>
                    <th>
                        Nome
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th>
                        Email
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th>
                        Telefone                        
                    </th>
                    <th><center>Ações</center></th>
                </tr>
            </thead>
            <tbody>
                <% 
                    for(int i = 0; i < hospedesEncontrados.length; ++i) {
                        String codCPF = hospedesEncontrados[i].getCodCPF();
                        String nomHospede = hospedesEncontrados[i].getNomHospede();
                        String desEmail = hospedesEncontrados[i].getDesEmail();
                        String desTelefone = hospedesEncontrados[i].getDesTelefone();
                %>
                <tr>
                    <td><% out.print(codCPF); %></td>
                    <td><% out.print(nomHospede); %></td>
                    <td><% out.print(desEmail); %></td>
                    <td><% out.print(desTelefone); %></td>
                    <td>
                        <center>
                            <!-- CHAMADA DO MÉTODO DE EXIBIÇÃO DO MODAL DE EDIÇÃO -->
                            <a href="#" class="modal-trigger" onclick="showEditDialog('<% out.print(codCPF); %>');"><i class="material-icons table-icon-edit">edit</i></a>
                        </center>    
                    </td>
                </tr>
                <% } // for  %>
            </tbody>
        </table>
    </body>
</html>