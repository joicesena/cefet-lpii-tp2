package br.cefetmg.inf.controller;

import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.cefetmg.inf.model.dto.ServicoArea;
import br.cefetmg.inf.model.bd.dao.ServicoAreaDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

@WebServlet(name = "AreaServicoControllerServlet", urlPatterns = {"/area-de-servico"})
public class AreaServicoControllerServlet extends HttpServlet {

    private HttpServletRequest requestInterno;
    private int operacaoArea;
    
    private ServicoAreaDAO servicoArea;

    private String codServicoAreaSelecionado;
    
    @Override
    public void init() throws ServletException {
        servicoArea = ServicoAreaDAO.getInstance();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoArea = 0;

        operacaoArea = Integer.parseInt(requestInterno.getParameter("operacaoArea"));
        JsonObject retorno;
        
        try {
            if (operacaoArea == 1) {
                codServicoAreaSelecionado = request.getParameter("codServicoArea");
                retorno = retornarDadosRegistro(codServicoAreaSelecionado);
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoArea == 2) {
                retorno = inserirArea();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
//                response.sendRedirect(caminhoTela);
            } else if (operacaoArea == 3) {
                pesquisarArea();
            } else if (operacaoArea == 4) {
                retorno = editarArea();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoArea == 5) {
                retorno = removerArea();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
//                response.sendRedirect(caminhoTela);
            }
        } catch (SQLException exc) {
            //
            //
            //
        } catch (NoSuchAlgorithmException ex) {
            //
            //
            //
        } catch (UnsupportedEncodingException ex) {
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
    

    
    //
    // MÉTODOS DE CONTROLE
    //
    private JsonObject retornarDadosRegistro (String codServicoArea) throws SQLException {
//        System.out.println("codItem - "+codItem);
        ServicoArea [] areasPesquisa = servicoArea.busca("codServicoArea", codServicoArea);
        ServicoArea areaRetorno = areasPesquisa[0];

        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("codServicoArea", areaRetorno.getCodServicoArea())
            .add("nomServicoArea", areaRetorno.getNomServicoArea())
            .build();

        return dadosRegistro;
    }
    

    private JsonObject inserirArea () throws SQLException {
        String codArea;
        String desArea;
        
        codArea = requestInterno.getParameter("codServicoArea");
        desArea = requestInterno.getParameter("nomServicoArea");
        
        ServicoArea areaAdicionar = new ServicoArea(codArea, desArea);
        boolean testeRegistro = servicoArea.adiciona(areaAdicionar);
        
        JsonObject dadosRegistro;
        
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
    
    private void pesquisarArea() throws SQLException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaArea");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaArea");
        
        ServicoArea [] areasPesquisa = servicoArea.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaAreas", areasPesquisa);
        return;
    }

    private JsonObject editarArea() throws SQLException {
        String codArea, desArea;
        codArea = requestInterno.getParameter("codAreaSelecionado");
        desArea = requestInterno.getParameter("nomAreaSelecionado");
        
        ServicoArea servicoAreaAtualizado = new ServicoArea(codArea, desArea);
        
        JsonObject dadosRegistro;
        
        boolean testeRegistro = servicoArea.atualiza(codArea, servicoAreaAtualizado);
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
    
    private JsonObject removerArea() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String codArea;
        codArea = requestInterno.getParameter("codAreaSelecionado");
        
        JsonObject dadosRegistro;
        
        HttpSession session = requestInterno.getSession();
        
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario[] usuarios = usuarioDAO.busca("codUsuario", session.getAttribute("codUsuario"));
        System.out.println("usuarios.length "+usuarios.length);
        
        String senhaSHA256 = requestInterno.getParameter("senhaFuncionario");
        String senha = UtilidadesBD.stringParaSHA256(senhaSHA256);
        
        if ((usuarios[0].getDesSenha()).equals(senha)) {
            boolean testeExclusaoItem = servicoArea.deleta(codArea);
            
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
