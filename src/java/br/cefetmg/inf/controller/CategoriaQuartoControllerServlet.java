package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.CategoriaQuartoDAO;
import br.cefetmg.inf.model.bd.dao.ItemConfortoDAO;
import br.cefetmg.inf.model.bd.dao.rel.impl.CategoriaItemConfortoDAOImpl;
import br.cefetmg.inf.model.dto.CategoriaQuarto;
import br.cefetmg.inf.model.dto.ItemConforto;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CategoriaQuartoControllerServlet", urlPatterns = {"/categoriad-de-quarto"})
public class CategoriaQuartoControllerServlet extends HttpServlet {

    private HttpServletRequest request;
    private String operacaoCategoria;
    
    private CategoriaQuartoDAO categoriaQuarto;
    
    @Override
    public void init() throws ServletException {
        categoriaQuarto = CategoriaQuartoDAO.getInstance();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        operacaoCategoria = null;

        operacaoCategoria = request.getParameter("operacaoCategoria");
        
        try {
            if (operacaoCategoria.equals("cadastrarCategoria")) {
                cadastraCategoria();
            } else if (operacaoCategoria.equals("pesquisarCategoria")) {
                pesquisaCategoria();
            } else if (operacaoCategoria.equals("editarCategoria")) {
                editaCategoria();
            } else if (operacaoCategoria.equals("removerCategoria")) {
                removeCategoria();
            }
        } catch (SQLException exc) {
            //
            //
            //
        }
        
        String caminhoTelaCategoriaQuarto = "";
        request.getRequestDispatcher(caminhoTelaCategoriaQuarto).forward(request, response);
    }

    
    //
    // MÉTODOS DE CONTROLE
    //
    private void cadastraCategoria () throws SQLException {
        String codCategoria;
        String nomCategoria;
        Double vlrDiaria;
        String [] itensConforto;
        String codItem;
        
        codCategoria = request.getParameter("codCategoria");
        nomCategoria = request.getParameter("desCategoria");
        vlrDiaria = Double.parseDouble(request.getParameter("vlrDiaria"));
        itensConforto = request.getParameterValues("itensConforto");
        
        CategoriaQuarto categoriaAdicionar = new CategoriaQuarto(codCategoria, nomCategoria, vlrDiaria);
        categoriaQuarto.adiciona(categoriaAdicionar);
        
        CategoriaItemConfortoDAOImpl relacaoCategItem = CategoriaItemConfortoDAOImpl.getInstance();
        ItemConfortoDAO itemConfortoDAO = ItemConfortoDAO.getInstance();
        
        for (String item : itensConforto) {
            // busca o codigo dos itens que tem aquelas descrições
            ItemConforto [] itemConforto = itemConfortoDAO.busca("desItem", item);
            
            codItem = itemConforto[0].getCodItem();

            // adiciona o relacionamento
            relacaoCategItem.adiciona(codCategoria, codItem);
        }
        
        return;
    }
    
    private void pesquisaCategoria() throws SQLException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)request.getParameter("pesquisaCategoria");
        tipoParametroPesquisa = (String)request.getParameter("parametroPesquisaCategoria");
        
        CategoriaQuarto [] categoriasPesquisa = categoriaQuarto.busca(tipoParametroPesquisa, parametroPesquisa);
        
        request.setAttribute("listaCategorias", categoriasPesquisa);
        
        return;
    }

    private void editaCategoria() throws SQLException {
        String codCategoria, nomCategoria;
        Double vlrDiaria;
        String [] itensConforto;
        String codItem;
        
        codCategoria = request.getParameter("codCategoriaSelecionado");
        nomCategoria = request.getParameter("nomCategoriaSelecionado");
        vlrDiaria = Double.parseDouble(request.getParameter("vlrDiariaSelecionada"));
        itensConforto = request.getParameterValues("itensConfortoSelecionados");
        
        CategoriaQuarto categoriaQuartoAtualizada = new CategoriaQuarto(codCategoria, nomCategoria, vlrDiaria);
        
        categoriaQuarto.atualiza(codCategoria, categoriaQuartoAtualizada);
        
        CategoriaItemConfortoDAOImpl relacaoCategItem = CategoriaItemConfortoDAOImpl.getInstance();
        ItemConfortoDAO itemConfortoDAO = ItemConfortoDAO.getInstance();
        

        relacaoCategItem.deleta(codCategoria, "codCategoria");
        for (String item : itensConforto) {
            ItemConforto [] itemConforto = itemConfortoDAO.busca("desItem", item);
            codItem = itemConforto[0].getCodItem();
            relacaoCategItem.adiciona(codCategoria, codItem);
        }
        
        return;
    }
    
    private void removeCategoria() throws SQLException {
        String codCategoria;
        codCategoria = request.getParameter("codCategoriaSelecionado");

        CategoriaItemConfortoDAOImpl relacaoCategItem = CategoriaItemConfortoDAOImpl.getInstance();
        relacaoCategItem.deleta(codCategoria, "codCategoria");
        categoriaQuarto.deleta(codCategoria);
        
        return;
    }

}
