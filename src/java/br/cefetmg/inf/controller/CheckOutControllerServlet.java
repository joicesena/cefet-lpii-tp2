package br.cefetmg.inf.controller;

import java.io.IOException;
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
        // buscar o seqHospedagem atual pelo numero do quarto e pela última data de check-in
        // buscar a view pelo seqHospedagem e pelo nroQuarto
        // montar o arquivo (fatura)
    }

}
