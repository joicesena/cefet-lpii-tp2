package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.ItemConfortoDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.dao.rel.impl.CategoriaItemConfortoDAOImpl;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.ItemConforto;
import br.cefetmg.inf.model.dto.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "ItemConfortoControllerServlet", urlPatterns = {"/item-de-conforto"})
public class ItemConfortoControllerServlet extends HttpServlet {

    private HttpServletRequest requestInterno;
    private int operacaoItem;
    
    private String codItemSelecionado;
    
    private ItemConfortoDAO itemConforto;
    
    @Override
    public void init() throws ServletException {
        itemConforto = ItemConfortoDAO.getInstance();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoItem = 0;

        operacaoItem = Integer.parseInt(requestInterno.getParameter("operacaoItem"));
        JsonObject retorno;
        
//        String caminhoTela = "http://localhost:8080/cefet-lpii-tp2/view/itens-conforto.jsp";
        
        try {
            if (operacaoItem == 1) {
                codItemSelecionado = request.getParameter("codItem");
                retorno = retornarDadosRegistro(codItemSelecionado);
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoItem == 2) {
                retorno = inserirItem();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
//                response.sendRedirect(caminhoTela);
            } else if (operacaoItem == 3) {
                pesquisarItem();
            } else if (operacaoItem == 4) {
                retorno = editarItem();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoItem == 5) {
                retorno = removerItem();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
//                response.sendRedirect(caminhoTela);
            }
        } catch (SQLException exc) {
            //
            //
            //
        } catch (NoSuchAlgorithmException ex) {
            //
            //
            //
        } catch (UnsupportedEncodingException ex) {
            //
            //
            //
        }
        
//        String caminhoArquivo = "/view/itens-conforto.jsp";
//        
//        RequestDispatcher rd = request.getRequestDispatcher(caminhoArquivo);
//        rd.forward(request, response);
        
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
    

    //
    // MÉTODOS DE CONTROLE
    //
    private JsonObject retornarDadosRegistro (String codItem) throws SQLException {
//        System.out.println("codItem - "+codItem);
        ItemConforto [] itensPesquisa = itemConforto.busca("codItem", codItem);
        ItemConforto itemRetorno = itensPesquisa[0];

        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("codigoItem", itemRetorno.getCodItem())
            .add("descricaoItem", itemRetorno.getDesItem())
            .build();

        return dadosRegistro;
    }
    
    private JsonObject inserirItem () throws SQLException {
        String codItem;
        String desItem;
        
        codItem = requestInterno.getParameter("codigoItem");
        desItem = requestInterno.getParameter("descricaoItem");
        
        JsonObject dadosRegistro;
        
        ItemConforto itemAdicionar = new ItemConforto(codItem, desItem);
        boolean testeRegistro = itemConforto.adiciona(itemAdicionar);
        
        if (testeRegistro) {
            dadosRegistro = Json.createObjectBuilder()
                .add("success", true)
                .add("mensagem", "Registro adicionado com sucesso!")
                .build();
        } else {
            dadosRegistro = Json.createObjectBuilder()
                .add("success", false)
                .add("mensagem", "Ocorreu erro ao adicionar o registro. Repita a operação.")
                .build();
        }

        return dadosRegistro;
    }
    
    private void pesquisarItem() throws SQLException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaItem");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaItem");
        
        ItemConforto [] itensPesquisa = itemConforto.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaItens", itensPesquisa);
        return;
    }

    private JsonObject editarItem() throws SQLException {
        String codItem, desItem;
        codItem = requestInterno.getParameter("codigoItem");
        desItem = requestInterno.getParameter("descricaoItem");
        
        ItemConforto itemConfortoAtualizado = new ItemConforto(codItem, desItem);
        JsonObject dadosRegistro;
        
        boolean testeRegistro = itemConforto.atualiza(codItemSelecionado, itemConfortoAtualizado);
        if (testeRegistro) {
            dadosRegistro = Json.createObjectBuilder()
                .add("sucesso", true)
                .add("mensagem", "Registro alterado com sucesso!")
                .build();
        } else {
            dadosRegistro = Json.createObjectBuilder()
                .add("sucesso", false)
                .add("mensagem", "Ocorreu erro ao alterar o registro. Repita a operação.")
                .build();
        }

        return dadosRegistro;
    }
    
    private JsonObject removerItem() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        CategoriaItemConfortoDAOImpl relacaoCategItem = CategoriaItemConfortoDAOImpl.getInstance();
        
        JsonObject dadosRegistro;
        
        HttpSession session = requestInterno.getSession();
        
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario[] usuarios = usuarioDAO.busca("codUsuario", session.getAttribute("codUsuario"));
        System.out.println("usuarios.length "+usuarios.length);
        
        String senhaSHA256 = requestInterno.getParameter("senhaFuncionario");
        String senha = UtilidadesBD.stringParaSHA256(senhaSHA256);
        
        if ((usuarios[0].getDesSenha()).equals(senha)) {
            boolean testeExclusaoItem = itemConforto.deleta(codItemSelecionado);
            boolean testeExclusaoItemRel = relacaoCategItem.deleta(codItemSelecionado, "codItem");
            
            System.out.println("testeExclusaoItem " + testeExclusaoItem);
            System.out.println("testeExclusaoItemRel " + testeExclusaoItemRel);
            
            if (testeExclusaoItem) {
                dadosRegistro = Json.createObjectBuilder()
                    .add("sucesso", true)
                    .add("mensagem", "Registro excluído com sucesso!")
                    .build();
            } else {
                dadosRegistro = Json.createObjectBuilder()
                    .add("sucesso", false)
                    .add("mensagem", "Ocorreu erro ao excluir o registro. Repita a operação.")
                    .build();
            }
        } else {
            dadosRegistro = Json.createObjectBuilder()
                .add("sucesso", false)
                .add("mensagem", "Senha inválida")
                .build();
        }

        return dadosRegistro;
    }

}
