<%@page import="br.cefetmg.inf.model.bd.dao.ServicoDAO"%>
<%@page import="br.cefetmg.inf.model.pojo.Servico"%>
<%@page import="br.cefetmg.inf.model.bd.dao.ServicoAreaDAO"%>
<%@page import="br.cefetmg.inf.model.pojo.ServicoArea"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    ServicoDAO registroDAO = ServicoDAO.getInstance();
    Servico[] registrosEncontrados = registroDAO.busca();
%>
<html>
    <body>
            <tbody>
                <% 
                    for(int i = 0; i < registrosEncontrados.length; ++i) {
                        int seqServico = registrosEncontrados[i].getSeqServico();
                        String desServico = registrosEncontrados[i].getDesServico();
                        String codServicoArea = registrosEncontrados[i].getCodServicoArea();
                        Double vlrUnit = registrosEncontrados[i].getVlrUnit();
                        
                        ServicoAreaDAO dao = ServicoAreaDAO.getInstance();
                        ServicoArea [] areas = dao.busca("codServicoArea", codServicoArea);
                        ServicoArea area = areas[0];
                        String nomServicoArea = area.getNomServicoArea();
                %>
                <tr>
                    <td><% out.print(nomServicoArea); %></td>
                    <td><% out.print(desServico); %></td>
                    <td><% out.print(vlrUnit); %></td>
                    <td>
                        <center>
                            <input name="radio-group" type="radio" id='<% out.print(seqServico); %>' onclick="salvaSeqServico('<% out.print(seqServico); %>');"/>
                            <label for='<% out.print(seqServico); %>'>Selecionar serviço</label>
                        </center>    
                    </td>
                </tr>
                <% } // for  %>
            </tbody>
    </body>
</html>