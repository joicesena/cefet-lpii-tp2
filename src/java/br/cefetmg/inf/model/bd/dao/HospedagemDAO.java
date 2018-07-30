package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Hospedagem;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

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
    public boolean adiciona(Hospedagem hospedagem) throws SQLException {
        String qry = "INSERT INTO Hospedagem"
                + "(datCheckIn, datCheckOut, vlrPago, codCPF)"
                + " VALUES (?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setTimestamp(1, hospedagem.getDatCheckIn());
        pStmt.setTimestamp(2, hospedagem.getDatCheckOut());
        pStmt.setDouble(3, hospedagem.getVlrPago());
        pStmt.setString(4, hospedagem.getCodCPF());

        return pStmt.execute();
    }

    @Override
    public Hospedagem[] busca(String coluna, Object dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Hospedagem "
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

        Hospedagem[] hospedagemEncontrados = new Hospedagem[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        while (rs.next()) {
            hospedagemEncontrados[i] = new Hospedagem(rs.getInt(1),
                    rs.getTimestamp(2), rs.getTimestamp(3), rs.getDouble(4),
                    rs.getString(5));
            i++;
        }

        return hospedagemEncontrados;
    }

    @Override
    public Hospedagem[] busca() throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        String qry = "SELECT * FROM Hospedagem";
        ResultSet rs = stmt.executeQuery(qry);

        Hospedagem[] hospedagemsEncontrados
                = new Hospedagem[UtilidadesBD.contaLinhasResultSet(rs)];

        int i = 0;
        rs.beforeFirst();
        while (rs.next()) {
            hospedagemsEncontrados[i] = new Hospedagem(rs.getInt(1),
                    rs.getTimestamp(2), rs.getTimestamp(3), rs.getDouble(4),
                    rs.getString(5));
            i++;
        }

        return hospedagemsEncontrados;
    }

    public Hospedagem[] busca(Hospedagem hospedagem) throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        String qry = "SELECT * FROM Hospedagem WHERE "
                + "datCheckIn=? AND datCheckOut=? AND vlrPago=? AND codCPF=?";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setTimestamp(1, hospedagem.getDatCheckIn());
        pStmt.setTimestamp(2, hospedagem.getDatCheckOut());
        pStmt.setDouble(3, hospedagem.getVlrPago());
        pStmt.setString(4, hospedagem.getCodCPF());

        ResultSet rs = pStmt.executeQuery();
        Hospedagem[] hospedagemsEncontrados
                = new Hospedagem[UtilidadesBD.contaLinhasResultSet(rs)];

        int i = 0;
        rs.beforeFirst();
        while (rs.next()) {
            hospedagemsEncontrados[i] = new Hospedagem(rs.getInt(1),
                    rs.getTimestamp(2), rs.getTimestamp(3), rs.getDouble(4),
                    rs.getString(5));
            i++;
        }

        return hospedagemsEncontrados;
    }


    @Override
    public boolean atualiza(Object pK, Hospedagem hospedagemAtualizado) throws SQLException {
        String qry = "UPDATE Hospedagem "
                + "SET datCheckIn = ?, datCheckOut = ?, vlrPago = ?, codCPF = ? "
                + "WHERE seqHospedagem = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setTimestamp(1, hospedagemAtualizado.getDatCheckIn());
        pStmt.setTimestamp(2, hospedagemAtualizado.getDatCheckOut());
        pStmt.setDouble(3, hospedagemAtualizado.getVlrPago());
        pStmt.setString(4, hospedagemAtualizado.getCodCPF());
        if (pK instanceof String) {
            pStmt.setString(5, pK.toString());
        } else {
            pStmt.setInt(5, Integer.parseInt(pK.toString()));
        }

        return pStmt.execute();
    }

    public boolean atualiza(Hospedagem hospedagemAntiga,
            Hospedagem hospedagemAtualizado) throws SQLException {
        String qry = "UPDATE Hospedagem "
                + "SET datCheckIn = ?, datCheckOut = ?, vlrPago = ?, codCPF = ? "
                + "WHERE datCheckIn = ? AND datCheckOut = ? AND vlrPago = ? AND "
                + "codCPF = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setTimestamp(1, hospedagemAtualizado.getDatCheckIn());
        pStmt.setTimestamp(2, hospedagemAtualizado.getDatCheckOut());
        pStmt.setDouble(3, hospedagemAtualizado.getVlrPago());
        pStmt.setString(4, hospedagemAtualizado.getCodCPF());
        pStmt.setTimestamp(5, hospedagemAntiga.getDatCheckIn());
        pStmt.setTimestamp(6, hospedagemAntiga.getDatCheckOut());
        pStmt.setDouble(7, hospedagemAntiga.getVlrPago());
        pStmt.setString(8, hospedagemAntiga.getCodCPF());

        return pStmt.execute();
    }

    public boolean atualiza(Timestamp datCheckInAntiga, Timestamp datCheckOutAntiga,
            Double vlrPagoAntigo, String codCPFAntigo,
            Hospedagem hospedagemAtualizado) throws SQLException {
        String qry = "SELECT * FROM Hospedagem "
                + "WHERE datCheckIn = ? AND datCheckOut = ? AND vlrPago = ? AND "
                + "codCPF = ?";

        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setTimestamp(1, datCheckInAntiga);
        pStmt.setTimestamp(2, datCheckOutAntiga);
        pStmt.setDouble(3, vlrPagoAntigo);
        pStmt.setString(4, codCPFAntigo);

        ResultSet rs = pStmt.executeQuery();

        if (UtilidadesBD.contaLinhasResultSet(rs) == 1) {
            qry = "UPDATE Hospedagem "
                    + "SET datCheckIn = ?, datCheckOut = ?, vlrPago = ?, codCPF = ? "
                    + "WHERE datCheckIn = ? AND datCheckOut = ? AND vlrPago = ? "
                    + "AND codCPF = ?";
            pStmt = con.prepareStatement(qry);
            pStmt.setTimestamp(1, hospedagemAtualizado.getDatCheckIn());
            pStmt.setTimestamp(2, hospedagemAtualizado.getDatCheckOut());
            pStmt.setDouble(3, hospedagemAtualizado.getVlrPago());
            pStmt.setString(4, hospedagemAtualizado.getCodCPF());
            pStmt.setTimestamp(5, datCheckInAntiga);
            pStmt.setTimestamp(6, datCheckOutAntiga);
            pStmt.setDouble(7, vlrPagoAntigo);
            pStmt.setString(8, codCPFAntigo);

            return pStmt.execute();
        }
        return false;
    }

    @Override
    public boolean deleta(Object pK) throws SQLException {
        String qry = "DELETE FROM Hospedagem "
                + "WHERE seqHospedagem = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        return pStmt.execute();
    }

    public boolean deleta(Hospedagem hospedagemAntiga) throws SQLException {
        String qry = "DELETE FROM Hospedagem "
                + "WHERE datCheckIn = ? AND datCheckOut = ? AND vlrPago = ? AND "
                + "codCPF = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setTimestamp(1, hospedagemAntiga.getDatCheckIn());
        pStmt.setTimestamp(2, hospedagemAntiga.getDatCheckOut());
        pStmt.setDouble(3, hospedagemAntiga.getVlrPago());
        pStmt.setString(4, hospedagemAntiga.getCodCPF());

        return pStmt.execute();
    }

    public boolean deleta(Timestamp datCheckInAntiga, Timestamp datCheckOutAntiga,
            Double vlrPagoAntigo, String codCPFAntigo) throws SQLException {
        String qry = "SELECT * FROM Hospedagem "
                + "WHERE datCheckIn = ? AND datCheckOut = ? AND vlrPago = ? AND "
                + "codCPF = ?";

        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setTimestamp(1, datCheckInAntiga);
        pStmt.setTimestamp(2, datCheckOutAntiga);
        pStmt.setDouble(3, vlrPagoAntigo);
        pStmt.setString(4, codCPFAntigo);

        ResultSet rs = pStmt.executeQuery();

        if (UtilidadesBD.contaLinhasResultSet(rs) == 1) {
            qry = "DELETE FROM Hospedagem "
                    + "WHERE datCheckIn = ? AND datCheckOut = ? AND vlrPago = ? "
                    + "AND codCPF = ?";
            pStmt = con.prepareStatement(qry);
            pStmt.setTimestamp(1, datCheckInAntiga);
            pStmt.setTimestamp(2, datCheckOutAntiga);
            pStmt.setDouble(3, vlrPagoAntigo);
            pStmt.setString(4, codCPFAntigo);

            return pStmt.execute();
        }
        return false;
    }
}
