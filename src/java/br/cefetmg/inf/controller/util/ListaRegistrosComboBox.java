package br.cefetmg.inf.controller.util;

import br.cefetmg.inf.model.bd.dao.*;
import br.cefetmg.inf.model.dto.*;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArray;

public class ListaRegistrosComboBox {
    
    public JsonArray listarItensConfortoComboBox () throws SQLException {
        ItemConfortoDAO registroDAO = ItemConfortoDAO.getInstance();
        ItemConforto[] registros = registroDAO.busca();
        
        JsonArray arrayJson = Json.createArrayBuilder().build();
        
        for (ItemConforto registro : registros) {
            arrayJson.add(
                (Json.createObjectBuilder()
                    .add("codigoItem", registro.getCodItem())
                    .add("descricaoItem", registro.getDesItem())
                ).build()
            );
        }
        
        return arrayJson;
    }

    public JsonArray listarProgramasComboBox () throws SQLException {
        ProgramaDAO registroDAO = ProgramaDAO.getInstance();
        Programa [] registros = registroDAO.busca();
        
        JsonArray arrayJson = Json.createArrayBuilder().build();
        
        for (Programa registro : registros) {
            arrayJson.add(
                (Json.createObjectBuilder()
                    .add("codPrograma", registro.getCodPrograma())
                    .add("desPrograma", registro.getDesPrograma())
                ).build()
            );
        }
        
        return arrayJson;
    }
    
}
