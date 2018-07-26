package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.CategoriaQuartoDAO;
import br.cefetmg.inf.model.bd.dao.HospedagemDAO;
import br.cefetmg.inf.model.bd.dao.QuartoDAO;
import br.cefetmg.inf.model.bd.dao.rel.impl.QuartoHospedagemDAOImpl;
import br.cefetmg.inf.model.dto.CategoriaQuarto;
import br.cefetmg.inf.model.dto.Hospedagem;
import br.cefetmg.inf.model.dto.Quarto;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CheckInControllerServlet", urlPatterns = {"/check-in"})
public class CheckInControllerServlet extends HttpServlet {
    
    private int nroQuarto;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        nroQuarto = Integer.parseInt(request.getParameter("nroQuarto"));
        
        String caminhoArquivo = "/view/check-in.jsp";
        
        RequestDispatcher rd = request.getRequestDispatcher(caminhoArquivo);
        rd.forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // cpf do hóspede
        String cpfHospede = request.getParameter("codCPF");
        
        // cálculos das datas
        int diasDeEstadia = Integer.parseInt(request.getParameter("diasDeEstadia"));
            // data check-in
            Date dataAtual = new Date();
            Timestamp dataCheckIn = new Timestamp(dataAtual.getTime());
            // data check-out
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.format(dataAtual);
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(dataAtual);
            calendario.add(Calendar.DATE, +diasDeEstadia);
            Timestamp dataCheckOut = new Timestamp(calendario.getTimeInMillis());
        
        // número de hóspedes
        int nroAdultos = Integer.parseInt(request.getParameter("nroAdultos"));
        int nroCriancas = Integer.parseInt(request.getParameter("nroCriancas"));
        
        // vlrDiaria
        try {
            QuartoDAO quartoDAO = QuartoDAO.getInstance();
            Quarto[] quarto;
            quarto = quartoDAO.busca("nroQuarto", nroQuarto);
            CategoriaQuartoDAO categoriaDAO = CategoriaQuartoDAO.getInstance();
            CategoriaQuarto[] categorias = categoriaDAO.busca("codCategoria", quarto[0].getCodCategoria());
            Double valorDiaria = categorias[0].getVlrDiaria();

            // realiza a operação de check-in
            Hospedagem hosp = new Hospedagem(dataCheckIn, dataCheckOut, (valorDiaria*diasDeEstadia), cpfHospede);
            HospedagemDAO hospDAO = HospedagemDAO.getInstance();
            hospDAO.adiciona(hosp);
            Hospedagem[] hospEncontrada = hospDAO.busca(hosp);
            
            QuartoHospedagemDAOImpl quartoHosp = QuartoHospedagemDAOImpl.getInstance();
            quartoHosp.adiciona(hospEncontrada[0].getSeqHospedagem(), nroQuarto, dataCheckIn, dataCheckOut, nroAdultos,
                                nroCriancas, valorDiaria);
        } catch (SQLException ex) {
            //
            //
            //
        }
        
        String caminhoArquivo = "http://localhost:8080/cefet-lpii-tp2/view/quartos-estados.jsp";
        response.sendRedirect(caminhoArquivo);
    }
}
