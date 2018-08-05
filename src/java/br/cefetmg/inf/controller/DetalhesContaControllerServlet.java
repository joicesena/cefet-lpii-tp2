/*
 * Funciona como o check-in, quando clica pega o nroQuarto
 * Tem que pegar a seqHospedagem atual para poder exibir o conteúdo de QuartosConsumo
 * Dá um forward para outro servlet lidar com as despesas
*/

package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.ServicoDAO;
import br.cefetmg.inf.model.bd.dao.rel.impl.QuartoConsumoDAOImpl;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Servico;
import br.cefetmg.inf.model.pojo.rel.QuartoConsumo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "DetalhesContaControllerServlet", urlPatterns = {"/detalhes-da-conta"})
public class DetalhesContaControllerServlet extends HttpServlet {
    private int nroQuarto;
    
    private int operacaoRegistro;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JsonObject [] retorno = null;
        try {
            operacaoRegistro = Integer.parseInt((String)request.getParameter("operacaoRegistro"));
            nroQuarto = Integer.parseInt((String)request.getParameter("nroQuarto"));
            int seqHospedagem = UtilidadesBD.buscaUltimoRegistroRelacionadoAoQuarto(nroQuarto);
            
            request.setAttribute("nroQuarto", nroQuarto);
            request.setAttribute("seqHospedagem", seqHospedagem);
            
            QuartoConsumoDAOImpl dao = QuartoConsumoDAOImpl.getInstance();
            QuartoConsumo [] registrosBuscados = dao.busca(Integer.toString(seqHospedagem), "seqHospedagem");
            
            if (registrosBuscados.length > 1) {
                int i = 0;
                
                retorno = new JsonObject [i];
                i = 0;
                for (QuartoConsumo reg : registrosBuscados) {
                    if (reg.getNroQuarto() == nroQuarto) {
                        ServicoDAO servicoDAO = ServicoDAO.getInstance();
                        Servico [] servico = servicoDAO.busca("seqservico", reg.getSeqServico());
                        
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        
                        Date dataConsumo = new Date(reg.getDatConsumo().getTime());
                        
                        // monta o array json com as informações para construir a tabela
                        retorno[i] = Json.createObjectBuilder()
                            .add("seqServico", servico[0].getSeqServico())
                            .add("desServico", servico[0].getDesServico())
                            .add("datConsumo", sdf.format(dataConsumo))
                            .add("vlrUnit", servico[0].getVlrUnit())
                            .add("qtdConsumo", reg.getQtdConsumo())
                            .build();
                        i++;
                    }
                }
            } else {
                retorno = null;
            }
            
            // coloca o array json no request, para que ele possa ser acessado na página jsp
            request.setAttribute("quartoConsumo", retorno);
            
            String caminho;
            if (operacaoRegistro == 1)
               caminho = "http://localhost:8080/cefet-lpii-tp2/view/conta-detalhes.jsp";
            else
                caminho = "http://localhost:8080/cefet-lpii-tp2/view/checkout.jsp";
            
            RequestDispatcher rd = request.getRequestDispatcher(caminho);
            rd.forward(request, response);
        } catch (SQLException ex) {
            retorno[0] = Json.createObjectBuilder()
                .add("success", false)
                .add("mensagem", "Erro! Tente novamente")
                .build();
            response.setContentType("text/json");
            PrintWriter out = response.getWriter();
            out.print(retorno);
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
