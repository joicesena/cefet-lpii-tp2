package br.cefetmg.inf.model.bd.dao.rel.impl;

import br.cefetmg.inf.model.bd.dao.rel.QuartoConsumoDAO;
import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.rel.QuartoConsumo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class QuartoConsumoDAOImpl implements QuartoConsumoDAO {

    private static QuartoConsumoDAOImpl instancia;
    private static Connection con;

    private QuartoConsumoDAOImpl() {
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized QuartoConsumoDAOImpl getInstance() {
        if (instancia == null) {
            instancia = new QuartoConsumoDAOImpl();
        }
        return instancia;
    }

    @Override
    public boolean adiciona(int seqHospedagem, int nroQuarto, Timestamp datConsumo,
            int qtdConsumo, int seqServico, String codUsuarioRegistro)
            throws SQLException {
        String qry = "INSERT INTO "
                + "QuartoConsumo(seqHospedagem, nroQuarto, datConsumo,"
                + "qtdConsumo, seqServico, codUsuarioRegistro) "
                + "VALUES(?,?,?,?,?,?)";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, seqHospedagem);
        pStmt.setInt(2, nroQuarto);
        pStmt.setTimestamp(3, datConsumo);
        pStmt.setInt(4, qtdConsumo);
        pStmt.setInt(5, seqServico);
        pStmt.setString(6, codUsuarioRegistro);
        return pStmt.executeUpdate() > 0;
    }

    public QuartoConsumo[] busca(String cod, String coluna) throws SQLException {
        String qry = "SELECT * "
                + "FROM QuartoConsumo "
                + "WHERE " + coluna + " = ?";

        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setString(1, cod);

        ResultSet rs = pStmt.executeQuery();

        QuartoConsumo[] quartoConsumoEncontrados
                = new QuartoConsumo[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        int i = 0;
        while (rs.next()) {
            quartoConsumoEncontrados[i]
                    = new QuartoConsumo(rs.getInt(1), rs.getInt(2),
                            rs.getTimestamp(3), rs.getInt(4), rs.getInt(5),
                            rs.getString(6));
            i++;
        }
        return quartoConsumoEncontrados;
    }

    @Override
    public boolean deleta(int seqHospedagem, int nroQuarto, Timestamp datConsumo)
            throws SQLException {
        String qry = "DELETE FROM QuartoConsumo "
                + "WHERE seqHospedagem = ? AND nroQuarto = ? AND datConsumo = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, seqHospedagem);
        pStmt.setInt(2, nroQuarto);
        pStmt.setTimestamp(3, datConsumo);
        return pStmt.executeUpdate() > 0;
    }
}
