package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.ServicoArea;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServicoAreaDAO extends BaseDAO<ServicoArea> {

    private static ServicoAreaDAO instancia;

    private ServicoAreaDAO() {
        super();
    }

    public static synchronized ServicoAreaDAO getInstance() {
        if (instancia == null) {
            instancia = new ServicoAreaDAO();
        }
        return instancia;
    }

    @Override
    public void adiciona(ServicoArea servicoArea) throws SQLException {
        String qry = "INSERT INTO ServicoArea"
                + "(codServicoArea, nomServicoArea)"
                + " VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, servicoArea.getCodServicoArea());
        pStmt.setString(2, servicoArea.getNomServicoArea());

        pStmt.execute();
    }

    @Override
    public ServicoArea[] busca(String coluna, String dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM ServicoArea "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        pStmt.setString(1, dadoBusca);
        ResultSet rs = pStmt.executeQuery();

        ServicoArea[] servicoAreaEncontrados = new ServicoArea[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        while (rs.next()) {
            servicoAreaEncontrados[i] = new ServicoArea(rs.getString(1), rs.getString(2));
            i++;
        }

        return servicoAreaEncontrados;
    }

    @Override
    public void atualiza(String pK, ServicoArea servicoAreaAtualizado) throws SQLException {
        String qry = "UPDATE ServicoArea "
                + "SET codServicoArea = ?, nomServicoArea = ? "
                + "WHERE codServicoArea = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setString(1, servicoAreaAtualizado.getCodServicoArea());
        pStmt.setString(2, servicoAreaAtualizado.getNomServicoArea());
        pStmt.setString(3, pK);

        pStmt.execute();
    }

    @Override
    public void deleta(String pK) throws SQLException {
        String qry = "DELETE FROM ServicoArea "
                + "WHERE codServicoArea = ?";
        PreparedStatement pStmt = con.prepareCall(qry);
        pStmt.setString(1, pK);

        pStmt.execute();
    }
}
