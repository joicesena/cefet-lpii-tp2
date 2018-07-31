package br.cefetmg.inf.model.bd.dao.rel.impl;

import br.cefetmg.inf.model.bd.dao.rel.CargoProgramaDAO;
import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.rel.CargoPrograma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public boolean adiciona(String codPrograma, String codCargo) throws SQLException {
        String qry = "INSERT INTO "
                + "CargoPrograma(codPrograma, codCargo) "
                + "VALUES(?,?)";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, codPrograma);
        pStmt.setString(2, codCargo);
        return pStmt.executeUpdate() > 0;
    }

//    @Override
//    public boolean deleta(String codPrograma, String codCargo) throws SQLException {
//        String qry = "DELETE FROM CargoPrograma "
//                + "WHERE codPrograma = ? AND "
//                + "codCargo = ?";
//        PreparedStatement pStmt = con.prepareStatement(qry);
//        pStmt.setString(1, codPrograma);
//        pStmt.setString(2, codCargo);
//        return pStmt.executeUpdate() > 0;
//    }
    
    public CargoPrograma[] busca(String cod, String coluna) throws SQLException {
        String qry;
        if (coluna.equals("codCargo")) {
            qry = "SELECT * FROM CargoPrograma "
                    + "WHERE codCargo = ?";
        } else {
            qry = "SELECT * FROM CargoPrograma "
                    + "WHERE codPrograma = ?";
        }
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setString(1, cod);

        ResultSet rs = pStmt.executeQuery();

        CargoPrograma[] cargoProgramaEncontrados
                = new CargoPrograma[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        int i = 0;
        while (rs.next()) {
            cargoProgramaEncontrados[i]
                = new CargoPrograma(rs.getString(1), rs.getString(2));
            i++;
        }
        return cargoProgramaEncontrados;
    }

    @Override
    public boolean deleta(String codPrograma, String codCargo) throws SQLException {
        String qry = "DELETE FROM CargoPrograma "
                + "WHERE codPrograma = ? AND codCargo = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, codPrograma);
        pStmt.setString(2, codCargo);
        return pStmt.executeUpdate() > 0;
    }
}
