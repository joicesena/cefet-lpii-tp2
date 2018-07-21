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
                + "(datCheckIn, datCheckOut, vlrPago, codCPF)"
                + " VALUES (?,?,?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setTimestamp(1, hospedagem.getDatCheckIn());
        pStmt.setTimestamp(2, hospedagem.getDatCheckOut());
        pStmt.setDouble(3, hospedagem.getVlrPago());
        pStmt.setString(4, hospedagem.getCodCPF());

        pStmt.execute();
    }

    @Override
    public Hospedagem[] busca(String coluna, Object dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Hospedagem "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        
        if(dadoBusca instanceof String) 
            pStmt.setString(1, dadoBusca.toString());
        else 
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        
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
    public void atualiza(Object pK, Hospedagem hospedagemAtualizado) throws SQLException {
        String qry = "UPDATE Hospedagem "
                + "SET datCheckIn = ?, datCheckOut = ?, vlrPago = ?, codCPF = ? "
                + "WHERE seqHospedagem = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setTimestamp(1, hospedagemAtualizado.getDatCheckIn());
        pStmt.setTimestamp(2, hospedagemAtualizado.getDatCheckOut());
        pStmt.setDouble(3, hospedagemAtualizado.getVlrPago());
        pStmt.setString(4, hospedagemAtualizado.getCodCPF());
        if(pK instanceof String) 
            pStmt.setString(5, pK.toString());
        else 
            pStmt.setInt(5, Integer.parseInt(pK.toString()));

        pStmt.execute();
    }

    @Override
    public void deleta(Object pK) throws SQLException {
        String qry = "DELETE FROM Hospedagem "
                + "WHERE codHospedagem = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if(pK instanceof String) 
            pStmt.setString(1, pK.toString());
        else 
            pStmt.setInt(1, Integer.parseInt(pK.toString()));

        pStmt.execute();
    }
}
