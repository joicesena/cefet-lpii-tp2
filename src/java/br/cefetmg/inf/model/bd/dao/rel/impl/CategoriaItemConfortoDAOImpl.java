package br.cefetmg.inf.model.bd.dao.rel.impl;

import br.cefetmg.inf.model.bd.dao.rel.CategoriaItemConfortoDAO;
import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    public void adiciona(String codCategoria, String codItem) throws SQLException {
        String qry = "INSERT INTO "
                + "CategoriaItemConforto(codCategoria, codItem) "
                + "VALUES(?,?)";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, codCategoria);
        pStmt.setString(2, codItem);
        pStmt.execute();
    }

    @Override
    public void deleta(String codCategoria, String codItem) throws SQLException {
        String qry = "DELETE FROM CategoriaItemConforto "
                + "WHERE codCategoria = ? AND "
                + "codItem = ?";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, codCategoria);
        pStmt.setString(2, codItem);
        pStmt.execute();
    }

    public void deleta(String codCategoria) throws SQLException {
        String qry = "DELETE FROM CategoriaItemConforto "
                + "WHERE codCategoria = ? AND ";
        PreparedStatement pStmt = con.prepareStatement(qry);
        pStmt.setString(1, codCategoria);
        pStmt.execute();
    }
}