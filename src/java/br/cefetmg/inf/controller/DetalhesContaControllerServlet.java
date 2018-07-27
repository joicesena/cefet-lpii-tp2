/*
 * Funciona como o check-in, quando clica pega o nroQuarto pelo doget
 * Tem que pegar a seqHospedagem atual para poder exibir o conteúdo de QuartosConsumo
 * Dá um forward para outro servlet lidar com as despesas
*/

package br.cefetmg.inf.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DetalhesContaControllerServlet", urlPatterns = {"/detalhes-da-conta"})
public class DetalhesContaControllerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int nroQuarto = Integer.parseInt((String)request.getParameter("nroQuarto"));
        System.out.println("nroQuarto - "+nroQuarto);
    }
}
