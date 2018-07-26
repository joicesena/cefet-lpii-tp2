package br.cefetmg.inf.model.bd.dao.rel.impl;

import br.cefetmg.inf.model.bd.dao.rel.QuartoHospedagemDAO;
import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    public void adiciona(int seqHospedagem, int nroQuarto, int nroAdultos, 
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
