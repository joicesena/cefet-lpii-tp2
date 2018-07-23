package br.cefetmg.inf.controller.hospede;

import br.cefetmg.inf.model.bd.dao.HospedeDAO;
import br.cefetmg.inf.model.dto.Hospede;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HospedeCadastroServlet", urlPatterns = {"/cadastroHospede"})
public class HospedeCadastroServlet extends HttpServlet {

    private HttpServletRequest request;
    private String operacaoHospede;
    
    private HospedeDAO hospede;
    
    @Override
    public void init() throws ServletException {
        hospede = HospedeDAO.getInstance();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.request = request;
        operacaoHospede = null;

        operacaoHospede = request.getParameter("operacaoHospede");
        
        try {
            if (operacaoHospede.equals("cadastrarHospede")) {
                cadastraHospede();
            } else if (operacaoHospede.equals("editarHospede")) {
                editaHospede();
            }
        } catch (SQLException exc) {
            //
            //
            //
        }
        
        //
        //
        String caminhoTelaHospede = "";
        response.sendRedirect(caminhoTelaHospede);
    }

    private void cadastraHospede () throws SQLException {
        String codCPF;
        String nomHospede;
        String desTelefone;
        String desEmail;
        
        codCPF = request.getParameter("codCPF");
        nomHospede = request.getParameter("nomHospede");
        desTelefone = request.getParameter("desTelefone");
        desEmail = request.getParameter("desEmail");
        
        Hospede hospedeAdicionar = new Hospede(codCPF, nomHospede, desTelefone, desEmail);
        hospede.adiciona(hospedeAdicionar);
        
        return;
    }
    
    private void editaHospede() throws SQLException {
        String codCPF;
        String nomHospede;
        String desTelefone;
        String desEmail;
        
        codCPF = request.getParameter("codCPFSelecionado");
        nomHospede = request.getParameter("nomHospedeSelecionado");
        desTelefone = request.getParameter("desTelefoneSelecionado");
        desEmail = request.getParameter("desEmailSelecionado");
        
        Hospede hospedeAtualizado = new Hospede(codCPF, nomHospede, desTelefone, desEmail);
        
        hospede.atualiza(codCPF, hospedeAtualizado);
        
        return;
    }
    

}
