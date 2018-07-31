package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Quarto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuartoDAO extends BaseDAO<Quarto> {

    private static QuartoDAO instancia;

    private QuartoDAO() {
        super();
    }

    public static synchronized QuartoDAO getInstance() {
        if (instancia == null) {
            instancia = new QuartoDAO();
        }
        return instancia;
    }

    @Override
    public boolean adiciona(Quarto quarto) throws SQLException {
        String qry = "INSERT INTO Quarto"
                + "(nroQuarto, codCategoria, idtOcupado)"
                + " VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, quarto.getNroQuarto());
        pStmt.setString(2, quarto.getCodCategoria());
        pStmt.setBoolean(3, quarto.isIdtOcupado());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public Quarto[] busca(String coluna, Object dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Quarto "
                + "WHERE " + coluna + " "
                + "= ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        if (dadoBusca instanceof String) {
            pStmt.setString(1, dadoBusca.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        }

        ResultSet rs = pStmt.executeQuery();

        Quarto[] quartoEncontrados = new Quarto[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        while (rs.next()) {
            quartoEncontrados[i] = new Quarto(rs.getInt(1), rs.getString(2),
                    rs.getBoolean(3));
            i++;
        }

        return quartoEncontrados;
    }

    @Override
    public Quarto[] busca() throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        String qry = "SELECT * FROM Quarto";
        ResultSet rs = stmt.executeQuery(qry);

        Quarto[] quartosEncontrados
                = new Quarto[UtilidadesBD.contaLinhasResultSet(rs)];

        int i = 0;
        rs.beforeFirst();
        while (rs.next()) {
            quartosEncontrados[i] = new Quarto(rs.getInt(1), rs.getString(2),
                    rs.getBoolean(3));
            i++;
        }

        return quartosEncontrados;
    }
    
    @Override
    public boolean atualiza(Object pK, Quarto quartoAtualizado) throws SQLException {
        String qry = "UPDATE Quarto "
                + "SET nroQuarto = ?, codCategoria = ?, idtOcupado = ? "
                + "WHERE nroQuarto = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, quartoAtualizado.getNroQuarto());
        pStmt.setString(2, quartoAtualizado.getCodCategoria());
        pStmt.setBoolean(3, quartoAtualizado.isIdtOcupado());
        if (pK instanceof String) {
            pStmt.setString(4, pK.toString());
        } else {
            pStmt.setInt(4, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deleta(Object pK) throws SQLException {
        String qry = "DELETE FROM Quarto "
                + "WHERE nroQuarto = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }
}
