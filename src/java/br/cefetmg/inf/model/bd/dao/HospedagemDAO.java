package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Hospedagem;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HospedagemDAO extends BaseDAO<Hospedagem> {

    private static HospedagemDAO instancia;

    private HospedagemDAO() {
        super();
    }

    public static synchronized HospedagemDAO getInstance() {
        if (instancia == null) {
            instancia = new HospedagemDAO();
        }
        return instancia;
    }

    @Override
    public void adiciona(Hospedagem hospedagem) throws SQLException {
        String qry = "INSERT INTO Hospedagem"
                + "(seqHospedagem, datCheckIn, datCheckOut, vlrPago, codCPF)"
                + " VALUES (?,?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, hospedagem.getSeqHospedagem());
        pStmt.setTimestamp(2, hospedagem.getDatCheckIn());
        pStmt.setTimestamp(3, hospedagem.getDatCheckOut());
        pStmt.setDouble(4, hospedagem.getVlrPago());
        pStmt.setString(5, hospedagem.getCodCPF());

        pStmt.execute();
    }

    @Override
    public Hospedagem[] busca(String coluna, String dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Hospedagem "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setString(1, dadoBusca);
        ResultSet rs = pStmt.executeQuery();

        Hospedagem[] hospedagemEncontrados = new Hospedagem[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        while (rs.next()) {
            hospedagemEncontrados[i] = new Hospedagem(rs.getInt(1), rs.getTimestamp(2),
                    rs.getTimestamp(3), rs.getDouble(4), rs.getString(5));
            i++;
        }

        return hospedagemEncontrados;
    }

    @Override
    public void atualiza(String pK, Hospedagem hospedagemAtualizado) throws SQLException {
        String qry = "UPDATE Hospedagem "
                + "SET seqHospedagem = ?, datCheckIn = ?, datCheckOut = ?, vlrPago = ?, codCPF = ?"
                + "WHERE codHospedagem = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setInt(1, hospedagemAtualizado.getSeqHospedagem());
        pStmt.setTimestamp(2, hospedagemAtualizado.getDatCheckIn());
        pStmt.setTimestamp(3, hospedagemAtualizado.getDatCheckOut());
        pStmt.setDouble(4, hospedagemAtualizado.getVlrPago());
        pStmt.setString(5, hospedagemAtualizado.getCodCPF());
        pStmt.setString(6, pK);

        pStmt.execute();
    }

    @Override
    public void deleta(String pK) throws SQLException {
        String qry = "DELETE FROM Hospedagem "
                + "WHERE codHospedagem = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setString(1, pK);

        pStmt.execute();
    }
}
