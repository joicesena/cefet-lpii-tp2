package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Cargo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CargoDAO extends BaseDAO<Cargo>{
    private static CargoDAO instancia;

    private CargoDAO() {
        super();
    }

    public static synchronized CargoDAO getInstance() {
        if (instancia == null) {
            instancia = new CargoDAO();
        }
        return instancia;
    }

    @Override
    public boolean adiciona(Cargo cargo) throws SQLException {
        String qry = "INSERT INTO Cargo"
                + "(codCargo, nomCargo, idtMaster)"
                + " VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, cargo.getCodCargo());
        pStmt.setString(2, cargo.getNomCargo());
        pStmt.setBoolean(3, cargo.isIdtMaster());

	return pStmt.executeUpdate() > 0;
    }

    @Override
    public Cargo[] busca(String coluna, Object dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Cargo "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        
        if(dadoBusca instanceof String) 
            pStmt.setString(1, dadoBusca.toString());
        else 
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        
        ResultSet rs = pStmt.executeQuery();

        Cargo[] cargosEncontrados = new Cargo[UtilidadesBD.contaLinhasResultSet(rs)];
        
        rs.beforeFirst();
        while (rs.next()) {
            cargosEncontrados[i] = new Cargo(rs.getString(1), rs.getString(2),
                    rs.getBoolean(3));
            i++;
        }

        return cargosEncontrados;
    }
    
    @Override
    public Cargo[] busca() throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        
        String qry = "SELECT * FROM Cargo";
        ResultSet rs = stmt.executeQuery(qry);
        
        Cargo[] cargosEncontrados = new Cargo[UtilidadesBD.contaLinhasResultSet(rs)];
        
        int i = 0;
        rs.beforeFirst();
        while (rs.next()) {
            cargosEncontrados[i] = new Cargo(rs.getString(1), rs.getString(2),
                    rs.getBoolean(3));
            i++;
        }

        return cargosEncontrados;
    }

    @Override
    public boolean atualiza(Object pK, Cargo cargoAtualizado) throws SQLException {
        String qry = "UPDATE Cargo "
                + "SET codCargo = ?, nomCargo = ?, idtMaster = ? "
                + "WHERE codCargo = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, cargoAtualizado.getCodCargo());
        pStmt.setString(2, cargoAtualizado.getNomCargo());
        pStmt.setBoolean(3, cargoAtualizado.isIdtMaster());
        if(pK instanceof String) 
            pStmt.setString(4, pK.toString());
        else 
            pStmt.setInt(4, Integer.parseInt(pK.toString()));

	return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deleta(Object pK) throws SQLException {
        String qry = "DELETE FROM Cargo "
                + "WHERE codCargo = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if(pK instanceof String) 
            pStmt.setString(1, pK.toString());
        else 
            pStmt.setInt(1, Integer.parseInt(pK.toString()));

	return pStmt.executeUpdate() > 0;
    }
}
