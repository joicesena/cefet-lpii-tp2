package br.cefetmg.inf.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DetalhesContaControllerServlet", urlPatterns = {"/detalhesConta"})
public class DetalhesContaControllerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int nroQuarto = Integer.parseInt((String)request.getParameter("nroQuarto"));
        System.out.println("nroQuarto - "+nroQuarto);
    }
}
