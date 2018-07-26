package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.ServicoDAO;
import br.cefetmg.inf.model.dto.Servico;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServicoControllerServlet", urlPatterns = {"/servico"})
public class ServicoControllerServlet extends HttpServlet {
    private HttpServletRequest requestInterno;
    private String operacaoServico;
    
    private ServicoDAO servico;
    
    @Override
    public void init() throws ServletException {
        servico = ServicoDAO.getInstance();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoServico = null;

        operacaoServico = requestInterno.getParameter("operacaoServico");
        
        try {
            if (operacaoServico.equals("cadastrarServico")) {
                cadastraServico();
            } else if (operacaoServico.equals("pesquisarServico")) {
                pesquisaServico();
            } else if (operacaoServico.equals("editarServico")) {
                editaServico();
            } else if (operacaoServico.equals("removerServico")) {
                removeServico();
            }
        } catch (SQLException exc) {
            //
            //
            //
        }
        
        String caminhoArquivo = "/view/servicos.jsp";
        
        RequestDispatcher rd = request.getRequestDispatcher(caminhoArquivo);
        rd.forward(request, response);
        
    }

    
    //
    // MÉTODOS DE CONTROLE
    //
    private void cadastraServico () throws SQLException {
        String desServico;
        Double vlrUnit;
        String codServicoArea;
        
        desServico = requestInterno.getParameter("desServico");
        vlrUnit = Double.parseDouble(requestInterno.getParameter("vlrUnit"));
        codServicoArea = requestInterno.getParameter("codServicoArea");
        
        Servico servicoAdicionar = new Servico(desServico, vlrUnit, codServicoArea);
        servico.adiciona(servicoAdicionar);
        
        return;
    }
    
    private void pesquisaServico() throws SQLException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaServico");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaServico");
        
        Servico [] servicosPesquisa = servico.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaServicos", servicosPesquisa);
        return;
    }

    private void editaServico() throws SQLException {
        String desServico;
        Double vlrUnit;
        String codServicoArea;

        desServico = requestInterno.getParameter("desServicoSelecionado");
        vlrUnit = Double.parseDouble(requestInterno.getParameter("vlrUnitSelecionado"));
        codServicoArea = requestInterno.getParameter("codServicoAreaSelecionado");
        
        Servico servicoAtualizado = new Servico(desServico, vlrUnit, codServicoArea);
        
////        servico.atualiza();
        
        return;
    }
    
    private void removeServico() throws SQLException {
//        String codServico;
//        codServico = requestInterno.getParameter("codServicoSelecionado");
//        
//        servico.deleta(codServico);
        
        return;
    }

}
