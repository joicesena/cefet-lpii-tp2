<%@page import="br.cefetmg.inf.model.bd.dao.UsuarioDAO"%>
<%@page import="br.cefetmg.inf.model.pojo.Usuario"%>
<%@page import="br.cefetmg.inf.model.bd.dao.CargoDAO"%> 
<%@page import="br.cefetmg.inf.model.pojo.Cargo"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   Usuario[] usuariosEncontrados = null;
   usuariosEncontrados = (Usuario []) request.getAttribute("listaUsuarios");
 
    if (usuariosEncontrados == null) {
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        usuariosEncontrados = usuarioDAO.busca();
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
                        Email
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th>
                        Cargo
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th><center>Ações</center></th>
                </tr>
            </thead>
            <tbody>
                <% 
                    for(int i = 0; i < usuariosEncontrados.length; ++i) {
                        String codUsuario = usuariosEncontrados[i].getCodUsuario();
                        String nomUsuario = usuariosEncontrados[i].getNomUsuario();
                        String desEmail = usuariosEncontrados[i].getDesEmail();
                        String codCargo = usuariosEncontrados[i].getCodCargo();
                        
                        CargoDAO cargoDAO = CargoDAO.getInstance(); 
                        Cargo [] cargos = cargoDAO.busca("codCargo", codCargo); 
                        String nomCargo = cargos[0].getNomCargo(); 
                %>
                <tr>
                    <td><% out.print(codUsuario); %></td>
                    <td><% out.print(nomUsuario); %></td>
                    <td><% out.print(desEmail); %></td>
                    <td><% out.print(nomCargo); %></td>
                    <td>
                        <center>
                            <!-- CHAMADA DOS MÉTODOS DE EXIBIÇÃO DO MODAL DE EDIÇÃO E EXCLUSÃO-->
                            <a href="#" class="modal-trigger" onclick="showEditDialog('<% out.print(codUsuario); %>');"><i class="material-icons table-icon-edit">edit</i></a>
                            <a href="#" class="modal-trigger" onclick="showDeleteDialog('<% out.print(codUsuario); %>');"><i class="material-icons table-icon-delete">delete</i></a>
                        </center>    
                    </td>
                </tr>
                <% } // for  %>
            </tbody>
        </table>
    </body>
</html>