package br.cefetmg.inf.model.bd.dao.rel.impl;

import br.cefetmg.inf.model.bd.dao.rel.CargoProgramaDAO;
import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CargoProgramaDAOImpl implements CargoProgramaDAO {
    private static CargoProgramaDAOImpl instancia;
    private static Connection con;

    private CargoProgramaDAOImpl() {
        con = new ConnectionFactory().getConnection();
    }
    
    public static synchronized CargoProgramaDAOImpl getInstance() {
        if(instancia == null)
            instancia = new CargoProgramaDAOImpl();
        return instancia;
    }
    
    @Override
    public void adiciona(String codPrograma, String codCargo) throws SQLException {
        String qry = "INSERT INTO "
                + "CargoPrograma(codPrograma, codCargo) "
                + "VALUES(?,?)";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, codPrograma);
        pStmt.setString(2, codCargo);
        pStmt.execute();
    }

    @Override
    public void deleta(String codPrograma, String codCargo) throws SQLException {
        String qry = "DELETE FROM CargoPrograma "
                + "WHERE codPrograma = ? AND "
                + "codCargo = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, codPrograma);
        pStmt.setString(2, codCargo);
        pStmt.execute();
    }
}
