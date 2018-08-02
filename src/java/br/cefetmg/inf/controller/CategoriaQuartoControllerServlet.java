package br.cefetmg.inf.controller;

import br.cefetmg.inf.exception.PKRepetidaException;
import br.cefetmg.inf.exception.RegistroUtilizadoExternamenteException;
import br.cefetmg.inf.model.bd.dao.CategoriaQuartoDAO;
import br.cefetmg.inf.model.bd.dao.QuartoDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.dao.rel.impl.CategoriaItemConfortoDAOImpl;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.CategoriaQuarto;
import br.cefetmg.inf.model.pojo.Quarto;
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

@WebServlet(name = "CategoriaQuartoControllerServlet", urlPatterns = {"/categoria-de-quarto"})
public class CategoriaQuartoControllerServlet extends HttpServlet {

    private HttpServletRequest requestInterno;
    private int operacaoRegistro;
    
    private String codRegistroSelecionado;
    
    private CategoriaQuartoDAO categoriaQuarto;
    
    @Override
    public void init() throws ServletException {
        categoriaQuarto = CategoriaQuartoDAO.getInstance();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoRegistro = 0;

        operacaoRegistro = Integer.parseInt(requestInterno.getParameter("operacaoItem"));
        JsonObject retorno;
        
        try {
            if (operacaoRegistro == 1) {
                codRegistroSelecionado = request.getParameter("codCategoria");
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
    private JsonObject retornarDadosRegistro (String codRegistro) throws SQLException {
        CategoriaQuarto [] registros = categoriaQuarto.busca("codCategoria", codRegistro);

        CategoriaQuarto registroRetorno = registros[0];
        
//        // buscar os itens de conforto que se relacionam com a categoria
//        CategoriaItemConfortoDAOImpl relDAO = CategoriaItemConfortoDAOImpl.getInstance();
//        CategoriaItemConforto[] vetorRel = relDAO.busca(codRegistro, "codCategoria");
//        // montar uma string com as descrições dos itens
//        String itensConforto = "";
//
//        String codCategoria = null;
//        for (CategoriaItemConforto cic : vetorRel) {
//            codCategoria = cic.getCodCategoria();
//            CategoriaQuarto [] buscaCategoria = categoriaQuarto.busca("codCategoria", codCategoria);
//            itensConforto += buscaCategoria[0].getNomCategoria() + "; ";
//        }

        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("codCategoria", registroRetorno.getCodCategoria())
            .add("nomCategoria", registroRetorno.getNomCategoria())
            .add("vlrDiaria", registroRetorno.getVlrDiaria())
//            .add("itensConforto", itensConforto)
            .build();

        return dadosRegistro;
    }

    private JsonObject inserirRegistro ()  throws SQLException, PKRepetidaException {
        String codCategoria;
        String nomCategoria;
        Double vlrDiaria;

        String [] codItensSelecionados;
        
        codCategoria = requestInterno.getParameter("codCategoria");
        nomCategoria = requestInterno.getParameter("nomCategoria");
        vlrDiaria = Double.parseDouble(requestInterno.getParameter("vlrDiaria"));
        codItensSelecionados = requestInterno.getParameterValues("codItem");
        
        CategoriaQuarto categoriaAdicionar = new CategoriaQuarto(codCategoria, nomCategoria, vlrDiaria);
        
        JsonObject dadosRegistro;
        
        //
        // TESTA SE JÁ EXISTE ALGUM REGISTRO COM AQUELA PK
        // LANÇA EXCEÇÃO
        CategoriaQuarto [] registrosBuscados = categoriaQuarto.busca("codCategoria", codCategoria);
        if (registrosBuscados.length > 0)
            throw new PKRepetidaException("inserir");
        //
        //

        boolean testeRegistro = categoriaQuarto.adiciona(categoriaAdicionar);
        
        if (testeRegistro) {
            CategoriaItemConfortoDAOImpl relacaoCategItem = CategoriaItemConfortoDAOImpl.getInstance();
            
            if (codItensSelecionados != null) {
                for (String item : codItensSelecionados) {
                    relacaoCategItem.adiciona(codCategoria, item);
                }
            }
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
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaCategoria");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaCategoria");
        
        CategoriaQuarto [] categoriasPesquisa = categoriaQuarto.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaCategorias", categoriasPesquisa);
    }

    private JsonObject editarRegistro() throws SQLException, PKRepetidaException, RegistroUtilizadoExternamenteException {
        String codCategoria, nomCategoria;
        Double vlrDiaria;
        
        String [] codItensSelecionados;
        
        codCategoria = requestInterno.getParameter("codCategoria");
        nomCategoria = requestInterno.getParameter("nomCategoria");
        vlrDiaria = Double.parseDouble(requestInterno.getParameter("vlrDiaria"));
        codItensSelecionados = requestInterno.getParameterValues("codItem");
        
        CategoriaQuarto categoriaQuartoAtualizada = new CategoriaQuarto(codCategoria, nomCategoria, vlrDiaria);
        
        //
        // TESTA SE JÁ EXISTE ALGUM REGISTRO COM AQUELA PK
        // LANÇA EXCEÇÃO
        //
        if (!codCategoria.equals(codRegistroSelecionado)) {
            CategoriaQuarto [] registrosBuscados = categoriaQuarto.busca("codCategoria", codCategoria);
            if (registrosBuscados.length > 0)
                throw new PKRepetidaException("alterar");
        }
        //
        // TESTA SE O CÓDIGO ATUAL É USADO EM QUARTO
        // LANÇA EXCEÇÃO
        //
        QuartoDAO dao1 = QuartoDAO.getInstance();
        if (!codCategoria.equals(codRegistroSelecionado)) {
            Quarto [] registrosExternosBuscados = dao1.busca("codServicoArea", codRegistroSelecionado);
            if (registrosExternosBuscados.length > 0)
                throw new RegistroUtilizadoExternamenteException("modificar", "quarto");
        }
        //
        //
        
        CategoriaItemConfortoDAOImpl relacaoCategItem = CategoriaItemConfortoDAOImpl.getInstance();

        relacaoCategItem.deleta(codCategoria, "codCategoria");
        if (codItensSelecionados != null) {
            for (String item : codItensSelecionados) {
                relacaoCategItem.adiciona(codCategoria, item);
            }
        }
        
        JsonObject dadosRegistro;

        boolean testeRegistro = categoriaQuarto.atualiza(codRegistroSelecionado, categoriaQuartoAtualizada);
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
        HttpSession session = requestInterno.getSession();
        
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario[] usuarios = usuarioDAO.busca("codUsuario", session.getAttribute("codUsuario"));
        String senhaSHA256 = requestInterno.getParameter("senhaFuncionario");
        String senha = UtilidadesBD.stringParaSHA256(senhaSHA256);
        
        JsonObject dadosRegistro;

        //
        // TESTA SE O CÓDIGO ATUAL É USADO EM QUARTO
        // LANÇA EXCEÇÃO
        //
        QuartoDAO dao1 = QuartoDAO.getInstance();
        Quarto [] registrosExternosBuscados1 = dao1.busca("codCategoria", codRegistroSelecionado);
        if (registrosExternosBuscados1.length > 0)
            throw new RegistroUtilizadoExternamenteException("excluir", "quarto");
        //
        //
        
        if ((usuarios[0].getDesSenha()).equals(senha)) {
            CategoriaItemConfortoDAOImpl relacaoCategItem = CategoriaItemConfortoDAOImpl.getInstance();
            CategoriaItemConforto [] registrosExternosBuscados = relacaoCategItem.busca(codRegistroSelecionado, "codCategoria");
            
            boolean testeExclusaoCatRel = true;
            if (registrosExternosBuscados.length > 0) {
                testeExclusaoCatRel = relacaoCategItem.deleta(codRegistroSelecionado, "codCategoria");
            }

            boolean testeExclusaoItem = categoriaQuarto.deleta(codRegistroSelecionado);
//            if (testeExclusaoItem) {
            if (testeExclusaoItem && testeExclusaoCatRel) {
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
