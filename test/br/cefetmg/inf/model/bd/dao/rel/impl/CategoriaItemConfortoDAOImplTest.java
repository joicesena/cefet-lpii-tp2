package br.cefetmg.inf.model.bd.dao.rel.impl;

import br.cefetmg.inf.model.bd.dao.CategoriaQuartoDAO;
import br.cefetmg.inf.model.bd.dao.ItemConfortoDAO;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.CategoriaQuarto;
import br.cefetmg.inf.model.pojo.ItemConforto;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CategoriaItemConfortoDAOImplTest {

    private static final CategoriaItemConfortoDAOImpl CATEGORIA_ITEM_CONFORTO_DAO_IMPL
            = CategoriaItemConfortoDAOImpl.getInstance();

    private static final CategoriaQuartoDAO CATEGORIA_QUARTO_DAO
            = CategoriaQuartoDAO.getInstance();
    private static final ItemConfortoDAO ITEM_CONFORTO_DAO
            = ItemConfortoDAO.getInstance();

    public CategoriaItemConfortoDAOImplTest() {
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
    public void testAdiciona() throws SQLException {
        System.out.println("---- Testa CategoriaItemConforto.adiciona() ----");

        CategoriaQuarto categoriaQuarto1 = new CategoriaQuarto("001", "Categoria Quarto 1", 100.00);
        CATEGORIA_QUARTO_DAO.adiciona(categoriaQuarto1);
        ItemConforto itemConforto1 = new ItemConforto("001", "Item Conforto 1");
        ITEM_CONFORTO_DAO.adiciona(itemConforto1);

        String codCategoria = categoriaQuarto1.getCodCategoria();
        String codItemConforto = itemConforto1.getCodItem();

        try {
            CATEGORIA_ITEM_CONFORTO_DAO_IMPL.adiciona(codCategoria, codItemConforto);
            assert true;
        } catch (SQLException e) {
            fail("--! O teste falhou !--");
        }
    }

    @Test
    public void testDeleta() throws Exception {
        System.out.println("---- Testa CategoriaItemConforto.deleta() ----");

        CategoriaQuarto categoriaQuarto1 = new CategoriaQuarto("001", "Categoria Quarto 1", 100.00);
        CATEGORIA_QUARTO_DAO.adiciona(categoriaQuarto1);
        ItemConforto itemConforto1 = new ItemConforto("001", "Item Conforto 1");
        ITEM_CONFORTO_DAO.adiciona(itemConforto1);

        String codCategoria = categoriaQuarto1.getCodCategoria();
        String codItemConforto = itemConforto1.getCodItem();

        CATEGORIA_ITEM_CONFORTO_DAO_IMPL.adiciona(codCategoria, codItemConforto);
        try {
            CATEGORIA_ITEM_CONFORTO_DAO_IMPL.deleta(codCategoria, codItemConforto);
            assert true;
        } catch (SQLException e) {
            e.printStackTrace();
            fail("--! O teste falhou !--");
        }
    }
}
