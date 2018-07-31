package br.cefetmg.inf.model.bd.dao.rel.impl;

import br.cefetmg.inf.model.bd.dao.rel.CategoriaItemConfortoDAO;
import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import br.cefetmg.inf.model.bd.util.UtilidadesBD;
import br.cefetmg.inf.model.dto.ServicoArea;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoriaItemConfortoDAOImpl implements CategoriaItemConfortoDAO {

    private static CategoriaItemConfortoDAOImpl instancia;
    private static Connection con;

    private CategoriaItemConfortoDAOImpl() {
        con = new ConnectionFactory().getConnection();
    }

    public static synchronized CategoriaItemConfortoDAOImpl getInstance() {
        if (instancia == null) {
            instancia = new CategoriaItemConfortoDAOImpl();
        }
        return instancia;
    }

    @Override
    public boolean adiciona(String codCategoria, String codItem) throws SQLException {
        String qry = "INSERT INTO "
                + "CategoriaItemConforto(codCategoria, codItem) "
                + "VALUES(?,?)";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, codCategoria);
        pStmt.setString(2, codItem);
        return pStmt.executeUpdate() > 0;
    }

//    @Override
//    public boolean deleta(String codCategoria, String codItem) throws SQLException {
//        String qry = "DELETE FROM CategoriaItemConforto "
//                + "WHERE codCategoria = ? AND "
//                + "codItem = ?";
//        PreparedStatement pStmt = con.prepareStatement(qry);
//        pStmt.setString(1, codCategoria);
//        pStmt.setString(2, codItem);
//        return pStmt.executeUpdate() > 0;
//    }

    @Override
    public boolean deleta(String cod, String coluna) throws SQLException {
        String qry = "DELETE FROM CategoriaItemConforto "
                + "WHERE " + coluna + " = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, cod);
        return pStmt.executeUpdate() > 0;
    }

    public String [] busca (String cod, String coluna) throws SQLException {
        String qry;
        if (coluna.equals("codItem")) {
            qry = "SELECT codCategoria FROM CategoriaItemConforto "
                    + "WHERE codItem = ?";
        } else {
            qry = "SELECT codItem FROM CategoriaItemConforto "
                    + "WHERE codCategoria = ?";
        }
        PreparedStatement pStmt = con.prepareStatement(qry, ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
            pStmt.setString(1, cod);

        ResultSet rs = pStmt.executeQuery();

        String[] codigos = new String[UtilidadesBD.contaLinhasResultSet(rs)];

        rs.beforeFirst();
        int i = 0;
        while (rs.next()) {
            codigos[i] = rs.getString(coluna);
            i++;
        }
        
        return codigos;
    }
}