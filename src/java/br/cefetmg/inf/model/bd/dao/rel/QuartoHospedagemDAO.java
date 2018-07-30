package br.cefetmg.inf.model.bd.dao.rel;

import java.sql.SQLException;

public interface QuartoHospedagemDAO {
    boolean adiciona(int seqHospedagem, int nroQuarto, 
            int nroAdultos, int nroCriancas,
            Double vlrDiaria) throws SQLException;

    // busca();
    
    //atualiza();

    boolean deleta(int seqHospedagem, int nroQuarto) throws SQLException;
}
