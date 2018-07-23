package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.Servico;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
                + "(desServico, vlrUnit, codServicoArea)"
                + " VALUES (?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, servico.getDesServico());
        pStmt.setDouble(2, servico.getVlrUnit());
        pStmt.setString(3, servico.getCodServicoArea());

        pStmt.execute();
    }

    @Override
    public Servico[] busca(String coluna, Object dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Servico "
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
    public Servico[] busca() throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        String qry = "SELECT * FROM Servico";
        ResultSet rs = stmt.executeQuery(qry);

        Servico[] servicosEncontrados
                = new Servico[UtilidadesBD.contaLinhasResultSet(rs)];

        int i = 0;
        rs.beforeFirst();
        while (rs.next()) {
            servicosEncontrados[i] = new Servico(rs.getInt(1), rs.getString(2),
                    rs.getDouble(3), rs.getString(4));
            i++;
        }

        return servicosEncontrados;
    }

    @Override
    public void atualiza(Object pK, Servico servicoAtualizado) throws SQLException {
        String qry = "UPDATE Servico "
                + "SET desServico = ?, vlrUnit = ?, codServicoArea = ? "
                + "WHERE seqServico = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, servicoAtualizado.getDesServico());
        pStmt.setDouble(2, servicoAtualizado.getVlrUnit());
        pStmt.setString(3, servicoAtualizado.getCodServicoArea());
        if (pK instanceof String) {
            pStmt.setString(4, pK.toString());
        } else {
            pStmt.setInt(4, Integer.parseInt(pK.toString()));
        }

        pStmt.execute();
    }

    @Override
    public void deleta(Object pK) throws SQLException {
        String qry = "DELETE FROM Servico "
                + "WHERE seqServico = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        pStmt.execute();
    }
}
