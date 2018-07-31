package br.cefetmg.inf.controller.util;

import br.cefetmg.inf.model.pojo.Programa;
import br.cefetmg.inf.model.pojo.ItemConforto;
import br.cefetmg.inf.model.bd.dao.*;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArray;

public class ListaRegistrosComboBox {
    //
    // LISTA DE PROGRAMAS
    //
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
    
    //
    // LISTA DE CARGOS
    //
    public JsonArray listarCargosComboBox () throws SQLException {
        CargoDAO registroDAO = CargoDAO.getInstance();
        Cargo [] registros = registroDAO.busca();
        
        JsonArray arrayJson = Json.createArrayBuilder().build();
        
        for (Cargo registro : registros) {
            arrayJson.add(
                (Json.createObjectBuilder()
                    .add("codCargo", registro.getCodCargo())
                    .add("nomCargo", registro.getNomCargo())
                ).build()
            );
        }
        
        return arrayJson;
    }
    
    //
    // LISTA DE ITENS DE CONFORTO
    //
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

    //
    // LISTA DE CATEGORIAS DE QUARTO
    //
    public JsonArray listarCategoriasQuartoComboBox () throws SQLException {
        CategoriaQuartoDAO registroDAO = CategoriaQuartoDAO.getInstance();
        CategoriaQuarto [] registros = registroDAO.busca();
        
        JsonArray arrayJson = Json.createArrayBuilder().build();
        
        for (CategoriaQuarto registro : registros) {
            arrayJson.add(
                (Json.createObjectBuilder()
                    .add("codCategoria", registro.getCodCategoria())
                    .add("nomCategoria", registro.getNomCategoria())
                    .add("vlrDiaria", registro.getVlrDiaria())
                ).build()
            );
        }
        
        return arrayJson;
    }

    //
    // LISTA DE ÁREAS DE SERVIÇO
    //
    public JsonArray listarServicoAreaComboBox () throws SQLException {
        ServicoAreaDAO registroDAO = ServicoAreaDAO.getInstance();
        ServicoArea [] registros = registroDAO.busca();
        
        JsonArray arrayJson = Json.createArrayBuilder().build();
        
        for (ServicoArea registro : registros) {
            arrayJson.add(
                (Json.createObjectBuilder()
                    .add("codServicoArea", registro.getCodServicoArea())
                    .add("nomServicoArea", registro.getNomServicoArea())
                ).build()
            );
        }
        
        return arrayJson;
    }
}
