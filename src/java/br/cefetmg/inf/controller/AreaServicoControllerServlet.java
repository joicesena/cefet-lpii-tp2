package br.cefetmg.inf.controller;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.cefetmg.inf.model.dto.ServicoArea;
import br.cefetmg.inf.model.bd.dao.ServicoAreaDAO;
import java.io.IOException;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "AreaServicoControllerServlet", urlPatterns = {"/areaServico"})
public class AreaServicoControllerServlet extends HttpServlet {

    private HttpServletRequest requestInterno;
    private String operacaoArea;
    
    private ServicoAreaDAO servicoArea;
    
    @Override
    public void init() throws ServletException {
        servicoArea = ServicoAreaDAO.getInstance();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoArea = null;

        operacaoArea = requestInterno.getParameter("operacaoArea");
        
        try {
            if (operacaoArea.equals("cadastrarArea")) {
                cadastraArea();
            } else if (operacaoArea.equals("pesquisarArea")) {
                pesquisaArea();
            } else if (operacaoArea.equals("editarArea")) {
                editaArea();
            } else if (operacaoArea.equals("removerArea")) {
                removeArea();
            }
        } catch (SQLException exc) {
            //
            //
            //
        }
        
        String caminhoArquivo = "/view/servicos-areas.jsp";
        
        RequestDispatcher rd = request.getRequestDispatcher(caminhoArquivo);
        rd.forward(request, response);
        
    }

    
    //
    // MÉTODOS DE CONTROLE
    //
    private void cadastraArea () throws SQLException {
        String codArea;
        String desArea;
        
        codArea = requestInterno.getParameter("codServicoArea");
        desArea = requestInterno.getParameter("nomServicoArea");
        
        ServicoArea areaAdicionar = new ServicoArea(codArea, desArea);
        servicoArea.adiciona(areaAdicionar);
        
        return;
    }
    
    private void pesquisaArea() throws SQLException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaArea");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaArea");
        
        ServicoArea [] areasPesquisa = servicoArea.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaAreas", areasPesquisa);
        return;
    }

    private void editaArea() throws SQLException {
        String codArea, desArea;
        codArea = requestInterno.getParameter("codAreaSelecionado");
        desArea = requestInterno.getParameter("nomAreaSelecionado");
        
        ServicoArea servicoAreaAtualizado = new ServicoArea(codArea, desArea);
        
        servicoArea.atualiza(codArea, servicoAreaAtualizado);
        
        return;
    }
    
    private void removeArea() throws SQLException {
        String codArea;
        codArea = requestInterno.getParameter("codAreaSelecionado");
        
        servicoArea.deleta(codArea);
        
        return;
    }

}
