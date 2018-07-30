package br.cefetmg.inf.controller.util;

import br.cefetmg.inf.model.bd.dao.ItemConfortoDAO;
import br.cefetmg.inf.model.dto.ItemConforto;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArray;

public class ListaRegistrosComboBox {
    
    public JsonArray listaItensComboBox () throws SQLException {
        ItemConfortoDAO itemDAO = ItemConfortoDAO.getInstance();
        ItemConforto[] itens = itemDAO.busca();
        
        JsonArray arrayJson = Json.createArrayBuilder().build();
        
        for (ItemConforto item : itens) {
            arrayJson.add(
                (Json.createObjectBuilder()
                    .add("codigoItem", item.getCodItem())
                    .add("descricaoItem", item.getDesItem())
                ).build()
            );
        }
        
        return arrayJson;
    }
    
}
