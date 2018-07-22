package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Cargo;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CargoDAOTest {

    private final CargoDAO cargoDAO = CargoDAO.getInstance();
    private final Connection con = new ConnectionFactory().getConnection();

    public CargoDAOTest() {
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
        System.out.println("-- Testa CargoDAO.adiciona() --");
        Cargo novoCargo = new Cargo("001", "Gerente", true);

        try {
            cargoDAO.adiciona(novoCargo);
        } catch (SQLException e) {
            fail("--!! O teste falhou !!--");
        }
        System.out.println("-->> Teste finalizado com sucesso <<--");
    }

    @Test
    public void testBusca() throws Exception {
        System.out.println("--Testa CargoDAO.busca()--");

        Cargo expResult = new Cargo("002", "Chefe de cozinha", false);

        try {
            cargoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }

        Cargo[] result = cargoDAO.busca("codCargo", "002");

        if((expResult.getCodCargo().equals(result[0].getCodCargo()))
                && (expResult.getNomCargo().equals(result[0].getNomCargo()))
                && (expResult.isIdtMaster()== result[0].isIdtMaster()))
        {
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } else {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
    
    @Test
    public void testAtualiza() throws Exception {
        System.out.println("--Testa CargoDAO.atualiza()--");

        Cargo expResult = new Cargo("003", "Recepcionista", false);

        try {
            cargoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            cargoDAO.atualiza("003", 
                    new Cargo("004", "Lançador de despesas", false));
            
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
    
    @Test
    public void testDeleta() throws Exception {
        System.out.println("--Testa CargoDAO.deleta()--");

        Cargo expResult = new Cargo("005", "Cargo que vai ser apagado", false);

        try {
            cargoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            cargoDAO.deleta("005");
            
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
}
