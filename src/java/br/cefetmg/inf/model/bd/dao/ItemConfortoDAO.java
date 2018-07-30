package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.ItemConforto;
import br.cefetmg.inf.model.dto.ItemConforto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ItemConfortoDAO extends BaseDAO<ItemConforto> {

    private static ItemConfortoDAO instancia;

    private ItemConfortoDAO() {
        super();
    }

    public static synchronized ItemConfortoDAO getInstance() {
        if (instancia == null) {
            instancia = new ItemConfortoDAO();
        }
        return instancia;
    }

    @Override
    public boolean adiciona(ItemConforto itemConforto) throws SQLException {
        String qry = "INSERT INTO ItemConforto"
                + "(codItem, desItem)"
                + " VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, itemConforto.getCodItem());
        pStmt.setString(2, itemConforto.getDesItem());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public ItemConforto[] busca(String coluna, Object dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM ItemConforto "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        
        if(dadoBusca instanceof String) 
            pStmt.setString(1, dadoBusca.toString());
        else 
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        
        ResultSet rs = pStmt.executeQuery();

        ItemConforto[] itemConfortoEncontrados = new ItemConforto[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        while (rs.next()) {
            itemConfortoEncontrados[i] = new ItemConforto(rs.getString(1), rs.getString(2));
            i++;
        }

        return itemConfortoEncontrados;
    }
    
    @Override
    public ItemConforto[] busca() throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        String qry = "SELECT * FROM ItemConforto";
        ResultSet rs = stmt.executeQuery(qry);

        ItemConforto[] itemConfortosEncontrados
                = new ItemConforto[UtilidadesBD.contaLinhasResultSet(rs)];

        int i = 0;
        rs.beforeFirst();
        while (rs.next()) {
            itemConfortosEncontrados[i] = new ItemConforto(rs.getString(1), rs.getString(2));
            i++;
        }

        return itemConfortosEncontrados;
    }

    @Override
    public boolean atualiza(Object pK, ItemConforto itemConfortoAtualizado) throws SQLException {
        String qry = "UPDATE ItemConforto "
                + "SET codItem = ?, desItem = ?"
                + "WHERE codItem = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, itemConfortoAtualizado.getCodItem());
        pStmt.setString(2, itemConfortoAtualizado.getDesItem());
        if(pK instanceof String) 
            pStmt.setString(3, pK.toString());
        else 
            pStmt.setInt(3, Integer.parseInt(pK.toString()));

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deleta(Object pK) throws SQLException {
        String qry = "DELETE FROM ItemConforto "
                + "WHERE codItem = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if(pK instanceof String) 
            pStmt.setString(1, pK.toString());
        else 
            pStmt.setInt(1, Integer.parseInt(pK.toString()));

        return pStmt.executeUpdate() > 0;
    }
}
