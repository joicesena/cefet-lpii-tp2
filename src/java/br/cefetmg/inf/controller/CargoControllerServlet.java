package br.cefetmg.inf.controller;

import br.cefetmg.inf.model.bd.dao.CargoDAO;
import br.cefetmg.inf.model.bd.dao.ProgramaDAO;
import br.cefetmg.inf.model.pojo.Cargo;
import br.cefetmg.inf.model.bd.dao.rel.impl.CargoProgramaDAOImpl;
import br.cefetmg.inf.model.pojo.Programa;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CargoControllerServlet", urlPatterns = {"/cargo"})
public class CargoControllerServlet extends HttpServlet {

    private HttpServletRequest request;
    private String operacaoCargo;
    
    private CargoDAO cargo;
    
    @Override
    public void init() throws ServletException {
        cargo = CargoDAO.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        operacaoCargo = null;

        operacaoCargo = request.getParameter("operacaoCargo");
        
        try {
            if (operacaoCargo.equals("cadastrarCargo")) {
                cadastraCargo();
            } else if (operacaoCargo.equals("pesquisarCargo")) {
                pesquisaCargo();
            } else if (operacaoCargo.equals("editarCargo")) {
                editaCargo();
            } else if (operacaoCargo.equals("removerCargo")) {
                removeCargo();
            }
        } catch (SQLException exc) {
            //
            //
            //
        }
        
        String caminhoTelaCargo = "";
        request.getRequestDispatcher(caminhoTelaCargo).forward(request, response);
    }

    
    //
    // MÉTODOS DE CONTROLE
    //
    private void cadastraCargo () throws SQLException {
        String codCargo;
        String nomCargo;
        boolean idtMaster;
        String [] programas;
        String codPrograma;
        
        codCargo = request.getParameter("codCargo");
        nomCargo = request.getParameter("nomCargo");
        idtMaster = Boolean.getBoolean(request.getParameter("idtMaster"));

        programas = request.getParameterValues("programas");
        
        Cargo cargoAdicionar = new Cargo(codCargo, nomCargo, idtMaster);
        cargo.adiciona(cargoAdicionar);
        
        CargoProgramaDAOImpl relacaoCargoPrograma = CargoProgramaDAOImpl.getInstance();
        ProgramaDAO programaDAO = ProgramaDAO.getInstance();
        
        for (String prog : programas) {
            // busca o codigo dos programas que tem aquelas descrições
            Programa [] programa = programaDAO.busca("desPrograma", prog);
            
            codPrograma = programa[0].getCodPrograma();

            // adiciona o relacionamento
            relacaoCargoPrograma.adiciona(codCargo, codPrograma);
        }
        
        return;
    }
    
    private void pesquisaCargo() throws SQLException {
        String parametroPesquisa, tipoParametroPesquisa;
        parametroPesquisa = (String)request.getParameter("pesquisaCargo");
        tipoParametroPesquisa = (String)request.getParameter("parametroPesquisaCargo");
        
        Cargo [] cargosPesquisa = cargo.busca(tipoParametroPesquisa, parametroPesquisa);
        
        request.setAttribute("listaCargos", cargosPesquisa);
        
        return;
    }

    private void editaCargo() throws SQLException {
        String codCargo;
        String nomCargo;
        boolean idtMaster;
        String [] programas;
        String codPrograma;
        
        codCargo = request.getParameter("codCargoSelecionado");
        nomCargo = request.getParameter("nomCargoSelecionado");
        idtMaster = Boolean.getBoolean(request.getParameter("idtMasterSelecionado"));

        programas = request.getParameterValues("programasSelecionados");
        
        Cargo cargoAtualizado = new Cargo(codCargo, nomCargo, idtMaster);
        
        cargo.atualiza(codCargo, cargoAtualizado);
        
        CargoProgramaDAOImpl relacaoCargoPrograma = CargoProgramaDAOImpl.getInstance();
        ProgramaDAO programaDAO = ProgramaDAO.getInstance();
        

        relacaoCargoPrograma.deleta(codCargo, "codCargo");
        
        for (String prog : programas) {
            Programa [] programa = programaDAO.busca("desPrograma", prog);
            codPrograma = programa[0].getCodPrograma();
            relacaoCargoPrograma.adiciona(codCargo, codPrograma);
        }
        
        return;
    }
    
    private void removeCargo() throws SQLException {
        String codCargo;
        codCargo = request.getParameter("codCargoSelecionado");

        CargoProgramaDAOImpl relacaoCargoPrograma = CargoProgramaDAOImpl.getInstance();
        relacaoCargoPrograma.deleta(codCargo, "codCargo");
        cargo.deleta(codCargo);
        
        return;
    }
}
