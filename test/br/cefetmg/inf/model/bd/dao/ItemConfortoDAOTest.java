package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.ItemConforto;
import br.cefetmg.inf.model.dto.Hospede;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ItemConfortoDAOTest {

    private final ItemConfortoDAO itemConfortoDAO = ItemConfortoDAO.getInstance();

    private final Connection con = new ConnectionFactory().getConnection();

    private int i = 1;

    public ItemConfortoDAOTest() {
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
        i++;
    }

    @Test
    public void testAdiciona() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("-- Testa ItemConfortoDAO.adiciona() --");

        ItemConforto novoItemConforto = new ItemConforto("00" + i, "Item n°" + i);

        try {
            itemConfortoDAO.adiciona(novoItemConforto);
        } catch (SQLException e) {
            fail("--!! O teste falhou !!--");
        }
        System.out.println("-->> Teste finalizado com sucesso <<--");
    }

    @Test
    public void testBusca() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("--Testa ItemConfortoDAO.busca()--");

        ItemConforto expResult = new ItemConforto("00" + i, "Item n°" + i);

        try {
            itemConfortoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }

        ItemConforto[] result = itemConfortoDAO.busca("codItem", "00" + i);

        if ((expResult.getCodItem().equals(result[0].getCodItem()))
                && (expResult.getDesItem().equals(result[0].getDesItem()))) {
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } else {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testAtualiza() throws Exception {
        System.out.println("--Testa ItemConfortoDAO.atualiza()--");

        ItemConforto expResult = new ItemConforto("00" + i, "Item n°" + i);
        i++;

        try {
            itemConfortoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            itemConfortoDAO.atualiza("00" + (i - 1),
                    new ItemConforto("00" + i, "Item n°" + i));

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testDeleta() throws Exception {
        System.out.println("--Testa ItemConfortoDAO.deleta()--");

        ItemConforto expResult = new ItemConforto("00" + i, "Item n°" + i);

        try {
            itemConfortoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            itemConfortoDAO.deleta("00" + i);

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
}
