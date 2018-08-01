package br.cefetmg.inf.controller;

import br.cefetmg.inf.exception.*;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.cefetmg.inf.model.pojo.ServicoArea;
import br.cefetmg.inf.model.bd.dao.ServicoAreaDAO;
import br.cefetmg.inf.model.bd.dao.ServicoDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Servico;
import br.cefetmg.inf.model.pojo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AreaServicoControllerServlet", urlPatterns = {"/area-de-servico"})
public class AreaServicoControllerServlet extends HttpServlet {

    private HttpServletRequest requestInterno;
    private int operacaoRegistro;
    
    private ServicoAreaDAO servicoArea;

    private String codRegistroSelecionado;
    
    @Override
    public void init() throws ServletException {
        servicoArea = ServicoAreaDAO.getInstance();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoRegistro = 0;

        operacaoRegistro = Integer.parseInt(requestInterno.getParameter("operacaoItem"));
        JsonObject retorno;
        
        try {
            if (operacaoRegistro == 1) {
                codRegistroSelecionado = request.getParameter("codServicoArea");
                retorno = retornarDadosRegistro(codRegistroSelecionado);
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
    private JsonObject retornarDadosRegistro (String codServicoArea) throws SQLException {
        ServicoArea [] areasPesquisa = servicoArea.busca("codServicoArea", codServicoArea);
        ServicoArea areaRetorno = areasPesquisa[0];

        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("codServicoArea", areaRetorno.getCodServicoArea())
            .add("nomServicoArea", areaRetorno.getNomServicoArea())
            .build();

        return dadosRegistro;
    }
    

    private JsonObject inserirRegistro () throws SQLException, PKRepetidaException {
        String codArea;
        String desArea;
        
        codArea = requestInterno.getParameter("codServicoArea");
        desArea = requestInterno.getParameter("nomServicoArea");
        
        ServicoArea areaAdicionar = new ServicoArea(codArea, desArea);
        
        JsonObject dadosRegistro;
        
        //
        // TESTA SE JÁ EXISTE ALGUM REGISTRO COM AQUELA PK
        // LANÇA EXCEÇÃO
        ServicoArea [] registrosBuscados = servicoArea.busca("codServicoArea", codArea);
        if (registrosBuscados.length > 0)
            throw new PKRepetidaException("inserir");
        //
        //
        
        if (servicoArea.adiciona(areaAdicionar)) {
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
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaArea");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaArea");
        
        ServicoArea [] areasPesquisa = servicoArea.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaAreas", areasPesquisa);
        return;
    }

    private JsonObject editarRegistro() throws SQLException, PKRepetidaException, RegistroUtilizadoExternamenteException {
        String codArea, desArea;
        codArea = requestInterno.getParameter("codServicoArea");
        desArea = requestInterno.getParameter("nomServicoArea");
        
        ServicoArea servicoAreaAtualizado = new ServicoArea(codArea, desArea);
        
        JsonObject dadosRegistro;
        
        //
        // TESTA SE JÁ EXISTE ALGUM REGISTRO COM AQUELA PK
        // LANÇA EXCEÇÃO
        //
        if (!codArea.equals(codRegistroSelecionado)) {
            ServicoArea [] registrosBuscados = servicoArea.busca("codServicoArea", codArea);
            if (registrosBuscados.length > 0)
                throw new PKRepetidaException("alterar");
        }
        //
        // TESTA SE O CÓDIGO ATUAL É UTILIZADO EM SERVIÇO
        // LANÇA EXCEÇÃO
        //
        ServicoDAO dao = ServicoDAO.getInstance();
        if (!codArea.equals(codRegistroSelecionado)) {
            Servico [] registrosExternosBuscados = dao.busca("codServicoArea", codRegistroSelecionado);
            if (registrosExternosBuscados.length > 0)
                throw new RegistroUtilizadoExternamenteException("modificar", "serviço");
        }
        //
        //
        
        boolean testeRegistro = servicoArea.atualiza(codRegistroSelecionado, servicoAreaAtualizado);
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
            System.out.println("senha correta");

            //
            // TESTA SE O CÓDIGO ATUAL É UTILIZADO EM SERVIÇO
            // LANÇA EXCEÇÃO
            ServicoDAO dao = ServicoDAO.getInstance();
            Servico [] registrosExternosBuscados = dao.busca("codServicoArea", codRegistroSelecionado);
            if (registrosExternosBuscados.length > 0)
                throw new RegistroUtilizadoExternamenteException("excluir", "serviço");
            //
            //

            boolean testeExclusaoItem = servicoArea.deleta(codRegistroSelecionado);
            System.out.println("testeExclusaoItem - "+testeExclusaoItem);
            
            if (testeExclusaoItem) {
                System.out.println("vai excluir");
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
