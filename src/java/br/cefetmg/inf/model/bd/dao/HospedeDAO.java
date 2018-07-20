package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Hospede;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public void adiciona(Hospede hospede) throws SQLException {
        String qry = "INSERT INTO Hospede"
                + "(codCPF, nomHospede, desTelefone, desEmail)"
                + " VALUES (?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, hospede.getCodCPF());
        pStmt.setString(2, hospede.getNomHospede());
        pStmt.setString(3, hospede.getDesTelefone());
        pStmt.setString(4, hospede.getDesEmail());

        pStmt.execute();
    }

    @Override
    public Hospede[] busca(String coluna, String dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Hospede "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setString(1, dadoBusca);
        ResultSet rs = pStmt.executeQuery();

        Hospede[] hospedesEncontrados = new Hospede[UtilidadesBD.contaLinhasResultSet(rs)];
        
        rs.beforeFirst();
        while (rs.next()) {
            hospedesEncontrados[i] = new Hospede(rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4));
            i++;
        }

        return hospedesEncontrados;
    }

    @Override
    public void atualiza(String pK, Hospede hospedeAtualizado) throws SQLException {
        String qry = "UPDATE Hospede "
                + "SET codCPF = ?, nomHospede = ?, desTelefone = ?, desEmail = ? "
                + "WHERE codCPF = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setString(1, hospedeAtualizado.getCodCPF());
        pStmt.setString(2, hospedeAtualizado.getNomHospede());
        pStmt.setString(3, hospedeAtualizado.getDesTelefone());
        pStmt.setString(4, hospedeAtualizado.getDesEmail());
        pStmt.setString(5, pK);

        pStmt.execute();
    }

    @Override
    public void deleta(String pK) throws SQLException {
        String qry = "DELETE FROM Hospede "
                + "WHERE codCPF = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setString(1, pK);

        pStmt.execute();
    }
}
