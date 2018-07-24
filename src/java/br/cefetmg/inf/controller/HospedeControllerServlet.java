package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.HospedeDAO;
import br.cefetmg.inf.model.dto.Hospede;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HospedeControllerServlet", urlPatterns = {"/hospede"})
public class HospedeControllerServlet extends HttpServlet {

    private HttpServletRequest request;
    private String operacaoHospede;
    
    private HospedeDAO hospede;
    
    @Override
    public void init() throws ServletException {
        hospede = HospedeDAO.getInstance();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        operacaoHospede = null;

        operacaoHospede = request.getParameter("operacaoHospede");
        
        try {
            if (operacaoHospede.equals("cadastrarHospede")) {
                request.getRequestDispatcher("/view/cadastroHospede.jsp");
                return;
            } else if (operacaoHospede.equals("pesquisarHospede")) {
                pesquisaHospede();
            } else if (operacaoHospede.equals("editarHospede")) {
                request.getRequestDispatcher("/view/cadastroHospede.jsp");
                return;
            } else if (operacaoHospede.equals("removerHospede")) {
                removeHospede();
            }
        } catch (SQLException exc) {
            //
            //
            //
        }
        
        //
        //
        String caminhoTelaHospede = "";
        request.getRequestDispatcher(caminhoTelaHospede).forward(request, response);
    }

    
    //
    // MÉTODOS DE CONTROLE
    //
    private void pesquisaHospede() throws SQLException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)request.getParameter("pesquisaHospede");
        tipoParametroPesquisa = (String)request.getParameter("parametroPesquisaHospede");
        
        Hospede [] hospedesPesquisa = hospede.busca(tipoParametroPesquisa, parametroPesquisa);
        
        request.setAttribute("listaHospedes", hospedesPesquisa);
        
        return;
    }

    private void removeHospede() throws SQLException {
        String codCPFHospede;
        codCPFHospede = request.getParameter("codCPFHospedeSelecionado");
        
        hospede.deleta(codCPFHospede);
        
        return;
    }
}
