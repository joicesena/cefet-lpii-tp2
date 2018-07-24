package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.ItemConfortoDAO;
import br.cefetmg.inf.model.dto.ItemConforto;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ItemConfortoControllerServlet", urlPatterns = {"/itemConforto"})
public class ItemConfortoControllerServlet extends HttpServlet {

    private HttpServletRequest request;
    private String operacaoItem;
    
    private ItemConfortoDAO itemConforto;
    
    @Override
    public void init() throws ServletException {
        itemConforto = ItemConfortoDAO.getInstance();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        operacaoItem = null;

        operacaoItem = request.getParameter("operacaoItem");
        
        try {
            if (operacaoItem.equals("cadastrarItem")) {
                cadastraItem();
            } else if (operacaoItem.equals("pesquisarItem")) {
                pesquisaItem();
            } else if (operacaoItem.equals("editarItem")) {
                editaItem();
            } else if (operacaoItem.equals("removerItem")) {
                removeItem();
            }
        } catch (SQLException exc) {
            //
            //
            //
        }
        
        //
        //
        String caminhoTelaItemConforto = "";
        request.getRequestDispatcher(caminhoTelaItemConforto).forward(request, response);
    }

    
    //
    // MÉTODOS DE CONTROLE
    //
    private void cadastraItem () throws SQLException {
        String codItem;
        String desItem;
        
        codItem = request.getParameter("codItem");
        desItem = request.getParameter("desItem");
        
        ItemConforto itemAdicionar = new ItemConforto(codItem, desItem);
        itemConforto.adiciona(itemAdicionar);
        
        return;
    }
    
    private void pesquisaItem() throws SQLException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)request.getParameter("pesquisaItem");
        tipoParametroPesquisa = (String)request.getParameter("parametroPesquisaItem");
        
        ItemConforto [] itensPesquisa = itemConforto.busca(tipoParametroPesquisa, parametroPesquisa);
        
        request.setAttribute("listaItens", itensPesquisa);
        
        return;
    }

    private void editaItem() throws SQLException {
        String codItem, desItem;
        codItem = request.getParameter("codItemSelecionado");
        desItem = request.getParameter("desItemSelecionado");
        
        ItemConforto itemConfortoAtualizado = new ItemConforto(codItem, desItem);
        
        itemConforto.atualiza(codItem, itemConfortoAtualizado);
        
        return;
    }
    
    private void removeItem() throws SQLException {
        String codItem;
        codItem = request.getParameter("codItemSelecionado");
        
        itemConforto.deleta(codItem);
        
        return;
    }
}
