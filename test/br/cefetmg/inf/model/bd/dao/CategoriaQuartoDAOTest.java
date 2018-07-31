package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.CategoriaQuarto;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CategoriaQuartoDAOTest {

    private final CategoriaQuartoDAO categoriaQuartoDAO = CategoriaQuartoDAO.getInstance();
    private final Connection con = new ConnectionFactory().getConnection();

    public CategoriaQuartoDAOTest() {
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
        System.out.println("-- Testa CategoriaQuartoDAO.adiciona() --");
        CategoriaQuarto novoCategoriaQuarto = new CategoriaQuarto("001", "Padrão", 10.00);

        try {
            categoriaQuartoDAO.adiciona(novoCategoriaQuarto);
        } catch (SQLException e) {
            fail("--!! O teste falhou !!--");
        }
        System.out.println("-->> Teste finalizado com sucesso <<--");
    }

    @Test
    public void testBusca() throws Exception {
        System.out.println("--Testa CategoriaQuartoDAO.busca()--");

        CategoriaQuarto expResult = new CategoriaQuarto("002", "Bom", 20.0);

        try {
            categoriaQuartoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }

        CategoriaQuarto[] result = categoriaQuartoDAO.busca("codCategoria", "002");

        if ((expResult.getCodCategoria().equals(result[0].getCodCategoria()))
                && (expResult.getNomCategoria().equals(result[0].getNomCategoria()))
                && (Double.compare(expResult.getVlrDiaria(), result[0].getVlrDiaria()) == 0)) {
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } else {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testAtualiza() throws Exception {
        System.out.println("--Testa CategoriaQuartoDAO.atualiza()--");

        CategoriaQuarto expResult = new CategoriaQuarto("003", "Top", 30.00);

        try {
            categoriaQuartoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            categoriaQuartoDAO.atualiza("003",
                    new CategoriaQuarto("004", "TopTop", 40.00));

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testDeleta() throws Exception {
        System.out.println("--Testa CategoriaQuartoDAO.deleta()--");

        CategoriaQuarto expResult = new CategoriaQuarto("005", "HOSP TOP", 999.99);

        try {
            categoriaQuartoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            categoriaQuartoDAO.deleta("005");

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
}
