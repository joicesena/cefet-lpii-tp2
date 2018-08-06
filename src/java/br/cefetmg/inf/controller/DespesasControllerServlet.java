/*
 * Controla a edição de despesa
 * Controla a exclusão de despesa
 * Controla o lançamento de despesa
 */
package br.cefetmg.inf.controller;

import br.cefetmg.inf.exception.PKRepetidaException;
import br.cefetmg.inf.exception.RegistroUtilizadoExternamenteException;
import br.cefetmg.inf.model.bd.dao.ServicoDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.dao.rel.impl.QuartoConsumoDAOImpl;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Servico;
import br.cefetmg.inf.model.pojo.Usuario;
import br.cefetmg.inf.model.pojo.rel.QuartoConsumo;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DespesasControllerServlet", urlPatterns = {"/despesas"})
public class DespesasControllerServlet extends HttpServlet {
    private int nroQuarto;
    private int seqHospedagem;
    
    private HttpServletRequest requestInterno;
    private int operacaoRegistro;
    
    private QuartoConsumoDAOImpl quartoConsumo;
    
    private QuartoConsumo registroAntigo;
    
    @Override
    public void init() throws ServletException {
        quartoConsumo = QuartoConsumoDAOImpl.getInstance();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoRegistro = 0;

        operacaoRegistro = Integer.parseInt(requestInterno.getParameter("operacaoItem"));
        JsonObject retorno;
        
        try {
            if (operacaoRegistro == 1) {
                nroQuarto = Integer.parseInt(request.getParameter("nroQuarto"));
                seqHospedagem = Integer.parseInt(request.getParameter("seqHospedagem"));
                retorno = retornarDadosRegistro();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoRegistro == 2) {
                retorno = inserirRegistro();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoRegistro == 3) {
                pesquisarRegistro();
            } else if (operacaoRegistro == 4) {
                retorno = editarRegistro();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoRegistro == 5) {
                retorno = removerRegistro();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            }
        } catch (SQLException | NoSuchAlgorithmException | UnsupportedEncodingException exc ) {
            retorno = Json.createObjectBuilder()
                .add("success", false)
                .add("mensagem", "Erro! Tente novamente")
                .build();
            response.setContentType("text/json");
            PrintWriter out = response.getWriter();
            out.print(retorno);
        } catch (PKRepetidaException | RegistroUtilizadoExternamenteException ex) {
            retorno = Json.createObjectBuilder()
                .add("success", true)
                .add("mensagem", ex.getMessage())
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
    

    //
    // MÉTODOS DE CONTROLE
    //
    private JsonObject retornarDadosRegistro () throws SQLException {
        int seqServico;
        String desServico;
        int qtdConsumo;
        Double vlrUnit;
        
        seqServico = Integer.parseInt(requestInterno.getParameter("seqServico"));
        ServicoDAO servicoDAO = ServicoDAO.getInstance();
        Servico [] servico = servicoDAO.busca("seqServico", seqServico);
        desServico = servico[0].getDesServico();
        vlrUnit = servico[0].getVlrUnit();
        qtdConsumo = Integer.parseInt(requestInterno.getParameter("qtdConsumo"));
        
        // formata a data de consumo do registro atual
        String data = requestInterno.getParameter("datConsumo");
        String [] dataSplit = data.split("/");
        int dia = Integer.parseInt(dataSplit[0]);
        int mes = Integer.parseInt(dataSplit[1]);
        int ano = Integer.parseInt(dataSplit[2]);
        Calendar calendario = Calendar.getInstance();
        calendario.set(ano, mes, dia);
        Timestamp datConsumo = new Timestamp(calendario.getTimeInMillis());
        // estabelece o valor do registro atual
        registroAntigo = new QuartoConsumo(seqHospedagem, nroQuarto, datConsumo, qtdConsumo, seqServico, desServico);
        
        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("seqServico", seqServico)
            .add("desServico", desServico)
            .add("qtdConsumo", qtdConsumo)
            .add("vlrUnit", vlrUnit)
            .build();

        return dadosRegistro;
    }
    
    private JsonObject inserirRegistro () throws SQLException, PKRepetidaException {
        Date dataAtual = new Date();
        Timestamp datConsumo = new Timestamp(dataAtual.getTime());
        
        int qtdConsumo = Integer.parseInt(requestInterno.getParameter("qtdConsumo"));
       
        int seqServico = Integer.parseInt(requestInterno.getParameter("seqServico"));
        
        HttpSession session = requestInterno.getSession();
        String codUsuarioRegistro = (String) session.getAttribute("codUsuario");
        
        JsonObject dadosRegistro;

        QuartoConsumo registroAdicionar = new QuartoConsumo(seqHospedagem, nroQuarto, datConsumo, qtdConsumo, seqServico, codUsuarioRegistro);
        boolean testeRegistro = quartoConsumo.adiciona(registroAdicionar);
        
        if (testeRegistro) {
            dadosRegistro = Json.createObjectBuilder()
                .add("success", true)
                .add("mensagem", "Registro adicionado com sucesso!")
                .build();
        } else {
            dadosRegistro = Json.createObjectBuilder()
                .add("success", false)
                .add("mensagem", "Ocorreu erro ao adicionar o registro. Repita a operação.")
                .build();
        }

        return dadosRegistro;
    }
    
    private void pesquisarRegistro() throws SQLException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaItem");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaItem");
        
        QuartoConsumo [] itensPesquisa = quartoConsumo.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaItens", itensPesquisa);
    }

