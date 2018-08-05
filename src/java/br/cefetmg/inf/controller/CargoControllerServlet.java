package br.cefetmg.inf.controller;

import br.cefetmg.inf.exception.PKRepetidaException;
import br.cefetmg.inf.exception.RegistroUtilizadoExternamenteException;
import br.cefetmg.inf.model.bd.dao.CargoDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.pojo.Cargo;
import br.cefetmg.inf.model.bd.dao.rel.impl.CargoProgramaDAOImpl;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Usuario;
import br.cefetmg.inf.model.pojo.rel.CargoPrograma;
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

@WebServlet(name = "CargoControllerServlet", urlPatterns = {"/cargo"})
public class CargoControllerServlet extends HttpServlet {

    private HttpServletRequest requestInterno;
    private int operacaoRegistro;
    
    private String codRegistroSelecionado;

    private CargoDAO cargo;
    
    @Override
    public void init() throws ServletException {
        cargo = CargoDAO.getInstance();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.requestInterno = request;
        operacaoRegistro = 0;

        operacaoRegistro = Integer.parseInt(requestInterno.getParameter("operacaoItem"));
        JsonObject retorno;
        
        try {
            if (operacaoRegistro == 1) {
                codRegistroSelecionado = request.getParameter("codCargo");
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
        Cargo [] registros = cargo.busca("codCargo", codRegistro);

        Cargo registroRetorno = registros[0];
        
        JsonObject dadosRegistro = Json.createObjectBuilder()
            .add("codCargo", registroRetorno.getCodCargo())
            .add("nomCargo", registroRetorno.getNomCargo())
            .add("idtMaster", registroRetorno.isIdtMaster())
            .build();

        return dadosRegistro;
    }

    private JsonObject inserirRegistro ()  throws SQLException, PKRepetidaException {
        String codCargo;
        String nomCargo;
        boolean idtMaster;
        String [] codProgramasSelecionados;
        
        codCargo = requestInterno.getParameter("codCargo");
        nomCargo = requestInterno.getParameter("nomCargo");
        idtMaster = false;

        codProgramasSelecionados = requestInterno.getParameterValues("codPrograma");
        
        Cargo cargoAdicionar = new Cargo(codCargo, nomCargo, idtMaster);

        JsonObject dadosRegistro;
        
        //
        // TESTA SE JÁ EXISTE ALGUM REGISTRO COM AQUELA PK
        // LANÇA EXCEÇÃO
        Cargo [] registrosBuscados = cargo.busca("codCargo", codCargo);
        if (registrosBuscados.length > 0)
            throw new PKRepetidaException("inserir");
        //
        //

        boolean testeRegistro = cargo.adiciona(cargoAdicionar);
        
        if (testeRegistro) {
        CargoProgramaDAOImpl relacaoCargoPrograma = CargoProgramaDAOImpl.getInstance();
            
            if (codProgramasSelecionados != null) {
                for (String item : codProgramasSelecionados) {
                    relacaoCargoPrograma.adiciona(item, codCargo);
                }
            }
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
        parametroPesquisa = (String)requestInterno.getParameter("pesquisaCargo");
        tipoParametroPesquisa = (String)requestInterno.getParameter("parametroPesquisaCargo");
        
        Cargo [] cargosPesquisa = cargo.busca(tipoParametroPesquisa, parametroPesquisa);
        
        requestInterno.setAttribute("listaCargos", cargosPesquisa);
    }

    private JsonObject editarRegistro() throws SQLException, PKRepetidaException, RegistroUtilizadoExternamenteException, NoSuchAlgorithmException, UnsupportedEncodingException {
        String codCargo;
        String nomCargo;
        boolean idtMaster;
        String [] codProgramasSelecionados;
        
        codCargo = requestInterno.getParameter("codCargo");
        nomCargo = requestInterno.getParameter("nomCargo");
        idtMaster = false;
        codProgramasSelecionados = requestInterno.getParameterValues("codPrograma");

        Cargo cargoAtualizado = new Cargo(codCargo, nomCargo, idtMaster);
        
        //
        // TESTA SE JÁ EXISTE ALGUM REGISTRO COM AQUELA PK
        // LANÇA EXCEÇÃO
        //
        if (!codCargo.equals(codRegistroSelecionado)) {
            Cargo [] registrosBuscados = cargo.busca("codCargo", codCargo);
            if (registrosBuscados.length > 0)
                throw new PKRepetidaException("alterar");
        }
        //
        // TESTA SE O CÓDIGO ATUAL É USADO EM USUARIO
        // LANÇA EXCEÇÃO
        //
        UsuarioDAO dao1 = UsuarioDAO.getInstance();
        if (!codCargo.equals(codRegistroSelecionado)) {
            Usuario [] registrosExternosBuscados = dao1.busca("codCargo", codRegistroSelecionado);
            if (registrosExternosBuscados.length > 0)
                throw new RegistroUtilizadoExternamenteException("modificar", "quarto");
        }
        //
        //

        CargoProgramaDAOImpl relacaoCargoPrograma = CargoProgramaDAOImpl.getInstance();

        relacaoCargoPrograma.deleta(codCargo, "codCargo");
        if (codProgramasSelecionados != null) {
            for (String item : codProgramasSelecionados) {
                relacaoCargoPrograma.adiciona(item, codCargo);
            }
        }
        
        JsonObject dadosRegistro;

        boolean testeRegistro = cargo.atualiza(codRegistroSelecionado, cargoAtualizado);
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
        HttpSession session = requestInterno.getSession();
        
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario[] usuarios = usuarioDAO.busca("codUsuario", session.getAttribute("codUsuario"));
        String senhaSHA256 = requestInterno.getParameter("senhaFuncionario");
        String senha = UtilidadesBD.stringParaSHA256(senhaSHA256);
        
        JsonObject dadosRegistro;

        //
        // TESTA SE O CÓDIGO ATUAL É USADO EM USUARIO
        // LANÇA EXCEÇÃO
        //
        UsuarioDAO dao1 = UsuarioDAO.getInstance();
        Usuario [] registrosExternosBuscados1 = dao1.busca("codCargo", codRegistroSelecionado);
        if (registrosExternosBuscados1.length > 0) {
            throw new RegistroUtilizadoExternamenteException("excluir", "registro de funcionário");
        }
        //
        //
        
        if ((usuarios[0].getDesSenha()).equals(senha)) {
            CargoProgramaDAOImpl relacaoCargoPrograma = CargoProgramaDAOImpl.getInstance();
            CargoPrograma [] registrosExternosBuscados = relacaoCargoPrograma.busca(codRegistroSelecionado, "codCargo");
            
            boolean testeExclusaoCatRel = true;
            if (registrosExternosBuscados.length > 0) {
                testeExclusaoCatRel = relacaoCargoPrograma.deleta(codRegistroSelecionado, "codCargo");
            }

            boolean testeExclusaoItem = cargo.deleta(codRegistroSelecionado);
            if (testeExclusaoItem && testeExclusaoCatRel) {
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
