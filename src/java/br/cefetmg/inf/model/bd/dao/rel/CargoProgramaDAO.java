package br.cefetmg.inf.model.bd.dao.rel;

import java.sql.SQLException;

public interface CargoProgramaDAO {
    boolean adiciona(String codPrograma, String codCargo) throws SQLException;
    
    //busca();
    
    //atualiza();
    
    boolean deleta(String codPrograma, String codCargo) throws SQLException;
}
