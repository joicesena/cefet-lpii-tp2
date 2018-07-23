package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Hospedagem;
import br.cefetmg.inf.model.dto.Hospede;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HospedagemDAOTest {

    private final HospedagemDAO hospedagemDAO = HospedagemDAO.getInstance();
    private final HospedeDAO hospedeDAO = HospedeDAO.getInstance();

    private final Connection con = new ConnectionFactory().getConnection();
    private final Calendar cal = Calendar.getInstance();

    private int i = 9;

    public HospedagemDAOTest() {
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
        i--;
    }

    @Test
    public void testAdiciona() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("-- Testa HospedagemDAO.adiciona() --");

        hospedeDAO.adiciona(new Hospede("           ".replace(" ", "" + i + ""), "Hospede" + i,
                "319        ".replace(" ", "" + i + ""), "pablo" + i + "@email.com"));

        Hospedagem novoHospedagem = new Hospedagem(
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * i)),
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * (i + 1))),
                100.00 * i,
                "           ".replace(" ", "" + i + ""));

        try {
            hospedagemDAO.adiciona(novoHospedagem);
        } catch (SQLException e) {
            fail("--!! O teste falhou !!--");
        }
        System.out.println("-->> Teste finalizado com sucesso <<--");
    }
    
    @Test
    public void testBusca() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("--Testa HospedagemDAO.busca()--");

        hospedeDAO.adiciona(new Hospede("           ".replace(" ", "" + i + ""), "Hospede" + i,
                "319        ".replace(" ", "" + i + ""), "pablo" + i + "@email.com"));

        Hospedagem expResult = new Hospedagem(
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * i)),
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * (i + 1))),
                100.00 * i,
                "           ".replace(" ", "" + i + ""));

        try {
            hospedagemDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }

        Hospedagem[] result = hospedagemDAO.busca("seqHospedagem", 1);
        
        if ((1 == result[0].getSeqHospedagem())
                && (expResult.getDatCheckIn().equals(result[0].getDatCheckIn()))
                && (expResult.getDatCheckOut().equals(result[0].getDatCheckOut()))
                && (Double.compare(expResult.getVlrPago(), result[0].getVlrPago()) == 0)
                && (expResult.getCodCPF().equals(result[0].getCodCPF()))) {
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } else {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testAtualiza() throws Exception {
        System.out.println("--Testa HospedagemDAO.atualiza()--");

        hospedeDAO.adiciona(new Hospede("           ".replace(" ", "" + i + ""), "Hospede" + i,
                "319        ".replace(" ", "" + i + ""), "pablo" + i + "@email.com"));

        Hospedagem expResult = new Hospedagem(
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * i)),
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * (i + 1))),
                100.00 * i,
                "           ".replace(" ", "" + i + ""));

        i--;

        hospedeDAO.adiciona(new Hospede("           ".replace(" ", "" + i + ""), "Hospede" + i,
                "319        ".replace(" ", "" + i + ""), "pablo" + i + "@email.com"));

        try {
            hospedagemDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            hospedagemDAO.atualiza(1,
                    new Hospedagem(
                            new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * i)),
                            new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * (i + 1))),
                            100.00 * i,
                            "           ".replace(" ", "" + i + "")));

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testDeleta() throws Exception {
        System.out.println("--Testa HospedagemDAO.deleta()--");

        hospedeDAO.adiciona(new Hospede("           ".replace(" ", "" + i + ""), "Hospede" + i,
                "319        ".replace(" ", "" + i + ""), "pablo" + i + "@email.com"));

        Hospedagem expResult = new Hospedagem(
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * i)),
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * (i + 1))),
                100.00 * i,
                "           ".replace(" ", "" + i + ""));

        try {
            hospedagemDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            hospedagemDAO.deleta(1);

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
}
