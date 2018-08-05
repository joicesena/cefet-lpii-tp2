<%@page import="br.cefetmg.inf.model.bd.util.UtilidadesBD"%>
<%@page import="br.cefetmg.inf.model.bd.dao.QuartoDAO"%>
<%@page import="br.cefetmg.inf.model.pojo.Quarto"%>
<%@page import="br.cefetmg.inf.model.bd.dao.rel.impl.QuartoHospedagemDAOImpl"%>
<%@page import="br.cefetmg.inf.model.pojo.rel.QuartoHospedagem"%>
<%@page import="br.cefetmg.inf.model.bd.dao.HospedagemDAO"%>
<%@page import="br.cefetmg.inf.model.pojo.Hospedagem"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
   Quarto[] quartosEncontrados = null;
   quartosEncontrados = (Quarto []) request.getAttribute("listaQuartos");
 
    if (quartosEncontrados == null) {
        QuartoDAO quartoDAO = QuartoDAO.getInstance();
        quartosEncontrados = quartoDAO.busca();
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
                        Estado
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableDESC()">arrow_drop_down</i></a>
                        <a href="#"><i class="material-icons right table-icon-sort" onclick="sortTableASC()">arrow_drop_up</i></a>
                    </th>
                    <th>
                        Data Limite da Estadia (prevista)
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
                       boolean idtOcupado = quartosEncontrados[i].isIdtOcupado();

                       String estadoQuarto = null;
                       String datCheckOut = null;

                       if (idtOcupado) {
                            estadoQuarto = "Ocupado";

                            //Acha o seqhospedagem que está em quarto hospedagem
                            int seqHospedagem = UtilidadesBD.buscaUltimoRegistroRelacionadoAoQuarto(nroQuarto);
                            //Usa o seqhospedagem para obter o datcheckout de hospedagem
                            HospedagemDAO hospedagemDAO = HospedagemDAO.getInstance(); 
                            Hospedagem [] hospedagens = hospedagemDAO.busca("seqhospedagem", seqHospedagem); 
                            Timestamp datCheckOutTS = hospedagens[0].getDatCheckOut();
                            //Passa datCheckOut de Timestamp para String
                            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                            datCheckOut = formato.format(datCheckOutTS);

                       } else {
                           estadoQuarto = "Livre";
                           datCheckOut = " - ";
                       }
                %>
                <tr>
                    <td><% out.print(nroQuarto); %></td>
                    <td><% out.print(estadoQuarto); %></td>
                    <td><% out.print(datCheckOut); %></td>
                    <td>
                        <center>
                            <% if(idtOcupado){  %>
                                <a href="#" id="info-button" class="waves-effect waves-light btn" onclick="forwardAccountDetails('<% out.print(nroQuarto); %>')"><i class="material-icons left">info_outline</i>Detalhes</a>
                                <a href="#" id="checkout-button" class="waves-effect waves-light btn" onclick="forwardCheckOut('<% out.print(nroQuarto); %>')"><i class="material-icons left">remove_circle_outline</i>Check-out</a>
                            <% } else {  %>
                                <a href="#" id="checkin-button" class="waves-effect waves-light btn" onclick="forwardCheckIn('<% out.print(nroQuarto); %>')"><i class="material-icons left">add_circle_outline</i>Check-in</a>
                            <% } // if  %>        
                        </center>    
                    </td>
                </tr>
                <% } // for  %>
             </tbody>
         </table>
    </body>
</html>