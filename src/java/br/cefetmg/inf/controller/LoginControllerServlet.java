package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.login.LoginAutenticador;

import java.io.IOException;
import java.io.PrintWriter;
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
            
            HttpSession session = request.getSession();
            
            if (email == null || senha == null) {
                response.sendRedirect("http://localhost:8080/cefet-lpii-tp2/view/login.jsp");
            }

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

        if (loginVerificado) {
            session.setAttribute("email", email);
            session.setAttribute("codCargo", verificaLogin.getCargo());
            session.setAttribute("codUsuario", verificaLogin.getCodUsuario());

            // direciona para a página principal interna do sistema
            response.sendRedirect("http://localhost:8080/cefet-lpii-tp2/view/quartos-estados.jsp");
        } else {
            // retorna para a página de login
//            response.sendRedirect("http://localhost:8080/cefet-lpii-tp2/view/login.jsp");
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("location='http://localhost:8080/cefet-lpii-tp2/view/login.jsp';");
            out.println("alert('Email ou senha incorretos');");
            out.println("</script>");            
        }
    }
}