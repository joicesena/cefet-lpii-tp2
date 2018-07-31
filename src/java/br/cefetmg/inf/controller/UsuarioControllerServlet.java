package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.CargoDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Cargo;
import br.cefetmg.inf.model.pojo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Logger;
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
    private JsonObject retornarDadosRegistro (String codRegistro) throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        Usuario [] registros = usuario.busca("codUsuario", codRegistro);

        Usuario registroRetorno = registros[0];

        CargoDAO cargoDAO = CargoDAO.getInstance();
        Cargo [] cargos = cargoDAO.busca("codCargo", registroRetorno.getCodCargo());
        String nomCargo = cargos[0].getNomCargo();

        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("codUsuario", registroRetorno.getCodUsuario())
            .add("nomUsuario", registroRetorno.getNomUsuario())
            .add("nomCargo", nomCargo)
            .add("desSenha", registroRetorno.getDesSenha())
            .add("desEmail", registroRetorno.getDesEmail())
            .build();

        return dadosRegistro;
    }

    private JsonObject inserirRegistro () throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String codUsuario;
        String nomUsuario;
        String nomCargo;
        String codCargo;
        String desSenha;
        String desEmail;
        
        codUsuario = requestInterno.getParameter("codUsuario");
        nomUsuario = requestInterno.getParameter("nomUsuario");
        desEmail = requestInterno.getParameter("desEmail");
        nomCargo = requestInterno.getParameter("nomCargo");
        
        desSenha = UtilidadesBD.stringParaSHA256(requestInterno.getParameter("desSenha"));
        
        CargoDAO cargo = CargoDAO.getInstance();
        Cargo [] cargosPesquisados = cargo.busca("nomCargo", nomCargo);
        codCargo = cargosPesquisados[0].getCodCargo();
        
        Usuario usuarioAdicionar = new Usuario(codUsuario, nomUsuario, codCargo, desSenha, desEmail);
        
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
    
    private JsonObject editarRegistro() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String codUsuario;
        String nomUsuario;
        String nomCargo;
        String codCargo;
        String desSenha;
        String desEmail;
        
        codUsuario = requestInterno.getParameter("codUsuario");
        nomUsuario = requestInterno.getParameter("nomUsuario");
        desEmail = requestInterno.getParameter("desEmail");
        nomCargo = requestInterno.getParameter("nomCargo");
        
        desSenha = UtilidadesBD.stringParaSHA256(requestInterno.getParameter("desSenha"));
        
        CargoDAO cargo = CargoDAO.getInstance();
        Cargo [] cargosPesquisados = cargo.busca("nomCargo", nomCargo);
        codCargo = cargosPesquisados[0].getCodCargo();
        
        Usuario registroAtualizado = new Usuario(codUsuario, nomUsuario, codCargo, desSenha, desEmail);
        
        JsonObject dadosRegistro;

        boolean testeRegistro = usuario.atualiza(codUsuario, registroAtualizado);;
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
        
        return;
    }

    private JsonObject removerRegistro() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String codUsuario;
        codUsuario = requestInterno.getParameter("codUsuarioSelecionado");
        
        HttpSession session = requestInterno.getSession();
        
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario[] usuarios = usuarioDAO.busca("codUsuario", session.getAttribute("codUsuario"));
        System.out.println("usuarios.length "+usuarios.length);
        
        String senhaSHA256 = requestInterno.getParameter("senhaFuncionario");
        String senha = UtilidadesBD.stringParaSHA256(senhaSHA256);
        
        JsonObject dadosRegistro;

        if ((usuarios[0].getDesSenha()).equals(senha)) {
            boolean testeExclusaoItem = usuario.deleta(codUsuario);
            
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
