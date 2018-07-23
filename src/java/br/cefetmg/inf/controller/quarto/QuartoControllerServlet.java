package br.cefetmg.inf.controller.quarto;

import br.cefetmg.inf.model.bd.dao.CategoriaQuartoDAO;
import br.cefetmg.inf.model.bd.dao.QuartoDAO;
import br.cefetmg.inf.model.dto.CategoriaQuarto;
import br.cefetmg.inf.model.dto.Quarto;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "QuartoControllerServlet", urlPatterns = {"/quarto"})
public class QuartoControllerServlet extends HttpServlet {

    private HttpServletRequest request;
    private String operacaoQuarto;
    
    private QuartoDAO quarto;
    
    @Override
    public void init() throws ServletException {
        quarto = QuartoDAO.getInstance();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        operacaoQuarto = null;

        operacaoQuarto = request.getParameter("operacaoQuarto");
        
        try {
            if (operacaoQuarto.equals("cadastrarQuarto")) {
                cadastraQuarto();
            } else if (operacaoQuarto.equals("pesquisarQuarto")) {
                pesquisaQuarto();
            } else if (operacaoQuarto.equals("editarQuarto")) {
                editaQuarto();
            } else if (operacaoQuarto.equals("removerQuarto")) {
                removeQuarto();
            }
        } catch (SQLException exc) {
            //
            //
            //
        }
        
        String caminhoTelaQuarto = "";
        request.getRequestDispatcher(caminhoTelaQuarto).forward(request, response);
    }

    
    //
    // MÉTODOS DE CONTROLE
    //
    private void cadastraQuarto () throws SQLException {
        int nroQuarto;
        String nomCategoria;
        String codCategoria;
        
        nroQuarto = Integer.parseInt(request.getParameter("nroQuarto"));
        nomCategoria = request.getParameter("nomCategoria");
        
        CategoriaQuartoDAO categoriaDAO = CategoriaQuartoDAO.getInstance();
        CategoriaQuarto[] categoria = categoriaDAO.busca("nomCategoria", nomCategoria);
        codCategoria = categoria[0].getCodCategoria();
        
        Quarto quartoAdicionar = new Quarto(nroQuarto, codCategoria, false);
        quarto.adiciona(quartoAdicionar);
        
        return;
    }
    
    private void pesquisaQuarto() throws SQLException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)request.getParameter("pesquisaQuarto");
        tipoParametroPesquisa = (String)request.getParameter("parametroPesquisaQuarto");
        
        Quarto [] quartosPesquisa = quarto.busca(tipoParametroPesquisa, parametroPesquisa);
        
        request.setAttribute("listaCategorias", quartosPesquisa);
        
        return;
    }

    private void editaQuarto() throws SQLException {
        int nroQuarto;
        String nomCategoria;
        String codCategoria;
        
        nroQuarto = Integer.parseInt(request.getParameter("nroQuartoSelecionado"));
        nomCategoria = request.getParameter("nomCategoriaSelecionado");
        
        CategoriaQuartoDAO categoriaDAO = CategoriaQuartoDAO.getInstance();
        CategoriaQuarto[] categoria = categoriaDAO.busca("nomCategoria", nomCategoria);
        codCategoria = categoria[0].getCodCategoria();
        
        Quarto quartoAdicionar = new Quarto(nroQuarto, codCategoria, false);
        quarto.atualiza(nroQuarto, quartoAdicionar);

        return;
    }
    
    private void removeQuarto() throws SQLException {
        int nroQuarto;
        nroQuarto = Integer.parseInt(request.getParameter("nroQuartoSelecionado"));
        
        Quarto [] quartoBuscado = quarto.busca("nroQuarto", nroQuarto);
        
        if (quartoBuscado[0].isIdtOcupado()) {
            request.setAttribute("tentativaExclusao", "Não é possível excluir este quarto: está ocupado!");
            return;
        } else {
            quarto.deleta(nroQuarto);        
            return;
        }
    }


}
