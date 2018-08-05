package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.login.LoginAutenticador;

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
            
            LoginAutenticador verificaLogin = new LoginAutenticador();
            boolean loginVerificado = false;
            
            try {
                loginVerificado = verificaLogin.loginValido(email, senha);
            } catch (SQLException | NoSuchAlgorithmException ex) {
                JsonObject retorno = Json.createObjectBuilder()
                    .add("success", false)
                    .add("mensagem", "Erro! Tente novamente")
                    .build();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            }

        if (loginVerificado) {
            session.setAttribute("email", email);
            session.setAttribute("codCargo", verificaLogin.getCargo());
            session.setAttribute("codUsuario", verificaLogin.getCodUsuario());

            // direciona para a página principal interna do sistema
            response.sendRedirect("http://localhost:8080/cefet-lpii-tp2/view/quartos-estados.jsp");
        } else {
            // retorna para a página de login
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("location='http://localhost:8080/cefet-lpii-tp2/view/login.jsp';");
            out.println("alert('Email ou senha incorretos');");
            out.println("</script>");            
        }
    }
}