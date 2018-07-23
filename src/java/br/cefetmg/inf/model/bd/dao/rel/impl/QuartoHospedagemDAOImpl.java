package br.cefetmg.inf.model.bd.dao.rel.impl;

import br.cefetmg.inf.model.bd.dao.rel.QuartoHospedagemDAO;
import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
    public void adiciona(int seqHospedagem, int nroQuarto, Timestamp datCheckIn,
            Timestamp datCheckOut, int nroAdultos, int nroCriancas,
            Double vlrDiaria) throws SQLException {
        String qry = "INSERT INTO "
                + "QuartoHospedagem("
                + "seqHospedagem, "
                + "nroQuarto, "
                + "datCheckIn, datCheckOut, "
                + "nroAdultos, nroCriancas, "
                + "vlrDiaria) "
                + "VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, seqHospedagem);
        pStmt.setInt(2, nroQuarto);
        pStmt.setTimestamp(3, datCheckIn);
        pStmt.setTimestamp(4, datCheckOut);
        pStmt.setInt(5, nroAdultos);
        pStmt.setInt(6, nroCriancas);
        pStmt.setDouble(7, vlrDiaria);
        pStmt.execute();
    }

    @Override
    public void deleta(int seqHospedagem, int nroQuarto) throws SQLException {
        String qry = "DELETE FROM QuartoHospedagem "
                + "WHERE seqHospedagem = ? AND "
                + "nroQuarto = ? ";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, seqHospedagem);
        pStmt.setInt(2, nroQuarto);
        pStmt.execute();
    }

}
