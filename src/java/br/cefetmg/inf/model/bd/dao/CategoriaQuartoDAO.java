package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.CategoriaQuarto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CategoriaQuartoDAO extends BaseDAO<CategoriaQuarto> {

    private static CategoriaQuartoDAO instancia;

    private CategoriaQuartoDAO() {
        super();
    }

    public static synchronized CategoriaQuartoDAO getInstance() {
        if (instancia == null) {
            instancia = new CategoriaQuartoDAO();
        }
        return instancia;
    }

    @Override
    public void adiciona(CategoriaQuarto categoriaQuarto) throws SQLException {
        String qry = "INSERT INTO Categoria"
                + "(codCategoria, nomCategoria, vlrDiaria)"
                + " VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, categoriaQuarto.getCodCategoria());
        pStmt.setString(2, categoriaQuarto.getNomCategoria());
        pStmt.setDouble(3, categoriaQuarto.getVlrDiaria());

        pStmt.execute();
    }

    @Override
    public CategoriaQuarto[] busca(String coluna, Object dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Categoria "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }

        ResultSet rs = pStmt.executeQuery();

        CategoriaQuarto[] categoriaQuartosEncontrados = new CategoriaQuarto[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        while (rs.next()) {
            categoriaQuartosEncontrados[i]
                    = new CategoriaQuarto(rs.getString(1), rs.getString(2),
                            rs.getDouble(3));
            i++;
        }

        return categoriaQuartosEncontrados;
    }

    @Override
    public CategoriaQuarto[] busca() throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        String qry = "SELECT * FROM Categoria";
        ResultSet rs = stmt.executeQuery(qry);

        CategoriaQuarto[] categoriaQuartosEncontrados
                = new CategoriaQuarto[UtilidadesBD.contaLinhasResultSet(rs)];

        int i = 0;
        rs.beforeFirst();
        while (rs.next()) {
            categoriaQuartosEncontrados[i]
                    = new CategoriaQuarto(rs.getString(1), rs.getString(2),
                            rs.getDouble(3));
            i++;
        }

        return categoriaQuartosEncontrados;
    }

    @Override
    public void atualiza(Object pK, CategoriaQuarto categoriaQuartoAtualizado) throws SQLException {
        String qry = "UPDATE Categoria "
                + "SET codCategoria = ?, nomCategoria = ?, vlrDiaria = ? "
                + "WHERE codCategoria = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, categoriaQuartoAtualizado.getCodCategoria());
        pStmt.setString(2, categoriaQuartoAtualizado.getNomCategoria());
        pStmt.setDouble(3, categoriaQuartoAtualizado.getVlrDiaria());
        if (pK instanceof String) {
            pStmt.setString(4, pK.toString());
        } else {
            pStmt.setInt(4, Integer.parseInt(pK.toString()));
        }

        pStmt.execute();
    }

    @Override
    public void deleta(Object pK) throws SQLException {
        String qry = "DELETE FROM Categoria "
                + "WHERE codCategoria = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        pStmt.execute();
    }
}
