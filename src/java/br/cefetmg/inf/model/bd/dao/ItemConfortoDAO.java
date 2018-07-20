package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.ItemConforto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void adiciona(ItemConforto itemConforto) throws SQLException {
        String qry = "INSERT INTO ItemConforto"
                + "(codItem, desItem)"
                + " VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, itemConforto.getCodItem());
        pStmt.setString(2, itemConforto.getDesItem());

        pStmt.execute();
    }

    @Override
    public ItemConforto[] busca(String coluna, String dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM ItemConforto "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setString(1, dadoBusca);
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
    public void atualiza(String pK, ItemConforto itemConfortoAtualizado) throws SQLException {
        String qry = "UPDATE ItemConforto "
                + "SET codItem = ?, desItem = ?"
                + "WHERE codItem = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setString(1, itemConfortoAtualizado.getCodItem());
        pStmt.setString(2, itemConfortoAtualizado.getDesItem());
        pStmt.setString(4, pK);

        pStmt.execute();
    }

    @Override
    public void deleta(String pK) throws SQLException {
        String qry = "DELETE FROM ItemConforto "
                + "WHERE codItem = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setString(1, pK);

        pStmt.execute();
    }
}
