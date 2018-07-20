package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Quarto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void adiciona(Quarto quarto) throws SQLException {
        String qry = "INSERT INTO Quarto"
                + "(nroQuarto, codCategoria, idtOcupado)"
                + " VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, quarto.getNroQuarto());
        pStmt.setString(2, quarto.getCodCategoria());
        pStmt.setBoolean(3, quarto.isIdtOcupado());

        pStmt.execute();
    }

    @Override
    public Quarto[] busca(String coluna, String dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Quarto "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setString(1, dadoBusca);
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
    public void atualiza(String pK, Quarto quartoAtualizado) throws SQLException {
        String qry = "UPDATE Quarto "
                + "SET nroQuarto = ?, codCategoria = ?, idtOcupado = ? "
                + "WHERE nroQuarto = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setInt(1, quartoAtualizado.getNroQuarto());
        pStmt.setString(2, quartoAtualizado.getCodCategoria());
        pStmt.setBoolean(3, quartoAtualizado.isIdtOcupado());
        pStmt.setInt(4, Integer.parseInt(pK));

        pStmt.execute();
    }

    @Override
    public void deleta(String pK) throws SQLException {
        String qry = "DELETE FROM Quarto "
                + "WHERE nroQuarto = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setString(1, pK);

        pStmt.execute();
    }
}
