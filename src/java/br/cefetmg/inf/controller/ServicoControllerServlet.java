package br.cefetmg.inf.controller;

import br.cefetmg.inf.exception.PKRepetidaException;
import br.cefetmg.inf.exception.RegistroUtilizadoExternamenteException;
import br.cefetmg.inf.model.bd.dao.ServicoAreaDAO;
import br.cefetmg.inf.model.bd.dao.ServicoDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.dao.rel.impl.QuartoConsumoDAOImpl;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Servico;
import br.cefetmg.inf.model.pojo.ServicoArea;
import br.cefetmg.inf.model.pojo.Usuario;
import br.cefetmg.inf.model.pojo.rel.QuartoConsumo;
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

@WebServlet(name = "ServicoControllerServlet", urlPatterns = {"/servico"})
public class ServicoControllerServlet extends HttpServlet {
    private HttpServletRequest requestInterno;
    private int operacaoRegistro;
    
    private String codRegistroSelecionado;
    
    private ServicoDAO servico;
    
    @Override
    public void init() throws ServletException {
        servico = ServicoDAO.getInstance();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoRegistro = 0;

        operacaoRegistro = Integer.parseInt(requestInterno.getParameter("operacaoItem"));
        JsonObject retorno;
        try {
            if (operacaoRegistro == 1) {
                codRegistroSelecionado = request.getParameter("seqServico");
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
    private JsonObject retornarDadosRegistro (String codRegistro) throws SQLException {
        Servico [] registros = servico.busca("seqServico", Integer.parseInt(codRegistro));

        Servico registroRetorno = registros[0];

        ServicoAreaDAO areaDAO = ServicoAreaDAO.getInstance();
        ServicoArea [] area = areaDAO.busca("codServicoArea", registroRetorno.getCodServicoArea());
        String nomServicoArea = area[0].getNomServicoArea();

        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("seqServico", registroRetorno.getSeqServico())
            .add("desServico", registroRetorno.getDesServico())
            .add("vlrUnit", registroRetorno.getVlrUnit())
            .add("nomServicoArea", nomServicoArea)
            .add("codServicoArea", registroRetorno.getCodServicoArea())
            .build();

        return dadosRegistro;
    }

    private JsonObject inserirRegistro () throws SQLException, PKRepetidaException {
        String desServico;
        Double vlrUnit;
        String codServicoArea;
//        String nomServicoArea;
        
        desServico = requestInterno.getParameter("desServico");
        vlrUnit = Double.parseDouble(requestInterno.getParameter("vlrUnit"));
        codServicoArea = requestInterno.getParameter("codServicoArea");
        
//        nomServicoArea = requestInterno.getParameter("nomServicoArea");
//        ServicoAreaDAO areaDAO = ServicoAreaDAO.getInstance();
//        ServicoArea[] areas = areaDAO.busca("nomServicoArea", nomServicoArea);
//        codServicoArea = areas[0].getCodServicoArea();
        
        JsonObject dadosRegistro;
        
        Servico servicoAdicionar = new Servico(desServico, vlrUnit, codServicoArea);
        boolean testeRegistro = servico.adiciona(servicoAdicionar);
        
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
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaServico");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaServico");
        
        Servico [] servicosPesquisa = servico.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaServicos", servicosPesquisa);
    }

    private JsonObject editarRegistro() throws SQLException, PKRepetidaException, RegistroUtilizadoExternamenteException {
        String desServico;
        Double vlrUnit;
        String codServicoArea;

        desServico = requestInterno.getParameter("desServico");
        vlrUnit = Double.parseDouble(requestInterno.getParameter("vlrUnit"));
        codServicoArea = requestInterno.getParameter("codServicoArea");

//        String nomServicoArea = requestInterno.getParameter("nomServicoArea");
//        ServicoAreaDAO areaDAO = ServicoAreaDAO.getInstance();
//        ServicoArea[] areas = areaDAO.busca("nomServicoArea", nomServicoArea);
//        codServicoArea = areas[0].getCodServicoArea();

        Servico registroAtualizado = new Servico(desServico, vlrUnit, codServicoArea);
        
        JsonObject dadosRegistro;
        
        boolean testeRegistro = servico.atualiza(codRegistroSelecionado, registroAtualizado);
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
        Servico [] registroBuscado = servico.busca("seqServico", Integer.parseInt(codRegistroSelecionado));

        HttpSession session = requestInterno.getSession();
        
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario[] usuarios = usuarioDAO.busca("codUsuario", session.getAttribute("codUsuario"));
        System.out.println("usuarios.length "+usuarios.length);
        
        String senhaSHA256 = requestInterno.getParameter("senhaFuncionario");
        String senha = UtilidadesBD.stringParaSHA256(senhaSHA256);
        
        JsonObject dadosRegistro;
        
        //
        // testa se o seqservico é utilizado em quartoconsumo
        // lança exceção
        QuartoConsumoDAOImpl dao = QuartoConsumoDAOImpl.getInstance();
        QuartoConsumo [] daoBusca = dao.busca(codRegistroSelecionado, "seqservico");
        if (daoBusca.length > 1) {
            throw new RegistroUtilizadoExternamenteException("excluir", "registro de consumo");
        }
        //
        //

        if ((usuarios[0].getDesSenha()).equals(senha)) {
            boolean testeExclusaoItem = servico.deleta(Integer.parseInt(codRegistroSelecionado));
            
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
