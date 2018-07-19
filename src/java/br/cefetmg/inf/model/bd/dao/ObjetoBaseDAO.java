package br.cefetmg.inf.model.bd.dao;

import br.cefetmg.inf.model.bd.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

abstract class ObjetoBaseDAO<ObjetoDTO> {

    protected Connection con;

    protected ObjetoBaseDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    protected abstract void adiciona(ObjetoDTO obj);

    protected ResultSet buscaGeneralziada(String tabela, String coluna, String chaveBusca) throws SQLException {
        String qry = String.format("SELECT * FROM %s WHERE %s LIKE %s", tabela, coluna, chaveBusca);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(qry);

        return rs;
    }

    protected void atualizacaoGeneralizada(String tabela, String coluna, String chaveBusca, String dadoAtualizado) throws SQLException {
        Statement st;
        String qry;
        ResultSet rs;
        
        qry = String.format("UPDATE %s SET %s WHERE %s LIKE %s",
                tabela, dadoAtualizado, coluna, chaveBusca);
        st = con.createStatement();
        rs = st.executeQuery(qry);
    }

    protected void delecaoGeneralizada(String tabela, String coluna, String chaveBusca) throws SQLException {
        Statement st;
        String qry;
        ResultSet rs;
        
        qry = String.format("DELETE FROM %s WHERE %s LIKE %s",
                tabela, coluna, chaveBusca);
        st = con.createStatement();
        rs = st.executeQuery(qry);
    }
}
