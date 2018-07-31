package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Servico;
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

public class ServicoDAOTest {

    private final ServicoAreaDAO servicoAreaDAO = ServicoAreaDAO.getInstance();
    private final ServicoDAO servicoDAO = ServicoDAO.getInstance();

    private final Connection con = new ConnectionFactory().getConnection();

    private int i = 1;

    public ServicoDAOTest() {
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
        System.out.println("-- Testa ServicoDAO.adiciona() --");

        servicoAreaDAO.adiciona(new ServicoArea("00" + i, "ServiçoArea n°" + i));
        Servico expResult = new Servico("Serviço n°" + i, 1.00 * i, "00" + i);

        try {
            servicoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou !!--");
        }
        System.out.println("-->> Teste finalizado com sucesso <<--");
    }

    @Test
    public void testBusca() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("--Testa ServicoDAO.busca()--");

        servicoAreaDAO.adiciona(new ServicoArea("00" + i, "ServiçoArea n°" + i));
        Servico expResult = new Servico("Serviço n°" + i, 1.00 * i, "00" + i);

        try {
            servicoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }

        Servico[] result = servicoDAO.busca("seqServico", 1);

        if ((1 == result[0].getSeqServico())
                && (expResult.getDesServico().equals(result[0].getDesServico()))
                && (Double.compare(expResult.getVlrUnit(), result[0].getVlrUnit()) == 0)
                && (expResult.getCodServicoArea().equals(result[0].getCodServicoArea()))) {
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } else {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testAtualiza() throws Exception {
        System.out.println("--Testa ServicoDAO.atualiza()--");

        servicoAreaDAO.adiciona(new ServicoArea("00" + i, "ServiçoArea n°" + i));
        Servico expResult = new Servico("Serviço n°" + i, 1.00 * i, "00" + i);
        i++;

        servicoAreaDAO.adiciona(new ServicoArea("00" + i, "ServiçoArea n°" + i));

        try {
            servicoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            servicoDAO.atualiza(1,
                    new Servico("Serviço n°" + i, 1.00 * i, "00" + i));

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
    
    @Test
    public void testAtualiza2() throws Exception {
        System.out.println("--Testa ServicoDAO.atualiza2()--");

        servicoAreaDAO.adiciona(new ServicoArea("00" + i, "ServiçoArea n°" + i));
        Servico expResult = new Servico("Serviço n°" + i, 1.00 * i, "00" + i);
        i++;

        servicoAreaDAO.adiciona(new ServicoArea("00" + i, "ServiçoArea n°" + i));

        try {
            servicoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            servicoDAO.atualiza(expResult,
                    new Servico("Serviço n°" + i, 1.00 * i, "00" + i));

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            e.printStackTrace();
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
    
    @Test
    public void testAtualiza3() throws Exception {
        System.out.println("--Testa ServicoDAO.atualiza3()--");

        servicoAreaDAO.adiciona(new ServicoArea("00" + i, "ServiçoArea n°" + i));
        Servico expResult = new Servico("Serviço n°" + i, 1.00 * i, "00" + i);
        i++;

        servicoAreaDAO.adiciona(new ServicoArea("00" + i, "ServiçoArea n°" + i));

        try {
            servicoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            servicoDAO.atualiza(expResult.getDesServico(), expResult.getCodServicoArea(),
                    new Servico("Serviço n°" + i, 1.00 * i, "00" + i));

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            e.printStackTrace();
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testDeleta() throws Exception {
        System.out.println("--Testa ServicoDAO.deleta()--");

        servicoAreaDAO.adiciona(new ServicoArea("00" + i, "ServiçoArea n°" + i));
        Servico expResult = new Servico("Serviço n°" + i, 1.00 * i, "00" + i);

        try {
            servicoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            servicoDAO.deleta(1);

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
    
    @Test
    public void testDeleta2() throws Exception {
        System.out.println("--Testa ServicoDAO.deleta2()--");

        servicoAreaDAO.adiciona(new ServicoArea("00" + i, "ServiçoArea n°" + i));
        Servico expResult = new Servico("Serviço n°" + i, 1.00 * i, "00" + i);

        try {
            servicoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            servicoDAO.deleta(expResult);

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            e.printStackTrace();
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
    
    @Test
    public void testDeleta3() throws Exception {
        System.out.println("--Testa ServicoDAO.deleta3()--");

        servicoAreaDAO.adiciona(new ServicoArea("00" + i, "ServiçoArea n°" + i));
        Servico expResult = new Servico("Serviço n°" + i, 1.00 * i, "00" + i);

        try {
            servicoDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            servicoDAO.deleta(expResult.getDesServico(), expResult.getCodServicoArea());

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            e.printStackTrace();
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
}
