package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Hospede;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HospedeDAOTest {

    private final HospedeDAO hospedeDAO = HospedeDAO.getInstance();
    private final Connection con  = new ConnectionFactory().getConnection();

    public HospedeDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException {
        UtilidadesBD.apagarBDHosten();
        UtilidadesBD.constroiBDHosten();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAdiciona() {
        System.out.println("-- Testa HospedeDAO.adiciona() --");
        Hospede novoHospede = new Hospede("11111111111", "Jao", "31988888888", "jao@email.com");

        try {
            hospedeDAO.adiciona(novoHospede);
        } catch (SQLException e) {
            fail("--!! O teste falhou !!--");
        }
        System.out.println("-->> Teste finalizado com sucesso <<--");
    }

    @Test
    public void testBusca() throws Exception {
        System.out.println("--Testa HospedeDAO.busca()--");

        Hospede expResult = new Hospede("22222222222", "Joca", "31977777777", "joca@email.com");

        try {
            hospedeDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }

        Hospede[] result = hospedeDAO.busca("codCPF", "22222222222");

        if((expResult.getCodCPF().equals(result[0].getCodCPF()))
                && (expResult.getNomHospede().equals(result[0].getNomHospede()))
                && (expResult.getDesTelefone().equals(result[0].getDesTelefone()))
                && (expResult.getDesEmail().equals(result[0].getDesEmail())))
        {
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } else {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
    
    @Test
    public void testAtualiza() throws Exception {
        System.out.println("--Testa HospedeDAO.atualiza()--");

        Hospede expResult = new Hospede("33333333333", "Jonas", "31966666666", "jonas@email.com");

        try {
            hospedeDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            hospedeDAO.atualiza("33333333333", 
                    new Hospede("44444444444", "Jodelson", "31955555555", "jodelson@email.com"));
            
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
    
    @Test
    public void testDeleta() throws Exception {
        System.out.println("--Testa HospedeDAO.deleta()--");

        Hospede expResult = new Hospede("55555555555", "João", "31944444444", "joao@email.com");

        try {
            hospedeDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            hospedeDAO.deleta("55555555555");
            
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
}
