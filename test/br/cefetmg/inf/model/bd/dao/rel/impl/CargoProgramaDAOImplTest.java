package br.cefetmg.inf.model.bd.dao.rel.impl;

import br.cefetmg.inf.model.bd.dao.CargoDAO;
import br.cefetmg.inf.model.bd.dao.ProgramaDAO;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Cargo;
import br.cefetmg.inf.model.pojo.Programa;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CargoProgramaDAOImplTest {
    private static final CargoProgramaDAOImpl CARGO_PROGRAMA_DAO_IMPL = 
            CargoProgramaDAOImpl.getInstance();
            
    private static final ProgramaDAO PROGRAMA_DAO = ProgramaDAO.getInstance();
    private static final CargoDAO CARGO_DAO = CargoDAO.getInstance();
    
    public CargoProgramaDAOImplTest() {
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
        System.out.println("---- Testa CargoPrograma.adiciona() ----");
        
        Cargo cargo1 = new Cargo("001", "Cargo 1", false);
        CARGO_DAO.adiciona(cargo1);
        Programa programa1 = new Programa("001", "Programa 1");
        PROGRAMA_DAO.adiciona(programa1);
        
        String codPrograma = programa1.getCodPrograma();
        String codCargo = cargo1.getCodCargo();
        
        try{
            CARGO_PROGRAMA_DAO_IMPL.adiciona(codPrograma, codCargo);
            assert true;
        } catch(SQLException e) {
            fail("--! O teste falhou !--");
        }
    }

    @Test
    public void testDeleta() throws Exception {
        System.out.println("---- Testa CargoPrograma.deleta() ----");
        
        Cargo cargo1 = new Cargo("001", "Cargo 1", false);
        CARGO_DAO.adiciona(cargo1);
        Programa programa1 = new Programa("001", "Programa 1");
        PROGRAMA_DAO.adiciona(programa1);
        
        String codPrograma = programa1.getCodPrograma();
        String codCargo = cargo1.getCodCargo();
    
        CARGO_PROGRAMA_DAO_IMPL.adiciona(codPrograma, codCargo);    
        try{
            CARGO_PROGRAMA_DAO_IMPL.deleta(codPrograma, codCargo);
            assert true;
        } catch(SQLException e) {
            fail("--! O teste falhou !--");
        }
    }
}
