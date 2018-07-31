package br.cefetmg.inf.model.bd.dao;

import static br.cefetmg.inf.model.bd.dao.BaseDAO.con;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.pojo.Programa;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class ProgramaDAO extends BaseDAO<Programa> {

    private static ProgramaDAO instancia;

    private ProgramaDAO() {
        super();
    }

    public static synchronized ProgramaDAO getInstance() {
        if (instancia == null) {
            instancia = new ProgramaDAO();
        }
        return instancia;
    }

    @Override
    public boolean adiciona(Programa programa) throws SQLException {
        String qry = "INSERT INTO Programa"
                + "(codPrograma, desPrograma) "
                + "VALUES (?,?)";

        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, programa.getCodPrograma());
        pStmt.setString(2, programa.getDesPrograma());

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public Programa[] busca(String coluna, Object dadoBusca) throws SQLException {
        int i = 0;

        String qry = "SELECT * FROM Programa "
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

        Programa[] programasEncontrados = new Programa[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        while (rs.next()) {
            programasEncontrados[i] = new Programa(rs.getString(1),
                    rs.getString(2));
            i++;
        }

        return programasEncontrados;
    }

    @Override
    public Programa[] busca() throws SQLException {
        Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        
        String qry = "SELECT * FROM Programa";
        ResultSet rs = stmt.executeQuery(qry);

        Programa[] programasEncontrados
                = new Programa[UtilidadesBD.contaLinhasResultSet(rs)];

        int i = 0;
        rs.beforeFirst();
        while (rs.next()) {
            programasEncontrados[i] = new Programa(rs.getString(1),
                    rs.getString(2));
            i++;
        }

        return programasEncontrados;
    }

    @Override
    public boolean atualiza(Object pK, Programa programaAtualizado) throws SQLException {
        String qry = "UPDATE Programa "
                + "SET codPrograma = ?, desPrograma = ? "
                + "WHERE codPrograma LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, programaAtualizado.getCodPrograma());
        pStmt.setString(2, programaAtualizado.getDesPrograma());
        if (pK instanceof String) {
            pStmt.setString(3, pK.toString());
        } else {
            pStmt.setInt(3, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }

    @Override
    public boolean deleta(Object pK) throws SQLException {
        String qry = "DELETE FROM Programa "
                + "WHERE codPrograma LIKE ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        if (pK instanceof String) {
            pStmt.setString(1, pK.toString());
        } else {
            pStmt.setInt(1, Integer.parseInt(pK.toString()));
        }

        return pStmt.executeUpdate() > 0;
    }
}
