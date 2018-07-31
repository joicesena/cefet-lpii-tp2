package br.cefetmg.inf.model.pojo;

public class Programa {

    private String codPrograma;
    private String desPrograma;

    public Programa(String codPrograma, String desPrograma) {
        this.codPrograma = codPrograma;
        this.desPrograma = desPrograma;
    }

    public String getCodPrograma() {
        return codPrograma;
    }

    public void setCodPrograma(String codPrograma) {
        this.codPrograma = codPrograma;
    }

    public String getDesPrograma() {
        return desPrograma;
    }

    public void setDesPrograma(String desPrograma) {
        this.desPrograma = desPrograma;
    }
}
