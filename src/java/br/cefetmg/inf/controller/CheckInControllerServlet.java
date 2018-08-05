package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.CategoriaQuartoDAO;
import br.cefetmg.inf.model.bd.dao.HospedagemDAO;
import br.cefetmg.inf.model.bd.dao.QuartoDAO;
import br.cefetmg.inf.model.bd.dao.rel.impl.QuartoHospedagemDAOImpl;
import br.cefetmg.inf.model.pojo.CategoriaQuarto;
import br.cefetmg.inf.model.pojo.Hospedagem;
import br.cefetmg.inf.model.pojo.Quarto;
import br.cefetmg.inf.model.pojo.rel.QuartoHospedagem;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CheckInControllerServlet", urlPatterns = {"/check-in"})
public class CheckInControllerServlet extends HttpServlet {
    private HttpServletRequest requestInterno;
    private int operacaoRegistro;
    
    private static int nroQuarto;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoRegistro = 0;

        operacaoRegistro = Integer.parseInt(requestInterno.getParameter("operacaoRegistro"));
        JsonObject retorno;
        
        try {
            if (operacaoRegistro == 1) {
                // operacao 1 --> salvar o nroQuarto e forward para a tela de check-in
                nroQuarto = Integer.parseInt(request.getParameter("nroQuarto"));
                retorno = Json.createObjectBuilder()
                    .add("success", true)
                    .add("mensagem", "Redirecionando para checkin")
                    .build();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoRegistro == 2) {
                // operação 2 --> efetuar o check-in
                retorno = efetuarCheckIn();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            }
        } catch (SQLException | UnsupportedEncodingException exc ) {
            retorno = Json.createObjectBuilder()
                .add("success", false)
                .add("mensagem", "Erro! Tente novamente")
                .build();
            response.setContentType("text/json");
            PrintWriter out = response.getWriter();
            out.print(retorno);
        }
    }

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
    
    protected JsonObject efetuarCheckIn() throws SQLException {
        JsonObject dadosRegistro;
        
        // cpf do hóspede
        String cpfHospede = requestInterno.getParameter("codCPF");
        
        // cálculos das datas
        int diasDeEstadia = Integer.parseInt(requestInterno.getParameter("diasDeEstadia"));
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
        int nroAdultos = Integer.parseInt(requestInterno.getParameter("nroAdultos"));
        int nroCriancas = Integer.parseInt(requestInterno.getParameter("nroCriancas"));
        
        // vlrDiaria
        QuartoDAO quartoDAO = QuartoDAO.getInstance();
        Quarto[] quarto = quartoDAO.busca("nroQuarto", nroQuarto);

        String codCategoria = quarto[0].getCodCategoria();
        
        CategoriaQuartoDAO categoriaDAO = CategoriaQuartoDAO.getInstance();
        CategoriaQuarto[] categorias = categoriaDAO.busca("codCategoria", codCategoria);
        Double valorDiaria = categorias[0].getVlrDiaria();
        
        double valorTotal = valorDiaria*diasDeEstadia;
        
        // ----------------------------------------------------------------------------------------------------------------------------------------
        // realiza a operação de check-in
        Hospedagem hosp = new Hospedagem(dataCheckIn, dataCheckOut, valorTotal, cpfHospede);
        HospedagemDAO hospDAO = HospedagemDAO.getInstance();
        hospDAO.adiciona(hosp);
        
        Hospedagem[] hospEncontrada = hospDAO.busca(hosp);

        QuartoHospedagemDAOImpl quartoHosp = QuartoHospedagemDAOImpl.getInstance();
        int seqHospedagem = hospEncontrada[0].getSeqHospedagem();
        QuartoHospedagem objAdicionar = new QuartoHospedagem(seqHospedagem, nroQuarto, nroAdultos, nroCriancas, valorDiaria);
        boolean testeAddQuarto = quartoHosp.adiciona(objAdicionar);

        // atualiza o idtOcupado do quarto pra ocupado
        Quarto quartoAtualizado = quarto[0];
        quartoAtualizado.setIdtOcupado(true);
        boolean testeAtualizaQuarto = quartoDAO.atualiza(nroQuarto, quartoAtualizado);

        dadosRegistro = Json.createObjectBuilder()
            .add("success", false)
            .add("mensagem", "Check-in efetuado com sucesso!")
            .build();
        
        return dadosRegistro;
    }
}
