package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.HospedagemDAO;
import br.cefetmg.inf.model.bd.dao.QuartoDAO;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Hospedagem;
import br.cefetmg.inf.model.pojo.Quarto;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CheckOutControllerServlet", urlPatterns = {"/check-out"})
public class CheckOutControllerServlet extends HttpServlet {
    int nroQuarto;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        nroQuarto = Integer.parseInt(request.getParameter("nroQuarto"));
        
        try {
            // buscar o seqHospedagem atual pelo numero do quarto e pela última data de check-in
            int seqHospedagem = UtilidadesBD.buscaUltimoRegistroRelacionadoAoQuarto(nroQuarto);

            Date dataAtual = new Date();
            Timestamp dataCheckOut = new Timestamp(dataAtual.getTime());
            
            HospedagemDAO hospDAO = HospedagemDAO.getInstance();
            Hospedagem[] hospBuscada = hospDAO.busca("seqHospedagem", seqHospedagem);
            Hospedagem hospedagemAtualizado = hospBuscada[0];
            hospedagemAtualizado.setDatCheckOut(dataCheckOut);
            
            // atualiza a data de check-out da hospedagem
            hospDAO.atualiza(seqHospedagem, hospedagemAtualizado);
            
            // muda o idtQuarto pra livre
            QuartoDAO quartoDAO = QuartoDAO.getInstance();
            Quarto [] quarto = quartoDAO.busca("nroQuarto", nroQuarto);
            Quarto quartoAtualizado = quarto[0];
            quartoAtualizado.setIdtOcupado(false);
            quartoDAO.atualiza(nroQuarto, quartoAtualizado);
            
            // colocar o seqHospedagem como atributo do request
            request.setAttribute("seqHospedagem", seqHospedagem);

            // dar um forward para o DespesasPDFControllerServlet
            String caminhoArquivo = "/despesas-pdf";
            RequestDispatcher rd = request.getRequestDispatcher(caminhoArquivo);
            rd.forward(request, response);
            
        } catch (SQLException ex) {
            JsonObject retorno = Json.createObjectBuilder()
                .add("success", false)
                .add("mensagem", "Erro! Tente novamente")
                .build();
            response.setContentType("text/json");
            PrintWriter out = response.getWriter();
            out.print(retorno);
        }
    }

}
