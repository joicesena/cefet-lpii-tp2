package br.cefetmg.inf.model.bd.dao.rel.impl;

import br.cefetmg.inf.model.bd.dao.rel.QuartoHospedagemDAO;
import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.rel.QuartoHospedagem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuartoHospedagemDAOImpl implements QuartoHospedagemDAO {

    private static QuartoHospedagemDAOImpl instancia;
    private static Connection con;

    private QuartoHospedagemDAOImpl() {
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized QuartoHospedagemDAOImpl getInstance() {
        if (instancia == null) {
            instancia = new QuartoHospedagemDAOImpl();
        }
        return instancia;
    }

    @Override
    public boolean adiciona(int seqHospedagem, int nroQuarto, int nroAdultos, 
            int nroCriancas, Double vlrDiaria) throws SQLException {
        String qry = "INSERT INTO "
                + "QuartoHospedagem("
                + "seqHospedagem, "
                + "nroQuarto, "
                + "nroAdultos, nroCriancas, "
                + "vlrDiaria) "
                + "VALUES(?,?,?,?,?)";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, seqHospedagem);
        pStmt.setInt(2, nroQuarto);
        pStmt.setInt(3, nroAdultos);
        pStmt.setInt(4, nroCriancas);
        pStmt.setDouble(5, vlrDiaria);
        return pStmt.executeUpdate() > 0;
    }
    
    @Override
    public QuartoHospedagem[] busca(String cod, String coluna) throws SQLException {
        String qry = "SELECT * "
                + "FROM QuartoHospedagem "
                + "WHERE " + coluna + " = ?";

        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setString(1, cod);

        ResultSet rs = pStmt.executeQuery();

        QuartoHospedagem[] quartoHospedagemEncontrados
                = new QuartoHospedagem[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        int i = 0;
        while (rs.next()) {
            quartoHospedagemEncontrados[i]
                    = new QuartoHospedagem(rs.getInt(1), rs.getInt(2),
                            rs.getInt(3), rs.getInt(4), rs.getDouble(5));
            i++;
        }
        return quartoHospedagemEncontrados;
    }

    @Override
    public boolean deleta(int seqHospedagem, int nroQuarto) throws SQLException {
        String qry = "DELETE FROM QuartoHospedagem "
                + "WHERE seqHospedagem = ? AND "
                + "nroQuarto = ? ";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, seqHospedagem);
        pStmt.setInt(2, nroQuarto);
        return pStmt.executeUpdate() > 0;
    }
}
