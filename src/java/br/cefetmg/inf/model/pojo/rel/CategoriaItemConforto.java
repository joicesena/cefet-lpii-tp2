package br.cefetmg.inf.model.pojo.rel;

public class CategoriaItemConforto {
    private String codCategoria;
    private String codItem;

    public CategoriaItemConforto(String codCategoria, String codItem) {
        this.codCategoria = codCategoria;
        this.codItem = codItem;
    }

    public String getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(String codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getCodItem() {
        return codItem;
    }

    public void setCodItem(String codItem) {
        this.codItem = codItem;
    }
}
