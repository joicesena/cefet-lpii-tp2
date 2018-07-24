package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.CargoDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Cargo;
import br.cefetmg.inf.model.dto.Usuario;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UsuarioCadastroServlet", urlPatterns = {"/cadastroFuncionario"})
public class UsuarioCadastroServlet extends HttpServlet {

    private HttpServletRequest request;
    private String operacaoUsuario;
    
    private UsuarioDAO usuario;
    
    @Override
    public void init() throws ServletException {
        usuario = UsuarioDAO.getInstance();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        operacaoUsuario = null;

        operacaoUsuario = request.getParameter("operacaoUsuario");
        
        try {
            if (operacaoUsuario.equals("cadastrarUsuario")) {
                cadastraUsuario();
            } else if (operacaoUsuario.equals("editarUsuario")) {
                editaUsuario();
            }
        } catch (SQLException exc) {
            //
            //
            //
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioCadastroServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UsuarioCadastroServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //
        //
        String caminhoTelaUsuario = "";
        response.sendRedirect(caminhoTelaUsuario);
    }

    private void cadastraUsuario () throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String codUsuario;
        String nomUsuario;
        String nomCargo;
        String codCargo;
        String desSenha;
        String desEmail;
        
        codUsuario = request.getParameter("codUsuario");
        nomUsuario = request.getParameter("nomUsuario");
        desEmail = request.getParameter("desEmail");
        nomCargo = request.getParameter("nomCargo");
        
        desSenha = UtilidadesBD.stringParaSHA256(request.getParameter("desSenha"));
        
        CargoDAO cargo = CargoDAO.getInstance();
        Cargo [] cargosPesquisados = cargo.busca("nomCargo", nomCargo);
        codCargo = cargosPesquisados[0].getCodCargo();
        
        Usuario usuarioAdicionar = new Usuario(codUsuario, nomUsuario, codCargo, desSenha, desEmail);
        usuario.adiciona(usuarioAdicionar);
        
        return;
    }
    
    private void editaUsuario() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String codUsuario;
        String nomUsuario;
        String nomCargo;
        String codCargo;
        String desSenha;
        String desEmail;
        
        codUsuario = request.getParameter("codUsuarioSelecionado");
        nomUsuario = request.getParameter("nomUsuarioSelecionado");
        desEmail = request.getParameter("desEmailSelecionado");
        nomCargo = request.getParameter("nomCargoSelecionado");
        
        desSenha = UtilidadesBD.stringParaSHA256(request.getParameter("desSenhaSelecionado"));
        
        CargoDAO cargo = CargoDAO.getInstance();
        Cargo [] cargosPesquisados = cargo.busca("nomCargo", nomCargo);
        codCargo = cargosPesquisados[0].getCodCargo();
        
        Usuario usuarioAtualizado = new Usuario(codUsuario, nomUsuario, codCargo, desSenha, desEmail);
        
        usuario.atualiza(codUsuario, usuarioAtualizado);
        
        return;
    }
    

}
