package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Programa;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProgramaDAOTest {

    private final ProgramaDAO programaDAO = ProgramaDAO.getInstance();
    private final Connection con  = new ConnectionFactory().getConnection();

    public ProgramaDAOTest() {
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
        System.out.println("-- Testa ProgramaDAO.adiciona() --");
        Programa novoPrograma = new Programa("001", "Tela qualquer");

        try {
            programaDAO.adiciona(novoPrograma);
        } catch (SQLException e) {
            fail("--!! O teste falhou !!--");
        }
        System.out.println("-->> Teste finalizado com sucesso <<--");
    }

    @Test
    public void testBusca() throws Exception {
        System.out.println("--Testa ProgramaDAO.busca()--");

        Programa expResult = new Programa("001", "Programa qualquer");

        try {
            programaDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }

        Programa[] result = programaDAO.busca("codPrograma", "001");

        if((expResult.getCodPrograma().equals(result[0].getCodPrograma()))
                && (expResult.getDesPrograma().equals(result[0].getDesPrograma())))
        {
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } else {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
    
    @Test
    public void testAtualiza() throws Exception {
        System.out.println("--Testa ProgramaDAO.atualiza()--");

        Programa expResult = new Programa("001", "Programa qualquer");

        try {
            programaDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            programaDAO.atualiza("001", 
                    new Programa("002", "Programa atualizado"));
            
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
    
    @Test
    public void testDeleta() throws Exception {
        System.out.println("--Testa ProgramaDAO.deleta()--");

        Programa expResult = new Programa("001", "Programa qualquer");

        try {
            programaDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            programaDAO.deleta("001");
            
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
}
