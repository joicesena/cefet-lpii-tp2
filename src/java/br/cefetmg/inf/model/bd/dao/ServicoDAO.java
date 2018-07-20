package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Servico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicoDAO extends BaseDAO<Servico> {

    private static ServicoDAO instancia;

    private ServicoDAO() {
        super();
    }

    public static synchronized ServicoDAO getInstance() {
        if (instancia == null) {
            instancia = new ServicoDAO();
        }
        return instancia;
    }

    @Override
    public void adiciona(Servico servico) throws SQLException {
        String qry = "INSERT INTO Servico"
                + "(seqServico, desServico, vlrUnit, codServicoArea)"
                + " VALUES (?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setInt(1, servico.getSeqServico());
        pStmt.setString(2, servico.getDesServico());
        pStmt.setDouble(3, servico.getVlrUnit());
        pStmt.setString(4, servico.getCodServicoArea());

        pStmt.execute();
    }

    @Override
    public Servico[] busca(String coluna, String dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Servico "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setString(1, dadoBusca);
        ResultSet rs = pStmt.executeQuery();

        Servico[] servicoEncontrados = new Servico[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        while (rs.next()) {
            servicoEncontrados[i] = new Servico(rs.getInt(1), rs.getString(2),
                    rs.getDouble(3), rs.getString(4));
            i++;
        }

        return servicoEncontrados;
    }

    @Override
    public void atualiza(String pK, Servico servicoAtualizado) throws SQLException {
        String qry = "UPDATE Servico "
                + "SET seqServico = ?, desServico = ?, vlrUnit = ?, codServicoArea = ? "
                + "WHERE seqServico = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setInt(1, servicoAtualizado.getSeqServico());
        pStmt.setString(2, servicoAtualizado.getDesServico());
        pStmt.setDouble(3, servicoAtualizado.getVlrUnit());
        pStmt.setString(4, servicoAtualizado.getCodServicoArea());
        pStmt.setInt(5, Integer.parseInt(pK));

        pStmt.execute();
    }

    @Override
    public void deleta(String pK) throws SQLException {
        String qry = "DELETE FROM Servico "
                + "WHERE codServico = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setString(1, pK);

        pStmt.execute();
    }
}
