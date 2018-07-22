package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.CategoriaQuarto;
import br.cefetmg.inf.model.dto.Quarto;
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

public class QuartoDAOTest {

    private final CategoriaQuartoDAO categoriaDAO = CategoriaQuartoDAO.getInstance();
    private final QuartoDAO quartoDAO = QuartoDAO.getInstance();

    private final Connection con = new ConnectionFactory().getConnection();

    private int i = 1;

    public QuartoDAOTest() {
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
        System.out.println("-- Testa QuartoDAO.adiciona() --");

        categoriaDAO.adiciona(new CategoriaQuarto("00" + i, "Categoria n°" + i, 10.00 * i));
        Quarto novoQuarto = new Quarto(i, "00" + i, false);

        try {
            quartoDAO.adiciona(novoQuarto);
        } catch (SQLException e) {
            fail("--!! O teste falhou !!--");
        }
        System.out.println("-->> Teste finalizado com sucesso <<--");
    }

    @Test
    public void testBusca() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("--Testa QuartoDAO.busca()--");

        categoriaDAO.adiciona(new CategoriaQuarto("00" + i, "Categoria n°" + i, 10.00 * i));
        Quarto expResult = new Quarto(i, "00" + i, false);

        try {
            quartoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }

        Quarto[] result = quartoDAO.busca("nroQuarto", i);

        if ((expResult.getNroQuarto() == result[0].getNroQuarto())
                && (expResult.getCodCategoria().equals(result[0].getCodCategoria()))
                && (expResult.isIdtOcupado() == result[0].isIdtOcupado()))  {
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } else {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testAtualiza() throws Exception {
        System.out.println("--Testa QuartoDAO.atualiza()--");

        categoriaDAO.adiciona(new CategoriaQuarto("00" + i, "Categoria n°" + i, 10.00 * i));
        Quarto expResult = new Quarto(i, "00" + i, false);
        i++;
        
        categoriaDAO.adiciona(new CategoriaQuarto("00" + i, "Categoria n°" + i, 10.00 * i));

        try {
            quartoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            quartoDAO.atualiza(i - 1,
                    new Quarto(i, "00" + i, false));

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testDeleta() throws Exception {
        System.out.println("--Testa QuartoDAO.deleta()--");

        categoriaDAO.adiciona(new CategoriaQuarto("00" + i, "Categoria n°" + i, 10.00 * i));
        Quarto expResult = new Quarto(i, "00" + i, false);

        try {
            
            quartoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            quartoDAO.deleta(i);

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
}
