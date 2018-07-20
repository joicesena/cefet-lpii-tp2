package br.cefetmg.inf.model.bd.util;

import java.sql.ResultSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UtilidadesBDTest {
    
    public UtilidadesBDTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /*
    @Test
    public void testContaLinhasResultSet() throws Exception {
        System.out.println("-- Testa UtilidadesBD.contaLinhasResultSet() --");
        ResultSet rs = null;
        int expResult = 0;
        int result = UtilidadesBD.contaLinhasResultSet(rs);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    */

    @Test
    public void testStringParaSHA256() throws Exception {
        System.out.println("-- Testa UtilidadesBD.StringParaSHA256() --");
        String senha = "admin";
       
        if(UtilidadesBD.verificaSenha(senha, UtilidadesBD.stringParaSHA256(senha))){
            System.out.println(UtilidadesBD.stringParaSHA256(senha));
            System.out.println("-->> Teste finalizado com sucesso <<--");
            assert true;    
        }
        else fail("--!! O teste falhou !!--");
    }    
}