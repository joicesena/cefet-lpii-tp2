package br.cefetmg.inf.exception;

public class PKRepetidaException extends Exception {

    public PKRepetidaException() {
        super("Não é possível modificar esse registro. Código repetido.");
    }

    public PKRepetidaException(String operacao) {
        super("Não é possível " + operacao + " esse registro. Código repetido.");
    }
}
