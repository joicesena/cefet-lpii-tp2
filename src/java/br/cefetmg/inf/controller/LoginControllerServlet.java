package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.login.LoginAutenticador;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LoginControllerServlet", urlPatterns = {"/login"})
public class LoginControllerServlet extends HttpServlet {
    private String email;
    private String senha;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
            email = request.getParameter("email");
            senha = request.getParameter("password");

            LoginAutenticador verificaLogin = new LoginAutenticador();
            boolean loginVerificado = false;
            
            try {
                loginVerificado = verificaLogin.loginValido(email, senha);
            } catch (SQLException ex) {
                //
                //
                //
            } catch (NoSuchAlgorithmException ex) {
            //
            //
            //
        }

            HttpSession session = request.getSession();

            if (loginVerificado) {
                session.setAttribute("email", email);
                session.setAttribute("codCargo", verificaLogin.getCargo());
                session.setAttribute("codUsuario", verificaLogin.getCodUsuario());
                
                // direciona para a página principal interna do sistema
                response.sendRedirect("http://localhost:8080/cefet-lpii-tp2/view/quartos-estados.jsp");
            } else {
                // retorna para a página de login
                session.setAttribute("mensagem-erro-login", "Informações inválidas!");
                response.sendRedirect("http://localhost:8080/cefet-lpii-tp2/view/login.jsp");
            }
    }
}