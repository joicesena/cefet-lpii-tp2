package br.cefetmg.inf.model.bd.dao.rel.impl;

import br.cefetmg.inf.model.bd.dao.CargoDAO;
import br.cefetmg.inf.model.bd.dao.CategoriaQuartoDAO;
import br.cefetmg.inf.model.bd.dao.HospedagemDAO;
import br.cefetmg.inf.model.bd.dao.HospedeDAO;
import br.cefetmg.inf.model.bd.dao.ItemConfortoDAO;
import br.cefetmg.inf.model.bd.dao.QuartoDAO;
import br.cefetmg.inf.model.bd.dao.ServicoAreaDAO;
import br.cefetmg.inf.model.bd.dao.ServicoDAO;
import br.cefetmg.inf.model.bd.dao.UsuarioDAO;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Cargo;
import br.cefetmg.inf.model.pojo.CategoriaQuarto;
import br.cefetmg.inf.model.pojo.Hospedagem;
import br.cefetmg.inf.model.pojo.Hospede;
import br.cefetmg.inf.model.pojo.ItemConforto;
import br.cefetmg.inf.model.pojo.Quarto;
import br.cefetmg.inf.model.pojo.Servico;
import br.cefetmg.inf.model.pojo.ServicoArea;
import br.cefetmg.inf.model.pojo.Usuario;
import br.cefetmg.inf.model.pojo.rel.QuartoConsumo;
import br.cefetmg.inf.model.pojo.rel.QuartoHospedagem;
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

public class QuartoConsumoDAOImplTest {

    private static final QuartoConsumoDAOImpl QUARTO_CONSUMO_DAO_IMPL
            = QuartoConsumoDAOImpl.getInstance();

    private static final CategoriaQuartoDAO CATEGORIA_QUARTO_DAO
            = CategoriaQuartoDAO.getInstance();
    private static final QuartoDAO QUARTO_DAO
            = QuartoDAO.getInstance();

    private static final UsuarioDAO USUARIO_DAO = UsuarioDAO.getInstance();
    private static final CargoDAO CARGO_DAO = CargoDAO.getInstance();

    private static final ServicoAreaDAO SERVICO_AREA_DAO = ServicoAreaDAO.getInstance();
    private static final ServicoDAO SERVICO_DAO = ServicoDAO.getInstance();

    private static final HospedagemDAO HOSPEDAGEM_DAO = HospedagemDAO.getInstance();
    private static final HospedeDAO HOSPEDE_DAO = HospedeDAO.getInstance();

    private static final QuartoHospedagemDAOImpl QUARTO_HOSPEDAGEM_DAO_IMPL
            = QuartoHospedagemDAOImpl.getInstance();

    public QuartoConsumoDAOImplTest() {
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
        System.out.println("---- Testa QuartoConsumo.adiciona() ----");

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

        Cargo cargo1 = new Cargo("001", "Cargo 1", false);
        CARGO_DAO.adiciona(cargo1);
        Usuario usuario1 = new Usuario("0001",
                "Usuario1",
                "001",
                "senha1",
                "usuario1@email.com");
        USUARIO_DAO.adiciona(usuario1);

        ServicoArea servicoArea1 = new ServicoArea("001", "Serviço Área 1");
        SERVICO_AREA_DAO.adiciona(servicoArea1);
        Servico servico1 = new Servico("Serviço 1", 2.00, "001");
        SERVICO_DAO.adiciona(servico1);
        
        QUARTO_HOSPEDAGEM_DAO_IMPL.adiciona(new QuartoHospedagem(HOSPEDAGEM_DAO.busca()[0].getSeqHospedagem(),
                quarto1.getNroQuarto(), 
                2, 
                1, 
                10.00));

        try {
            QUARTO_CONSUMO_DAO_IMPL.adiciona(new QuartoConsumo(HOSPEDAGEM_DAO.busca()[0].getSeqHospedagem(),
                    quarto1.getNroQuarto(),
                    new Timestamp(System.currentTimeMillis()),
                    1,
                    SERVICO_DAO.busca()[0].getSeqServico(),
                    usuario1.getCodUsuario()));
            assert true;
        } catch (SQLException e) {
            e.printStackTrace();
            fail("--! O teste falhou !--");
        }
    }

    @Test
    public void testDeleta() throws Exception {
        System.out.println("---- Testa QuartoConsumo.deleta() ----");

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

        Cargo cargo1 = new Cargo("001", "Cargo 1", false);
        CARGO_DAO.adiciona(cargo1);
        Usuario usuario1 = new Usuario("0001",
                "Usuario1",
                "001",
                "senha1",
                "usuario1@email.com");
        USUARIO_DAO.adiciona(usuario1);

        ServicoArea servicoArea1 = new ServicoArea("001", "Serviço Área 1");
        SERVICO_AREA_DAO.adiciona(servicoArea1);
        Servico servico1 = new Servico("Serviço 1", 2.00, "001");
        SERVICO_DAO.adiciona(servico1);
        
        QUARTO_HOSPEDAGEM_DAO_IMPL.adiciona(new QuartoHospedagem(HOSPEDAGEM_DAO.busca()[0].getSeqHospedagem(),
                quarto1.getNroQuarto(), 
                2, 
                1, 
                10.00));

        Timestamp timestamp1 = new Timestamp(System.currentTimeMillis());

        QUARTO_CONSUMO_DAO_IMPL.adiciona(new QuartoConsumo(HOSPEDAGEM_DAO.busca()[0].getSeqHospedagem(),
                quarto1.getNroQuarto(),
                timestamp1,
                1,
                SERVICO_DAO.busca()[0].getSeqServico(),
                usuario1.getCodUsuario()));
        try {
            QUARTO_CONSUMO_DAO_IMPL.deleta(HOSPEDAGEM_DAO.busca()[0].getSeqHospedagem(),
                    quarto1.getNroQuarto(),
                    timestamp1);
            assert true;
        } catch (SQLException e) {
            e.printStackTrace();
            fail("--! O teste falhou !--");
        }
    }
}
