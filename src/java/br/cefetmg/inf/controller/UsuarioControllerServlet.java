package br.cefetmg.inf.controller;

import br.cefetmg.inf.exception.PKRepetidaException;
import br.cefetmg.inf.exception.RegistroUtilizadoExternamenteException;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.dao.rel.impl.QuartoConsumoDAOImpl;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Usuario;
import br.cefetmg.inf.model.pojo.rel.QuartoConsumo;
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

@WebServlet(name = "UsuarioControllerServlet", urlPatterns = {"/funcionario"})
public class UsuarioControllerServlet extends HttpServlet {

    private HttpServletRequest requestInterno;
    private int operacaoRegistro;
    
    private String codRegistroSelecionado;
    
    private UsuarioDAO usuario;
    
    @Override
    public void init() throws ServletException {
        usuario = UsuarioDAO.getInstance();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoRegistro = 0;

        operacaoRegistro = Integer.parseInt(requestInterno.getParameter("operacaoItem"));
        JsonObject retorno;
        
        try {
            if (operacaoRegistro == 1) {
                codRegistroSelecionado = request.getParameter("codUsuario");
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
    private JsonObject retornarDadosRegistro (String codRegistro) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Usuario [] registros = usuario.busca("codUsuario", codRegistro);
        Usuario registroRetorno = registros[0];
        
        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("codUsuario", codRegistro)
            .add("nomUsuario", registroRetorno.getNomUsuario())
            .add("desSenha", registroRetorno.getDesSenha())
            .add("desEmail", registroRetorno.getDesEmail())
            .add("codCargo", registroRetorno.getCodCargo())
            .build();

        return dadosRegistro;
    }

    private JsonObject inserirRegistro () throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, PKRepetidaException {
        String codUsuario;
        String nomUsuario;
        String codCargo;
        String desEmail;
        
        codUsuario = requestInterno.getParameter("codUsuario");
        nomUsuario = requestInterno.getParameter("nomUsuario");
        desEmail = requestInterno.getParameter("desEmail");
        codCargo = requestInterno.getParameter("codCargo");
        
        String senha = requestInterno.getParameter("desSenha");

        //
        // TESTA SE JÁ EXISTE ALGUM REGISTRO COM AQUELA PK
        // LANÇA EXCEÇÃO
        Usuario [] registrosBuscados = usuario.busca("codUsuario", codUsuario);
        if (registrosBuscados.length > 0)
            throw new PKRepetidaException("inserir");
        //
        //
        
        Usuario usuarioAdicionar = new Usuario(codUsuario, nomUsuario, codCargo, senha, desEmail);
        
        JsonObject dadosRegistro;
        
        boolean testeRegistro = usuario.adiciona(usuarioAdicionar);
        
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
    
    private JsonObject editarRegistro() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, PKRepetidaException, RegistroUtilizadoExternamenteException {
        String codUsuario;
        String nomUsuario;
        String codCargo;
        String desEmail;
        
        codUsuario = requestInterno.getParameter("codUsuario");
        nomUsuario = requestInterno.getParameter("nomUsuario");
        desEmail = requestInterno.getParameter("desEmail");
        codCargo = requestInterno.getParameter("codCargo");        
        String senha = requestInterno.getParameter("desSenha");

        Usuario registroAtualizado = new Usuario(codUsuario, nomUsuario, codCargo, senha, desEmail);
        
        JsonObject dadosRegistro;

        //
        // TESTA SE JÁ EXISTE ALGUM REGISTRO COM AQUELA PK
        // LANÇA EXCEÇÃO
        //
        if (!codUsuario.equals(codRegistroSelecionado)) {
            Usuario [] registrosBuscados = usuario.busca("codUsuario", codUsuario);
            if (registrosBuscados.length > 0)
                throw new PKRepetidaException("alterar");
        }
        //
        // testa se o usuario tentando modificar é o mesmo sendo modificado
        HttpSession session = requestInterno.getSession();
        String codUsuarioLogado = (String) session.getAttribute("codUsuario");
        if (codRegistroSelecionado.equals(codUsuarioLogado)) {
            if (!codUsuario.equals(codUsuarioLogado)) {
                throw new RegistroUtilizadoExternamenteException("Não é possível alterar seu próprio código!");
            }
        }
        //
        //
        
        boolean testeRegistro = usuario.atualiza(codRegistroSelecionado, registroAtualizado);;
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

    private void pesquisarRegistro () throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaUsuario");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaUsuario");
        
        Usuario [] usuariosPesquisa = usuario.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaUsuarios", usuariosPesquisa);
    }

    private JsonObject removerRegistro() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException, RegistroUtilizadoExternamenteException {
        HttpSession session = requestInterno.getSession();
        
        //
        // testa se o codUsuario é usado em QuartoConsumo
        QuartoConsumoDAOImpl dao = QuartoConsumoDAOImpl.getInstance();
        QuartoConsumo [] relacionamento = dao.busca(codRegistroSelecionado, "codUsuarioRegistro");
        if (relacionamento.length > 1) {
            for (QuartoConsumo reg : relacionamento) {
                dao.deleta(reg);
                reg.setCodUsuarioRegistro(null);
                dao.adiciona(reg);
            }
        }
        //
        // testa se o usuario que quer remover é o mesmo a ser removido
        String codUsuarioLogado = (String) session.getAttribute("codUsuario");
        if (codRegistroSelecionado.equals(codUsuarioLogado)) {
            throw new RegistroUtilizadoExternamenteException("Não é possível se excluir do sistema!");
        }
        //
        //
        
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario[] usuarios = usuarioDAO.busca("codUsuario", session.getAttribute("codUsuario"));
        
        String senhaSHA256 = requestInterno.getParameter("senhaFuncionario");
        String senha = UtilidadesBD.stringParaSHA256(senhaSHA256);
        
        JsonObject dadosRegistro;

        if ((usuarios[0].getDesSenha()).equals(senha)) {
            boolean testeExclusaoItem = usuario.deleta(codRegistroSelecionado);
            
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
