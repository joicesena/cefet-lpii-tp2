package br.cefetmg.inf.model.bd.dao.rel.impl;

import br.cefetmg.inf.model.bd.dao.*;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.*;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class QuartoHospedagemDAOImplTest {

    private static final QuartoHospedagemDAOImpl QUARTO_HOSPEDAGEM_DAO_IMPL
            = QuartoHospedagemDAOImpl.getInstance();

    private static final CategoriaQuartoDAO CATEGORIA_QUARTO_DAO
            = CategoriaQuartoDAO.getInstance();
    private static final QuartoDAO QUARTO_DAO
            = QuartoDAO.getInstance();

    private static final HospedagemDAO HOSPEDAGEM_DAO = HospedagemDAO.getInstance();
    private static final HospedeDAO HOSPEDE_DAO = HospedeDAO.getInstance();

    public QuartoHospedagemDAOImplTest() {
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

    /*
    @Test
    public void testGetInstance() {
    }
     */
    @Test
    public void testAdiciona() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("---- Testa QuartoHospedagem.adiciona() ----");

        CategoriaQuarto categoriaQuarto1 = new CategoriaQuarto(
                "001", "Categoria Quarto 1", 100.00);
        CATEGORIA_QUARTO_DAO.adiciona(categoriaQuarto1);
        Quarto quarto1 = new Quarto(1, "001", false);
        QUARTO_DAO.adiciona(quarto1);

        Hospede hospede1 = new Hospede("11111111111", "Hospede1",
                "32988888888", "hospede1@email.com");
        HOSPEDE_DAO.adiciona(hospede1);
        Hospedagem hospedagem1 = new Hospedagem(
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                100.00,
                "11111111111");
        HOSPEDAGEM_DAO.adiciona(hospedagem1);

        try {
            QUARTO_HOSPEDAGEM_DAO_IMPL.adiciona(HOSPEDAGEM_DAO.busca()[0].getSeqHospedagem(),
                    quarto1.getNroQuarto(),
                    hospedagem1.getDatCheckIn(),
                    hospedagem1.getDatCheckOut(),
                    2,
                    1,
                    10.00);
            assert true;
        } catch (SQLException e) {
            e.printStackTrace();
            fail("--! O teste falhou !--");
        }
    }

    @Test
    public void testDeleta() throws Exception {
        System.out.println("---- Testa QuartoHospedagem.deleta() ----");

        CategoriaQuarto categoriaQuarto1 = new CategoriaQuarto(
                "001", "Categoria Quarto 1", 100.00);
        CATEGORIA_QUARTO_DAO.adiciona(categoriaQuarto1);
        Quarto quarto1 = new Quarto(1, "001", false);
        QUARTO_DAO.adiciona(quarto1);

        Hospede hospede1 = new Hospede("11111111111", "Hospede1",
                "32988888888", "hospede1@email.com");
        HOSPEDE_DAO.adiciona(hospede1);
        Hospedagem hospedagem1 = new Hospedagem(
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                100.00,
                "11111111111");
        HOSPEDAGEM_DAO.adiciona(hospedagem1);

        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

        QUARTO_HOSPEDAGEM_DAO_IMPL.adiciona(HOSPEDAGEM_DAO.busca()[0].getSeqHospedagem(),
                    quarto1.getNroQuarto(),
                    hospedagem1.getDatCheckIn(),
                    hospedagem1.getDatCheckOut(),
                    2,
                    1,
                    10.00);
        try {
            QUARTO_HOSPEDAGEM_DAO_IMPL.deleta(HOSPEDAGEM_DAO.busca()[0].getSeqHospedagem(),
                    quarto1.getNroQuarto());
            assert true;
        } catch (SQLException e) {
            e.printStackTrace();
            fail("--! O teste falhou !--");
        }
    }
}
