package br.cefetmg.inf.model.bd.dao.rel;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface QuartoHospedagemDAO {
    void adiciona(int seqHospedagem, int nroQuarto, 
            Timestamp datCheckIn, Timestamp datCheckOut,
            int nroAdultos, int nroCriancas,
            Double vlrDiaria) throws SQLException;

    // busca();
    
    //atualiza();

    void deleta(int seqHospedagem, int nroQuarto) throws SQLException;
}
