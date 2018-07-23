package br.cefetmg.inf.model.bd.dao.rel;

import java.sql.SQLException;

public interface CargoProgramaDAO {
    void adiciona(String codPrograma, String codCargo) throws SQLException;
    
    //busca();
    
    //atualiza();
    
    void deleta(String codPrograma, String codCargo) throws SQLException;
}
