<%@page import="br.cefetmg.inf.model.bd.dao.CargoDAO"%>
<%@page import="br.cefetmg.inf.model.pojo.Cargo"%>
<%@page import="br.cefetmg.inf.model.bd.dao.rel.impl.CargoProgramaDAOImpl"%>
<%@page import="br.cefetmg.inf.model.pojo.rel.CargoPrograma"%>
<%@page import="br.cefetmg.inf.model.bd.dao.ProgramaDAO"%>
<%@page import="br.cefetmg.inf.model.pojo.Programa"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   Cargo[] cargosEncontrados = null;
   cargosEncontrados = (Cargo []) request.getAttribute("listaCargos");
 
    if (cargosEncontrados == null) {
        CargoDAO cargoDAO = CargoDAO.getInstance();
        cargosEncontrados = cargoDAO.busca();
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
                    <th>Telas com acesso permitido</th>
                    <th><center>Ações</center></th>
                </tr>
            </thead>
            <tbody>
                <% 
                    for(int i = 0; i < cargosEncontrados.length; ++i) {
                        String codCargo = cargosEncontrados[i].getCodCargo();
                        String nomCargo = cargosEncontrados[i].getNomCargo();

                        CargoProgramaDAOImpl cargoProgramaDAO = CargoProgramaDAOImpl.getInstance();
                        CargoPrograma [] codTelas = cargoProgramaDAO.busca(codCargo, "codCargo"); 
                %>
                <tr>
                    <td><% out.print(codCargo); %></td>
                    <td><% out.print(nomCargo); %></td>
                    <td>
                        <% 
                            for(int j = 0; j < codTelas.length; ++j) {
                                String codTela = codTelas[j].getCodPrograma();

                                ProgramaDAO programaDAO = ProgramaDAO.getInstance(); 
                                Programa [] telas = programaDAO.busca("codprograma", codTela); 
                                String desPrograma = telas[0].getDesPrograma(); 
                                out.print(desPrograma + "<br>");
                            }
                        %>
                    </td>
                    <td>
                        <center>
                            <!-- CHAMADA DOS MÉTODOS DE EXIBIÇÃO DO MODAL DE EDIÇÃO E EXCLUSÃO-->
                            <a href="#" class="modal-trigger" onclick="showEditDialog('<% out.print(codCargo); %>');"><i class="material-icons table-icon-edit">edit</i></a>
                            <a href="#" class="modal-trigger" onclick="showDeleteDialog('<% out.print(codCargo); %>');"><i class="material-icons table-icon-delete">delete</i></a>
                        </center>    
                    </td>
                </tr>
                <% } // for  %>
            </tbody>
        </table>
    </body>
</html> 