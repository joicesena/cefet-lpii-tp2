package br.cefetmg.inf.model.bd.dao.rel;

import java.sql.SQLException;

public interface CategoriaItemConfortoDAO {
    boolean adiciona(String codCategoria, String codItem) throws SQLException;
    
    //busca();
    
    //atualiza();
    
    boolean deleta(String codCategoria, String codItem) throws SQLException;
}
