package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.ItemConfortoDAO;
import br.cefetmg.inf.model.bd.dao.rel.impl.CategoriaItemConfortoDAOImpl;
import br.cefetmg.inf.model.dto.ItemConforto;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ItemConfortoControllerServlet", urlPatterns = {"/item-de-conforto"})
public class ItemConfortoControllerServlet extends HttpServlet {

    private HttpServletRequest requestInterno;
    private String operacaoItem;
    
    private ItemConfortoDAO itemConforto;
    
    @Override
    public void init() throws ServletException {
        itemConforto = ItemConfortoDAO.getInstance();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoItem = null;

        operacaoItem = requestInterno.getParameter("operacaoItem");
        
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
        
        String caminhoArquivo = "/view/itens-conforto.jsp";
        
        RequestDispatcher rd = request.getRequestDispatcher(caminhoArquivo);
        rd.forward(request, response);
        
    }

    
    //
    // MÉTODOS DE CONTROLE
    //
    private void cadastraItem () throws SQLException {
        String codItem;
        String desItem;
        
        codItem = requestInterno.getParameter("codItem");
        desItem = requestInterno.getParameter("desItem");
        
        ItemConforto itemAdicionar = new ItemConforto(codItem, desItem);
        itemConforto.adiciona(itemAdicionar);
        
        return;
    }
    
    private void pesquisaItem() throws SQLException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaItem");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaItem");
        
        ItemConforto [] itensPesquisa = itemConforto.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaItens", itensPesquisa);
        return;
    }

    private void editaItem() throws SQLException {
        String codItem, desItem;
        codItem = requestInterno.getParameter("codItemSelecionado");
        desItem = requestInterno.getParameter("desItemSelecionado");
        
        ItemConforto itemConfortoAtualizado = new ItemConforto(codItem, desItem);
        
        itemConforto.atualiza(codItem, itemConfortoAtualizado);
        
        return;
    }
    
    private void removeItem() throws SQLException {
        String codItem;
        codItem = requestInterno.getParameter("codItemSelecionado");
        
        CategoriaItemConfortoDAOImpl relacaoCategItem = CategoriaItemConfortoDAOImpl.getInstance();
        relacaoCategItem.deleta(codItem, "codItem");
        itemConforto.deleta(codItem);
        
        return;
    }

}
