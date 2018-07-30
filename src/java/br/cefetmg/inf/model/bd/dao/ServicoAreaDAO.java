package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.ServicoArea;
import br.cefetmg.inf.model.dto.ServicoArea;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    public boolean adiciona(ServicoArea servicoArea) throws SQLException {
        String qry = "INSERT INTO ServicoArea"
                + "(codServicoArea, nomServicoArea)"
                + " VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, servicoArea.getCodServicoArea());
        pStmt.setString(2, servicoArea.getNomServicoArea());

        return pStmt.execute();
    }

    @Override
    public ServicoArea[] busca(String coluna, Object dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM ServicoArea "
                + "WHERE " + coluna + " "
                + "LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        
        if(dadoBusca instanceof String) 
            pStmt.setString(1, dadoBusca.toString());
        else 
            pStmt.setInt(1, Integer.parseInt(dadoBusca.toString()));
        
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
    public ServicoArea[] busca() throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);

        String qry = "SELECT * FROM ServicoArea";
        ResultSet rs = stmt.executeQuery(qry);

        ServicoArea[] servicoAreasEncontrados
                = new ServicoArea[UtilidadesBD.contaLinhasResultSet(rs)];

        int i = 0;
        rs.beforeFirst();
        while (rs.next()) {
            servicoAreasEncontrados[i] = new ServicoArea(rs.getString(1), rs.getString(2));
            i++;
        }

        return servicoAreasEncontrados;
    }

    @Override
    public boolean atualiza(Object pK, ServicoArea servicoAreaAtualizado) throws SQLException {
        String qry = "UPDATE ServicoArea "
                + "SET codServicoArea = ?, nomServicoArea = ? "
                + "WHERE codServicoArea = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, servicoAreaAtualizado.getCodServicoArea());
        pStmt.setString(2, servicoAreaAtualizado.getNomServicoArea());
        if(pK instanceof String) 
            pStmt.setString(3, pK.toString());
        else 
            pStmt.setInt(3, Integer.parseInt(pK.toString()));

        return pStmt.execute();
    }

    @Override
    public boolean deleta(Object pK) throws SQLException {
        String qry = "DELETE FROM ServicoArea "
                + "WHERE codServicoArea = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if(pK instanceof String) 
            pStmt.setString(1, pK.toString());
        else 
            pStmt.setInt(1, Integer.parseInt(pK.toString()));

        return pStmt.execute();
    }
}
