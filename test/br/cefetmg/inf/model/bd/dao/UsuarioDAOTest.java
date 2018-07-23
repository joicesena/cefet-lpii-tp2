package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Cargo;
import br.cefetmg.inf.model.dto.Usuario;
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

public class UsuarioDAOTest {

    private final CargoDAO cargoDAO = CargoDAO.getInstance();
    private final UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

    private final Connection con = new ConnectionFactory().getConnection();

    private int i = 1;

    public UsuarioDAOTest() {
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
        System.out.println("-- Testa UsuarioDAO.adiciona() --");

        cargoDAO.adiciona(new Cargo("00" + i, "Cargo n°" + i, false));
        Usuario expResult = new Usuario("000" + i, "Usuário n°" + i, "00" + i,
                "senha" + i, "email" + i + "@email.com");

        try {
            usuarioDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou !!--");
        }
        System.out.println("-->> Teste finalizado com sucesso <<--");
    }

    @Test
    public void testBusca() throws SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
        System.out.println("--Testa UsuarioDAO.busca()--");

        cargoDAO.adiciona(new Cargo("00" + i, "Cargo n°" + i, false));
        Usuario expResult = new Usuario("000" + i, "Usuário n°" + i, "00" + i,
                "senha" + i, "email" + i + "@email.com");

        try {
            usuarioDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }

        Usuario[] result = usuarioDAO.busca("codUsuario", "000" + i);
        
        if ((expResult.getCodUsuario().equals(result[0].getCodUsuario()))
                && (expResult.getNomUsuario().equals(result[0].getNomUsuario()))
                && (expResult.getCodCargo().equals(result[0].getCodCargo()))
                && (expResult.getDesSenha().equals(result[0].getDesSenha()))
                && (expResult.getDesEmail().equals(result[0].getDesEmail()))) {
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } else {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testAtualiza() throws Exception {
        System.out.println("--Testa UsuarioDAO.atualiza()--");

        cargoDAO.adiciona(new Cargo("00" + i, "Cargo n°" + i, false));
        Usuario expResult = new Usuario("000" + i, "Usuário n°" + i, "00" + i,
                "senha" + i, "email" + i + "@email.com");
        i++;

        cargoDAO.adiciona(new Cargo("00" + i, "Cargo n°" + i, false));

        try {
            usuarioDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            usuarioDAO.atualiza("000" + (i - 1),
                    new Usuario("000" + i, "Usuário n°" + i, "00" + i,
                            "senha" + i, "email" + i + "@email.com"));

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }

    @Test
    public void testDeleta() throws Exception {
        System.out.println("--Testa UsuarioDAO.deleta()--");

        cargoDAO.adiciona(new Cargo("00" + i, "Cargo n°" + i, false));
        Usuario expResult = new Usuario("000" + i, "Usuário n°" + i, "00" + i,
                "senha" + i, "email" + i + "@email.com");

        try {
            usuarioDAO.adiciona(expResult);
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 1 !!--");
        }
        try {
            usuarioDAO.deleta("000" + i);

            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;
        } catch (SQLException e) {
            fail("--!! O teste falhou -> 2 !!--");
        }
    }
}