    private JsonObject editarRegistro() throws SQLException, PKRepetidaException, RegistroUtilizadoExternamenteException {
        Date dataAtual = new Date();
        Timestamp datConsumo = new Timestamp(dataAtual.getTime());

        int seqServico = Integer.parseInt(requestInterno.getParameter("seqServico"));
        int qtdConsumo = Integer.parseInt(requestInterno.getParameter("qtdConsumo"));

        HttpSession session = requestInterno.getSession();
        String codUsuarioRegistro = (String) session.getAttribute("codUsuario");

        QuartoConsumo registroAtualizado = new QuartoConsumo(seqHospedagem, nroQuarto, datConsumo, qtdConsumo, seqServico, codUsuarioRegistro);

        JsonObject dadosRegistro;
        
        quartoConsumo.deleta(registroAntigo);
        boolean testeRegistro = quartoConsumo.adiciona(registroAtualizado);
        if (testeRegistro) {
            dadosRegistro = Json.createObjectBuilder()
                .add("sucesso", true)
                .add("mensagem", "Registro alterado com sucesso!")
                .build();
        } else {
            dadosRegistro = Json.createObjectBuilder()
                .add("sucesso", false)
                .add("mensagem", "Ocorreu erro ao alterar o registro. Repita a operação.")
                .build();
        }

        return dadosRegistro;
    }
    
    private JsonObject removerRegistro() throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException, RegistroUtilizadoExternamenteException {
        JsonObject dadosRegistro;

        HttpSession session = requestInterno.getSession();
        
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario[] usuarios = usuarioDAO.busca("codUsuario", session.getAttribute("codUsuario"));
        
        String senhaSHA256 = requestInterno.getParameter("senhaFuncionario");
        String senha = UtilidadesBD.stringParaSHA256(senhaSHA256);
        
        if ((usuarios[0].getDesSenha()).equals(senha)) {
            boolean testeExclusaoItem = quartoConsumo.deleta(registroAntigo);
            
            if (testeExclusaoItem) {
                dadosRegistro = Json.createObjectBuilder()
                    .add("sucesso", true)
                    .add("mensagem", "Registro excluído com sucesso!")
                    .build();
            } else {
                dadosRegistro = Json.createObjectBuilder()
                    .add("sucesso", false)
                    .add("mensagem", "Ocorreu erro ao excluir o registro. Repita a operação.")
                    .build();
            }
        } else {
            dadosRegistro = Json.createObjectBuilder()
                .add("sucesso", false)
                .add("mensagem", "Senha inválida")
                .build();
        }

        return dadosRegistro;
    }

}
