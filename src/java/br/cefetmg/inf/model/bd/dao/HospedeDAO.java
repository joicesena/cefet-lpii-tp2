package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Hospede;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class HospedeDAO extends BaseDAO<Hospede> {

    private static HospedeDAO instancia;

    private HospedeDAO() {
        super();
    }

    public static synchronized HospedeDAO getInstance() {
        if (instancia == null) {
            instancia = new HospedeDAO();
        }
        return instancia;
    }

    @Override
    public boolean adiciona(Hospede hospede) throws SQLException {
        String qry = "INSERT INTO Hospede"
                + "(codCPF, nomHospede, desTelefone, desEmail)"
                + " VALUES (?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, hospede.getCodCPF());
        pStmt.setString(2, hospede.getNomHospede());
        pStmt.setString(3, hospede.getDesTelefone());
        pStmt.setString(4, hospede.getDesEmail());

        return pStmt.execute();
    }

    @Override
    public Hospede[] busca(String coluna, Object dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Hospede "
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

        Hospede[] hospedesEncontrados = new Hospede[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        while (rs.next()) {
            hospedesEncontrados[i] = new Hospede(rs.getString(1),
                    rs.getString(2), rs.getString(3), rs.getString(4));
            i++;
        }

        return hospedesEncontrados;
    }

    @Override
    public Hospede[] busca() throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        String qry = "SELECT * FROM Hospede";
        ResultSet rs = stmt.executeQuery(qry);

        Hospede[] hospedesEncontrados
                = new Hospede[UtilidadesBD.contaLinhasResultSet(rs)];

        int i = 0;
        rs.beforeFirst();
        while (rs.next()) {
            hospedesEncontrados[i] = new Hospede(rs.getString(1),
                    rs.getString(2), rs.getString(3), rs.getString(4));
            i++;
        }

        return hospedesEncontrados;
    }

    @Override
    public boolean atualiza(Object pK, Hospede hospedeAtualizado) throws SQLException {
        String qry = "UPDATE Hospede "
                + "SET codCPF = ?, nomHospede = ?, desTelefone = ?, desEmail = ? "
                + "WHERE codCPF = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, hospedeAtualizado.getCodCPF());
        pStmt.setString(2, hospedeAtualizado.getNomHospede());
        pStmt.setString(3, hospedeAtualizado.getDesTelefone());
        pStmt.setString(4, hospedeAtualizado.getDesEmail());
        if (pK instanceof String) {
            pStmt.setString(5, pK.toString());
        } else {
            pStmt.setInt(5, Integer.parseInt(pK.toString()));
        }

        return pStmt.execute();
    }

    @Override
    public boolean deleta(Object pK) throws SQLException {
        String qry = "DELETE FROM Hospede "
                + "WHERE codCPF = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        return pStmt.execute();
    }
}
