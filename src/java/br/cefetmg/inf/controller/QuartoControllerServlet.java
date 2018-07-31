package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.CategoriaQuartoDAO;
import br.cefetmg.inf.model.bd.dao.QuartoDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.CategoriaQuarto;
import br.cefetmg.inf.model.dto.Quarto;
import br.cefetmg.inf.model.dto.Usuario;
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

@WebServlet(name = "QuartoControllerServlet", urlPatterns = {"/quarto"})
public class QuartoControllerServlet extends HttpServlet {

    private HttpServletRequest requestInterno;
    private int operacaoItem;
    
    private String nroQuartoSelecionado;
    
    private QuartoDAO quarto;
    
    @Override
    public void init() throws ServletException {
        quarto = QuartoDAO.getInstance();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoItem = 0;

        operacaoItem = Integer.parseInt(requestInterno.getParameter("operacaoItem"));
        JsonObject retorno;
        
        try {
            if (operacaoItem == 1) {
                nroQuartoSelecionado = request.getParameter("nroQuarto");
                retorno = retornarDadosRegistro(nroQuartoSelecionado);
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoItem == 2) {
                retorno = inserirQuarto();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
//                response.sendRedirect(caminhoTela);
            } else if (operacaoItem == 3) {
                pesquisarQuarto();
            } else if (operacaoItem == 4) {
                retorno = editarQuarto();
                response.setContentType("text/json");
                PrintWriter out = response.getWriter();
                out.print(retorno);
            } else if (operacaoItem == 5) {
                retorno = removerQuarto();
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
    private JsonObject retornarDadosRegistro (String nroQuarto) throws SQLException {
        Quarto [] quartos = quarto.busca("nroQuarto", Integer.parseInt(nroQuarto));

        System.out.println("tamanho do vetor "+quartos.length);
        Quarto quartoRetorno = quartos[0];
        

        CategoriaQuartoDAO categoriaDAO = CategoriaQuartoDAO.getInstance();
        CategoriaQuarto[] categoria = categoriaDAO.busca("codCategoria", quartoRetorno.getCodCategoria());
        String nomCategoria = categoria[0].getNomCategoria();

        
        System.out.println("nroQuarto - "+nroQuarto);
        System.out.println("nomCategoria - "+nomCategoria);
        
        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("nroQuarto", quartoRetorno.getNroQuarto())
            .add("nomCategoria", nomCategoria)
            .build();

        return dadosRegistro;
    }

    private JsonObject inserirQuarto () throws SQLException {
        int nroQuarto;
        String nomCategoria;
        String codCategoria;
        
        nroQuarto = Integer.parseInt(requestInterno.getParameter("nroQuarto"));
        nomCategoria = requestInterno.getParameter("nomCategoria");
        
        CategoriaQuartoDAO categoriaDAO = CategoriaQuartoDAO.getInstance();
        CategoriaQuarto[] categoria = categoriaDAO.busca("nomCategoria", nomCategoria);
        codCategoria = categoria[0].getCodCategoria();
        
        Quarto quartoAdicionar = new Quarto(nroQuarto, codCategoria, false);

        JsonObject dadosRegistro;
        
        boolean testeRegistro = quarto.adiciona(quartoAdicionar);;
        
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
    
    private void pesquisarQuarto() throws SQLException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaQuarto");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaQuarto");
        
        Quarto [] quartosPesquisa = quarto.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaCategorias", quartosPesquisa);
        
        return;
    }

    private JsonObject editarQuarto() throws SQLException {
        int nroQuarto;
        String nomCategoria;
        String codCategoria;
        
        nroQuarto = Integer.parseInt(requestInterno.getParameter("nroQuarto"));
        nomCategoria = requestInterno.getParameter("nomCategoria");
        
        CategoriaQuartoDAO categoriaDAO = CategoriaQuartoDAO.getInstance();
        CategoriaQuarto[] categoria = categoriaDAO.busca("nomCategoria", nomCategoria);
        codCategoria = categoria[0].getCodCategoria();
        
        Quarto quartoAdicionar = new Quarto(nroQuarto, codCategoria, false);
        
        JsonObject dadosRegistro;

        boolean testeRegistro = quarto.atualiza(nroQuarto, quartoAdicionar);
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
    
    private JsonObject removerQuarto() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        int nroQuarto;
        nroQuarto = Integer.parseInt(requestInterno.getParameter("nroQuarto"));
        
        Quarto [] quartoBuscado = quarto.busca("nroQuarto", nroQuarto);

        HttpSession session = requestInterno.getSession();
        
        JsonObject dadosRegistro;

        if (!quartoBuscado[0].isIdtOcupado()) {
            // quarto livre
            UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
            Usuario[] usuarios = usuarioDAO.busca("codUsuario", session.getAttribute("codUsuario"));

            String senhaSHA256 = requestInterno.getParameter("senhaFuncionario");
            String senha = UtilidadesBD.stringParaSHA256(senhaSHA256);

            if ((usuarios[0].getDesSenha()).equals(senha)) {
                boolean testeExclusaoItem = quarto.deleta(nroQuarto);

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
            
        } else {
            // quarto ocupado
            dadosRegistro = Json.createObjectBuilder()
                .add("sucesso", false)
                .add("mensagem", "Não é possível excluir esse quarto. Está ocupado!")
                .build();
        }

        return dadosRegistro;
    }

}
