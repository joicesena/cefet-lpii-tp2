package br.cefetmg.inf.controller.login;

import br.cefetmg.inf.model.login.LoginAutenticador;

import java.io.IOException;
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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            email = request.getParameter(email);
            senha = request.getParameter(senha);

            LoginAutenticador verificaLogin = new LoginAutenticador(email, senha);

            if (verificaLogin.loginValido() ) {
                HttpSession session = request.getSession(); 
                session.setAttribute("email", email);
                session.setAttribute("codCargo", verificaLogin.retornaCargo());
                
                // direciona para a página principal interna do sistema
                response.sendRedirect("visualizacaoEstadoQuartos.jsp");
            } else {
                // retorna para a página de login
                response.sendRedirect("login.jsp");
            }
    }
}