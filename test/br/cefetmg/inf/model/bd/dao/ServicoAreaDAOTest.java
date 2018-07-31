package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.ServicoArea;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ServicoAreaDAOTest {

    private final ServicoAreaDAO servicoAreaDAO = ServicoAreaDAO.getInstance();

    private final Connection con = new ConnectionFactory().getConnection();

    private int i = 1;

    public ServicoAreaDAOTest() {
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
        System.out.println("-- Testa ServicoAreaDAO.adiciona() --");

        ServicoArea novoServicoArea = new ServicoArea("00" + i, "ServiçoArea n°" + i);

        try {
            servicoAreaDAO.adiciona(novoServicoArea);
        } catch (SQLException e) {
            fail("--!! O teste falhou !!--");
        }
        System.out.println("-->> Teste finalizado com sucesso <<--");
    }

    @Test
    public void testBusca() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("--Testa ServicoAreaDAO.busca()--");

        ServicoArea expResult = new ServicoArea("00" + i, "ServiçoArea n°" + i);

        try {
            servicoAreaDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }

        ServicoArea[] result = servicoAreaDAO.busca("codServicoArea", "00" + i);

        if ((expResult.getCodServicoArea().equals(result[0].getCodServicoArea()))
                && (expResult.getNomServicoArea().equals(result[0].getNomServicoArea()))) {
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } else {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testAtualiza() throws Exception {
        System.out.println("--Testa ServicoAreaDAO.atualiza()--");

        ServicoArea expResult = new ServicoArea("00" + i, "ServiçoArea n°" + i);
        i++;

        try {
            servicoAreaDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            servicoAreaDAO.atualiza("00" + (i - 1),
                    new ServicoArea("00" + i, "ServiçoArea n°" + i));

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testDeleta() throws Exception {
        System.out.println("--Testa ServicoAreaDAO.deleta()--");

        ServicoArea expResult = new ServicoArea("00" + i, "ServiçoArea n°" + i);

        try {

            servicoAreaDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            servicoAreaDAO.deleta("00" + i);

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
}
