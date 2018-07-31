package br.cefetmg.inf.controller.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.json.JsonArray;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ListaRegistrosServlet", urlPatterns = {"/lista-registros"})
public class ListaRegistrosServlet extends HttpServlet {

    private String tipoLista;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ListaRegistrosComboBox jsonArrayRetorno = new ListaRegistrosComboBox();
        JsonArray retornoArray;
        
        tipoLista = request.getParameter("tipoLista");
        
        try {
            if (tipoLista.equals("programas")) {
                retornoArray = jsonArrayRetorno.listarProgramasComboBox();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retornoArray);
            } else if (tipoLista.equals("cargos")) {
                retornoArray = jsonArrayRetorno.listarCargosComboBox();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retornoArray);
            } else if (tipoLista.equals("categorias")) {
                retornoArray = jsonArrayRetorno.listarCategoriasQuartoComboBox();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retornoArray);
            } else if (tipoLista.equals("itensConforto")) {
                retornoArray = jsonArrayRetorno.listarItensConfortoComboBox();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retornoArray);
            } else if (tipoLista.equals("areasServico")) {
                retornoArray = jsonArrayRetorno.listarServicoAreaComboBox();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retornoArray);
            } 
        } catch (SQLException ex) {
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
}
