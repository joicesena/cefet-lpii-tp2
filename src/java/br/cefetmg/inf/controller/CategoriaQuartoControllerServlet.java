package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.CategoriaQuartoDAO;
import br.cefetmg.inf.model.bd.dao.ItemConfortoDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.dao.rel.impl.CategoriaItemConfortoDAOImpl;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.CategoriaQuarto;
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

@WebServlet(name = "CategoriaQuartoControllerServlet", urlPatterns = {"/categoriad-de-quarto"})
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
                codRegistroSelecionado = request.getParameter("seqServico");
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
        
        // buscar os itens de conforto que se relacionam com a categoria
        CategoriaItemConfortoDAOImpl relDAO = CategoriaItemConfortoDAOImpl.getInstance();
        String[] codigos = relDAO.busca(codRegistro, "codCategoria");
        // montar uma string com as descrições dos itens
        String itensConforto = "";
        for (String cod : codigos) {
            itensConforto += cod + "; ";
        }

        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("codCategoria", registroRetorno.getCodCategoria())
            .add("nomCategoria", registroRetorno.getNomCategoria())
            .add("vlrDiaria", registroRetorno.getVlrDiaria())
            .add("itensConforto", itensConforto)
            .build();

        return dadosRegistro;
    }

    private JsonObject inserirRegistro () throws SQLException {
        String codCategoria;
        String nomCategoria;
        Double vlrDiaria;
        String [] itensConforto;
        String codItem;
        
        codCategoria = requestInterno.getParameter("codCategoria");
        nomCategoria = requestInterno.getParameter("desCategoria");
        vlrDiaria = Double.parseDouble(requestInterno.getParameter("vlrDiaria"));
        itensConforto = requestInterno.getParameterValues("itensConforto");
        
        CategoriaQuarto categoriaAdicionar = new CategoriaQuarto(codCategoria, nomCategoria, vlrDiaria);
        
        JsonObject dadosRegistro;
        
        boolean testeRegistro = categoriaQuarto.adiciona(categoriaAdicionar);
        
        if (testeRegistro) {
            CategoriaItemConfortoDAOImpl relacaoCategItem = CategoriaItemConfortoDAOImpl.getInstance();
            ItemConfortoDAO itemConfortoDAO = ItemConfortoDAO.getInstance();

            for (String item : itensConforto) {
                // busca o codigo dos itens que tem aquelas descrições
                ItemConforto [] itemConforto = itemConfortoDAO.busca("desItem", item);

                codItem = itemConforto[0].getCodItem();

                // adiciona o relacionamento
                relacaoCategItem.adiciona(codCategoria, codItem);
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
        
        return;
    }

    private JsonObject editarRegistro() throws SQLException {
        String codCategoria, nomCategoria;
        Double vlrDiaria;
        String [] itensConforto;
        String codItem;
        
        codCategoria = requestInterno.getParameter("codCategoria");
        nomCategoria = requestInterno.getParameter("nomCategoria");
        vlrDiaria = Double.parseDouble(requestInterno.getParameter("vlrDiaria"));
        itensConforto = requestInterno.getParameterValues("itensConforto");
        
        CategoriaQuarto categoriaQuartoAtualizada = new CategoriaQuarto(codCategoria, nomCategoria, vlrDiaria);
        
        CategoriaItemConfortoDAOImpl relacaoCategItem = CategoriaItemConfortoDAOImpl.getInstance();
        ItemConfortoDAO itemConfortoDAO = ItemConfortoDAO.getInstance();

        relacaoCategItem.deleta(codCategoria, "codCategoria");
        for (String item : itensConforto) {
            ItemConforto [] itemConforto = itemConfortoDAO.busca("desItem", item);
            codItem = itemConforto[0].getCodItem();
            relacaoCategItem.adiciona(codCategoria, codItem);
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
    
    private JsonObject removerRegistro() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        CategoriaItemConfortoDAOImpl relacaoCategItem = CategoriaItemConfortoDAOImpl.getInstance();
        
        HttpSession session = requestInterno.getSession();
        
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario[] usuarios = usuarioDAO.busca("codUsuario", session.getAttribute("codUsuario"));
        System.out.println("usuarios.length "+usuarios.length);
        
        String senhaSHA256 = requestInterno.getParameter("senhaFuncionario");
        String senha = UtilidadesBD.stringParaSHA256(senhaSHA256);
        
        JsonObject dadosRegistro;

        if ((usuarios[0].getDesSenha()).equals(senha)) {
            relacaoCategItem.deleta(codRegistroSelecionado, "codCategoria");
            boolean testeExclusaoItem = categoriaQuarto.deleta(codRegistroSelecionado);
            
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
