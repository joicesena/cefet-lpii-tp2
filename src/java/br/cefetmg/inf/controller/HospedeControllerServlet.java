package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.HospedeDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Hospede;
import br.cefetmg.inf.model.pojo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "HospedeControllerServlet", urlPatterns = {"/hospede"})
public class HospedeControllerServlet extends HttpServlet {

    private HttpServletRequest requestInterno;
    private int operacaoItem;
    
    private String codItemSelecionado;
    
    private HospedeDAO hospede;
    
    @Override
    public void init() throws ServletException {
        hospede = HospedeDAO.getInstance();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoItem = 0;

        operacaoItem = Integer.parseInt(requestInterno.getParameter("operacaoItem"));
        JsonObject retorno;
        
//        String caminhoTela = "http://localhost:8080/cefet-lpii-tp2/view/itens-conforto.jsp";
        
        try {
            if (operacaoItem == 1) {
                codItemSelecionado = request.getParameter("codItem");
                retorno = retornarDadosRegistro(codItemSelecionado);
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoItem == 2) {
                retorno = inserirRegistro();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
//                response.sendRedirect(caminhoTela);
            } else if (operacaoItem == 3) {
                pesquisarRegistro();
            } else if (operacaoItem == 4) {
                retorno = editarRegistro();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoItem == 5) {
                retorno = removerRegistro();
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
    private JsonObject retornarDadosRegistro (String codRegistro) throws SQLException {
        Hospede [] registrosPesquisa = hospede.busca("codCPF", codRegistro);
        Hospede registroRetorno = registrosPesquisa[0];

        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("codCPF", registroRetorno.getCodCPF())
            .add("nomHospede", registroRetorno.getNomHospede())
            .add("desTelefone", registroRetorno.getDesTelefone())
            .add("desEmail", registroRetorno.getDesEmail())
            .build();

        return dadosRegistro;
    }

    private JsonObject inserirRegistro () throws SQLException {
        String codCPF;
        String nomHospede;
        String desTelefone;
        String desEmail;
        
        codCPF = requestInterno.getParameter("codCPF");
        nomHospede = requestInterno.getParameter("nomHospede");
        desTelefone = requestInterno.getParameter("desTelefone");
        desEmail = requestInterno.getParameter("desEmail");
        
        JsonObject dadosRegistro;

        Hospede hospedeAdicionar = new Hospede(codCPF, nomHospede, desTelefone, desEmail);

        boolean testeRegistro = hospede.adiciona(hospedeAdicionar);
        
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
        parametroPesquisa = (String)requestInterno.getParameter("pesquisarRegistro");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaHospede");
        
        Hospede [] hospedesPesquisa = hospede.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaHospedes", hospedesPesquisa);
        
        return;
    }

    private JsonObject editarRegistro() throws SQLException {
        String codCPF;
        String nomHospede;
        String desTelefone;
        String desEmail;
        
        codCPF = requestInterno.getParameter("codCPFSelecionado");
        nomHospede = requestInterno.getParameter("nomHospedeSelecionado");
        desTelefone = requestInterno.getParameter("desTelefoneSelecionado");
        desEmail = requestInterno.getParameter("desEmailSelecionado");
        
        Hospede hospedeAtualizado = new Hospede(codCPF, nomHospede, desTelefone, desEmail);
        
        JsonObject dadosRegistro;
        
        boolean testeRegistro = hospede.atualiza(codCPF, hospedeAtualizado);
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
    
    
    private JsonObject removerRegistro() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String codCPFHospede;
        codCPFHospede = requestInterno.getParameter("codCPF");
        
        JsonObject dadosRegistro;
        
        HttpSession session = requestInterno.getSession();
        
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario[] usuarios = usuarioDAO.busca("codUsuario", session.getAttribute("codUsuario"));
        System.out.println("usuarios.length "+usuarios.length);
        
        String senhaSHA256 = requestInterno.getParameter("senhaFuncionario");
        String senha = UtilidadesBD.stringParaSHA256(senhaSHA256);
        
        if ((usuarios[0].getDesSenha()).equals(senha)) {
            boolean testeExclusaoRegistro = hospede.deleta(codCPFHospede);
            
            if (testeExclusaoRegistro) {
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
