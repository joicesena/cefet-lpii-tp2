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
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
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

        try {
            operacaoRegistro = Integer.parseInt(request.getParameter("operacaoRegistro"));
            nroQuarto = Integer.parseInt(request.getParameter("nroQuarto"));
            int seqHospedagem = UtilidadesBD.buscaUltimoRegistroRelacionadoAoQuarto(nroQuarto);
            
            request.setAttribute("nroQuarto", nroQuarto);
            request.setAttribute("seqHospedagem", seqHospedagem);
            
            QuartoConsumoDAOImpl dao = QuartoConsumoDAOImpl.getInstance();
            QuartoConsumo [] registrosBuscados = dao.busca(seqHospedagem, "seqHospedagem");
            
            JsonArrayBuilder builder = Json.createArrayBuilder();
            
            if (registrosBuscados.length > 0) {
                int i = 0;
                for (QuartoConsumo reg : registrosBuscados) {
                    if (reg.getNroQuarto() == nroQuarto) {
                        ServicoDAO servicoDAO = ServicoDAO.getInstance();
                        Servico [] servico = servicoDAO.busca("seqservico", reg.getSeqServico());
                        
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        
                        Date dataConsumo = new Date(reg.getDatConsumo().getTime());
                        
                        JsonObject consumo;
                        // monta o array json com as informações para construir a tabela
                        consumo = Json.createObjectBuilder()
                            .add("nroQuarto", reg.getNroQuarto())
                            .add("seqHospedagem", reg.getSeqHospedagem())
                            .add("seqServico", servico[0].getSeqServico())
                            .add("desServico", servico[0].getDesServico())
                            .add("datConsumo", sdf.format(dataConsumo))
                            .add("vlrUnit", servico[0].getVlrUnit())
                            .add("qtdConsumo", reg.getQtdConsumo())
                            .build();
                        builder.add(consumo);
                        i++;
                    }
                }
                
                JsonObject linhasQuartoConsumo = Json.createObjectBuilder().add("linhas", builder).build();

                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(linhasQuartoConsumo);
            } else {
                JsonObject retorno = null;
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            }

        } catch (SQLException ex) {
            JsonObject retorno = Json.createObjectBuilder()
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
