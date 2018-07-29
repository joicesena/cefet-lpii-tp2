package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.HospedagemDAO;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Hospedagem;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
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
        nroQuarto = Integer.parseInt(request.getParameter("nroQuarto"));
        
        String caminhoArquivo = "/view/check-out.jsp";
        
        RequestDispatcher rd = request.getRequestDispatcher(caminhoArquivo);
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            
            // colocar o seqHospedagem como atributo do request
            request.setAttribute("seqHospedagem", seqHospedagem);

            // dar um forward para o DespesasPDFControllerServlet
            String caminhoArquivo = "/despesas-pdf";
            RequestDispatcher rd = request.getRequestDispatcher(caminhoArquivo);
            rd.forward(request, response);
            
        } catch (SQLException ex) {
            //
            //
            //
        }
    }

}
