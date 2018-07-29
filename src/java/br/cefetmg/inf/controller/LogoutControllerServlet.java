package br.cefetmg.inf.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LogoutControllerServlet", urlPatterns = {"/logout"})
public class LogoutControllerServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session != null)
            session.invalidate();
        response.sendRedirect("http://localhost:8080/cefet-lpii-tp2/view/login.jsp");
    }
}
