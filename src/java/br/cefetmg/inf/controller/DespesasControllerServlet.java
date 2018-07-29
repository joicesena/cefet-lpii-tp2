/*
 * Controla a edição de despesa
 * Controla a exclusão de despesa
 * Redireciona para o lançamento de despesa
 */
package br.cefetmg.inf.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "DespesasControllerServlet", urlPatterns = {"/despesas"})
public class DespesasControllerServlet extends HttpServlet {
    
}
