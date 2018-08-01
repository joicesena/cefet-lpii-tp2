package br.cefetmg.inf.controller;

import br.cefetmg.inf.exception.PKRepetidaException;
import br.cefetmg.inf.exception.RegistroUtilizadoExternamenteException;
import br.cefetmg.inf.model.bd.dao.ItemConfortoDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.dao.rel.impl.CategoriaItemConfortoDAOImpl;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.ItemConforto;
import br.cefetmg.inf.model.pojo.Usuario;
import br.cefetmg.inf.model.pojo.rel.CategoriaItemConforto;
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
    private int operacaoRegistro;
    
    private String codRegistroSelecionado;
    
    private ItemConfortoDAO itemConforto;
    
    @Override
    public void init() throws ServletException {
        itemConforto = ItemConfortoDAO.getInstance();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoRegistro = 0;

        operacaoRegistro = Integer.parseInt(requestInterno.getParameter("operacaoItem"));
        JsonObject retorno;
        
        try {
            if (operacaoRegistro == 1) {
                codRegistroSelecionado = request.getParameter("codItem");
                retorno = retornarDadosRegistro(codRegistroSelecionado);
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoRegistro == 2) {
                retorno = inserirRegistro();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoRegistro == 3) {
                pesquisarRegistro();
            } else if (operacaoRegistro == 4) {
                retorno = editarRegistro();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoRegistro == 5) {
                retorno = removerRegistro();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            }
        } catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException exc ) {
            retorno = Json.createObjectBuilder()
                .add("success", false)
                .add("mensagem", "Erro! Tente novamente")
                .build();
            response.setContentType("text/json");
            PrintWriter out = response.getWriter();
            out.print(retorno);
        } catch (PKRepetidaException | RegistroUtilizadoExternamenteException ex) {
            retorno = Json.createObjectBuilder()
                .add("success", true)
                .add("mensagem", ex.getMessage())
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
    

    //
    // MÉTODOS DE CONTROLE
    //
    private JsonObject retornarDadosRegistro (String codItem) throws SQLException {
        ItemConforto [] itensPesquisa = itemConforto.busca("codItem", codItem);
        ItemConforto itemRetorno = itensPesquisa[0];

        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("codigoItem", itemRetorno.getCodItem())
            .add("descricaoItem", itemRetorno.getDesItem())
            .build();

        return dadosRegistro;
    }
    
    private JsonObject inserirRegistro () throws SQLException, PKRepetidaException {
        String codItem;
        String desItem;
        
        codItem = requestInterno.getParameter("codigoItem");
        desItem = requestInterno.getParameter("descricaoItem");
        
        JsonObject dadosRegistro;
        
        //
        // TESTA SE JÁ EXISTE ALGUM REGISTRO COM AQUELA PK
        // LANÇA EXCEÇÃO
        ItemConforto [] registrosBuscados = itemConforto.busca("codItem", codItem);
        if (registrosBuscados.length > 0)
            throw new PKRepetidaException("inserir");
        //
        //
        
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
    
    private void pesquisarRegistro() throws SQLException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaItem");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaItem");
        
        ItemConforto [] itensPesquisa = itemConforto.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaItens", itensPesquisa);
    }

    private JsonObject editarRegistro() throws SQLException, PKRepetidaException, RegistroUtilizadoExternamenteException {
        String codItem, desItem;
        codItem = requestInterno.getParameter("codigoItem");
        desItem = requestInterno.getParameter("descricaoItem");
        
        ItemConforto itemConfortoAtualizado = new ItemConforto(codItem, desItem);
        JsonObject dadosRegistro;
        
        //
        // TESTA SE JÁ EXISTE ALGUM REGISTRO COM AQUELA PK
        // LANÇA EXCEÇÃO
        //
        if (!codItem.equals(codRegistroSelecionado)) {
            ItemConforto [] registrosBuscados = itemConforto.busca("codItem", codItem);
            if (registrosBuscados.length > 0)
                throw new PKRepetidaException("alterar");
        }
        //
        // TESTA SE O CÓDIGO ATUAL É UTILIZADO EM CATEGORIA DE QUARTO
        // LANÇA EXCEÇÃO
        //
        CategoriaItemConfortoDAOImpl dao = CategoriaItemConfortoDAOImpl.getInstance();
        if (!codItem.equals(codRegistroSelecionado)) {
            CategoriaItemConforto [] registrosExternosBuscados = dao.busca(codRegistroSelecionado, "codItem");
            if (registrosExternosBuscados.length > 0)
                throw new RegistroUtilizadoExternamenteException("modificar", "categoria de quarto");
        }
        //
        //

        boolean testeRegistro = itemConforto.atualiza(codRegistroSelecionado, itemConfortoAtualizado);
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
    
    private JsonObject removerRegistro() throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException, RegistroUtilizadoExternamenteException {
        CategoriaItemConfortoDAOImpl relacaoCategItem = CategoriaItemConfortoDAOImpl.getInstance();
        
        JsonObject dadosRegistro;

        //
        // TESTA SE O CÓDIGO ATUAL É UTILIZADO EM CATEGORIA DE QUARTO
        // LANÇA EXCEÇÃO
        //
        CategoriaItemConfortoDAOImpl dao = CategoriaItemConfortoDAOImpl.getInstance();
        CategoriaItemConforto [] registrosExternosBuscados = dao.busca(codRegistroSelecionado, "codItem");
        if (registrosExternosBuscados.length > 0)
            throw new RegistroUtilizadoExternamenteException("modificar", "categoria de quarto");
        //
        //
        
        HttpSession session = requestInterno.getSession();
        
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario[] usuarios = usuarioDAO.busca("codUsuario", session.getAttribute("codUsuario"));
        
        String senhaSHA256 = requestInterno.getParameter("senhaFuncionario");
        String senha = UtilidadesBD.stringParaSHA256(senhaSHA256);
        
        if ((usuarios[0].getDesSenha()).equals(senha)) {
            boolean testeExclusaoItem = itemConforto.deleta(codRegistroSelecionado);
            
            boolean testeExclusaoItemRel = true;
            if (registrosExternosBuscados.length > 0) {
                testeExclusaoItemRel = relacaoCategItem.deleta(codRegistroSelecionado, "codItem");
            }
            
            if (testeExclusaoItem && testeExclusaoItemRel) {
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
