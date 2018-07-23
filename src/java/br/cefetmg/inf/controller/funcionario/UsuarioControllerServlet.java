package br.cefetmg.inf.controller.funcionario;

import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
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

@WebServlet(name = "UsuarioControllerServlet", urlPatterns = {"/funcionario"})
public class UsuarioControllerServlet extends HttpServlet {

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
                request.getRequestDispatcher("/view/cadastroUsuario.jsp");
                return;
            } else if (operacaoUsuario.equals("pesquisarUsuario")) {
                pesquisaUsuario();
            } else if (operacaoUsuario.equals("editarUsuario")) {
                request.getRequestDispatcher("/view/cadastroUsuario.jsp");
                return;
            } else if (operacaoUsuario.equals("removerUsuario")) {
                removeUsuario();
            }
        } catch (SQLException exc) {
            //
            //
            //
        }   catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UsuarioControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(UsuarioControllerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String caminhoTelaUsuario = "";
        request.getRequestDispatcher(caminhoTelaUsuario).forward(request, response);

    }

    
    
    //
    // MÉTODOS DE CONTROLE
    //
    private void pesquisaUsuario() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)request.getParameter("pesquisaUsuario");
        tipoParametroPesquisa = (String)request.getParameter("parametroPesquisaUsuario");
        
        Usuario [] usuariosPesquisa = usuario.busca(tipoParametroPesquisa, parametroPesquisa);
        
        request.setAttribute("listaUsuarios", usuariosPesquisa);
        
        return;
    }

    private void removeUsuario() throws SQLException {
        String codUsuario;
        codUsuario = request.getParameter("codUsuarioSelecionado");
        
        usuario.deleta(codUsuario);
        
        return;
    }
}
