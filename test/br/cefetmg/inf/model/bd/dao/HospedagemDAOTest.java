package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.dto.Cargo;
import br.cefetmg.inf.model.dto.Hospedagem;
import br.cefetmg.inf.model.dto.Usuario;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private final CargoDAO cargoDAO = CargoDAO.getInstance();
    private final Connection con = new ConnectionFactory().getConnection();
    private final Calendar cal = Calendar.getInstance();

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
        Statement stmt = con.createStatement();
        stmt.execute("TRUNCATE TABLE Hospedagem CASCADE");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAdiciona() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("-- Testa HospedagemDAO.adiciona() --");

        cargoDAO.adiciona(new Cargo("006", "Cargo qualquer", false));
        usuarioDAO.adiciona(new Usuario("0001", "Josias", "006", "senha6", "josias@email.com"));

        Hospedagem novoHospedagem = new Hospedagem(
                new Timestamp(cal.getTimeInMillis()),
                new Timestamp(cal.getTimeInMillis()),
                100.00, "0001");

        try {
            hospedagemDAO.adiciona(novoHospedagem);
        } catch (SQLException e) {
            fail("--!! O teste falhou !!--");
        }
        System.out.println("-->> Teste finalizado com sucesso <<--");
    }

    @Test
    public void testBusca() throws Exception {
        System.out.println("--Testa HospedagemDAO.busca()--");

        cargoDAO.adiciona(new Cargo("007", "Cargo qualquer 2", false));
        usuarioDAO.adiciona(new Usuario("0002", "Jô", "007", "senha7", "jo@email.com"));

        Hospedagem expResult = new Hospedagem(
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 2)),
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 4)),
                200.00, "0002");

        try {
            hospedagemDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }

        Hospedagem[] result = hospedagemDAO.busca("seqHospedagem", 1);

        if ((expResult.getSeqHospedagem() == result[0].getSeqHospedagem())
                && (expResult.getDatCheckIn().equals(result[0].getDatCheckIn()))
                && (expResult.getDatCheckOut().equals(result[0].getDatCheckOut()))
                && (Double.compare(expResult.getVlrPago(), result[0].getVlrPago()) == 0)
                && (expResult.getCodCPF().equals(result[0].getVlrPago()))) {
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } else {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testAtualiza() throws Exception {
        System.out.println("--Testa HospedagemDAO.atualiza()--");

        cargoDAO.adiciona(new Cargo("008", "Cargo qualquer 3", false));
        usuarioDAO.adiciona(new Usuario("0003", "Joaozinho", "008", "senha8", "joaozinho@email.com"));

        Hospedagem expResult = new Hospedagem(
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 2)),
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 4)),
                300.00, "0003");
        
        cargoDAO.adiciona(new Cargo("011", "Cargo qualquer 4", false));
        usuarioDAO.adiciona(new Usuario("0004", "Joá", "011", "senha11", "joa@email.com"));

        try {
            hospedagemDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            hospedagemDAO.atualiza(1,
                    new Hospedagem(
                            new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 2)),
                            new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 4)),
                            400.00, "0004"));

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testDeleta() throws Exception {
        System.out.println("--Testa HospedagemDAO.deleta()--");
        
        cargoDAO.adiciona(new Cargo("009", "Cargo qualquer 4", false));
        usuarioDAO.adiciona(new Usuario("0005", "Joaozin", "009", "senha9", "joaozin@email.com"));

        Hospedagem expResult = new Hospedagem(
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 2)),
                new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 4)),
                500.00, "0005");

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
