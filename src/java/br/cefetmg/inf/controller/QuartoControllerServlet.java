package br.cefetmg.inf.controller;

import br.cefetmg.inf.exception.PKRepetidaException;
import br.cefetmg.inf.exception.RegistroUtilizadoExternamenteException;
import br.cefetmg.inf.model.bd.dao.CategoriaQuartoDAO;
import br.cefetmg.inf.model.bd.dao.QuartoDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.dao.rel.impl.QuartoHospedagemDAOImpl;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.CategoriaQuarto;
import br.cefetmg.inf.model.pojo.Quarto;
import br.cefetmg.inf.model.pojo.Usuario;
import br.cefetmg.inf.model.pojo.rel.QuartoHospedagem;
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

@WebServlet(name = "QuartoControllerServlet", urlPatterns = {"/quarto"})
public class QuartoControllerServlet extends HttpServlet {

    private HttpServletRequest requestInterno;
    private int operacaoRegistro;
    
    private int codRegistroSelecionado;
    
    private QuartoDAO quarto;
    
    @Override
    public void init() throws ServletException {
        quarto = QuartoDAO.getInstance();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoRegistro = 0;

        operacaoRegistro = Integer.parseInt(requestInterno.getParameter("operacaoItem"));
        JsonObject retorno;
        
        try {
            if (operacaoRegistro == 1) {
                codRegistroSelecionado = Integer.parseInt(request.getParameter("nroQuarto"));
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
    private JsonObject retornarDadosRegistro (int nroQuarto) throws SQLException {
        Quarto [] quartos = quarto.busca("nroQuarto", nroQuarto);
        Quarto quartoRetorno = quartos[0];

        CategoriaQuartoDAO categoriaDAO = CategoriaQuartoDAO.getInstance();
        CategoriaQuarto[] categoria = categoriaDAO.busca("codCategoria", quartoRetorno.getCodCategoria());
        String nomCategoria = categoria[0].getNomCategoria();

        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("nroQuarto", quartoRetorno.getNroQuarto())
            .add("codCategoria", quartoRetorno.getCodCategoria())
            .add("nomCategoria", nomCategoria)
            .build();

        return dadosRegistro;
    }

    private JsonObject inserirRegistro () throws SQLException, PKRepetidaException {
        int nroQuarto;
        String codCategoria;
        
        nroQuarto = Integer.parseInt(requestInterno.getParameter("nroQuarto"));
        codCategoria = requestInterno.getParameter("codCategoria");
        
        Quarto quartoAdicionar = new Quarto(nroQuarto, codCategoria, false);

        JsonObject dadosRegistro;
        
        //
        // TESTA SE JÁ EXISTE ALGUM REGISTRO COM AQUELA PK
        // LANÇA EXCEÇÃO
        Quarto [] registrosBuscados = quarto.busca("nroQuarto", nroQuarto);
        if (registrosBuscados.length > 0)
            throw new PKRepetidaException("inserir");
        //
        //

        boolean testeRegistro = quarto.adiciona(quartoAdicionar);
        
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
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaQuarto");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaQuarto");
        
        Quarto [] quartosPesquisa = quarto.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaCategorias", quartosPesquisa);
    }

    private JsonObject editarRegistro() throws SQLException, PKRepetidaException, RegistroUtilizadoExternamenteException {
        int nroQuarto;
        String codCategoria;
        
        nroQuarto = Integer.parseInt(requestInterno.getParameter("nroQuarto"));
        codCategoria = requestInterno.getParameter("codCategoria");

        Quarto quartoAdicionar = new Quarto(nroQuarto, codCategoria, false);
        
        JsonObject dadosRegistro;

        //
        // TESTA SE JÁ EXISTE ALGUM REGISTRO COM AQUELA PK
        // LANÇA EXCEÇÃO
        //
        if (nroQuarto != codRegistroSelecionado) {
            Quarto [] registrosBuscados = quarto.busca("nroQuarto", nroQuarto);
            if (registrosBuscados.length > 0)
                throw new PKRepetidaException("alterar");
        }
        //

        boolean testeRegistro = quarto.atualiza(codRegistroSelecionado, quartoAdicionar);
        
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
        Quarto [] quartoBuscado = quarto.busca("nroQuarto", codRegistroSelecionado);
        
        //
        // testa se ele tem idtOcupado (não pode remover o quarto se tiver gente nele)
        // lança exceção
        if (quartoBuscado[0].isIdtOcupado()) {
            throw new RegistroUtilizadoExternamenteException("Não é possível excluir esse quarto. Ele está ocupado!");
        }
        //
        // testa se o nroQuarto está sendo usado em QuartoHospedagem
        // lança exceção
        QuartoHospedagemDAOImpl dao = QuartoHospedagemDAOImpl.getInstance();
        QuartoHospedagem [] registrosExternosBuscados = dao.busca(codRegistroSelecionado, "nroQuarto");
        if (registrosExternosBuscados.length > 0) {
            throw new RegistroUtilizadoExternamenteException("modificar", "categoria de quarto");
        }
        //
        //
        
        HttpSession session = requestInterno.getSession();
        
        JsonObject dadosRegistro;

        if (!quartoBuscado[0].isIdtOcupado()) {
            // quarto livre
            UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
            Usuario[] usuarios = usuarioDAO.busca("codUsuario", session.getAttribute("codUsuario"));

            String senhaSHA256 = requestInterno.getParameter("senhaFuncionario");
            String senha = UtilidadesBD.stringParaSHA256(senhaSHA256);

            if ((usuarios[0].getDesSenha()).equals(senha)) {
                boolean testeExclusaoItem = quarto.deleta(codRegistroSelecionado);

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
            
        } else {
            // quarto ocupado
            dadosRegistro = Json.createObjectBuilder()
                .add("sucesso", false)
                .add("mensagem", "Não é possível excluir esse quarto. Está ocupado!")
                .build();
        }

        return dadosRegistro;
    }

}
