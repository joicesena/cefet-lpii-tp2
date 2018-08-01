package br.cefetmg.inf.exception;

public class RegistroUtilizadoExternamenteException extends Exception {

    public RegistroUtilizadoExternamenteException() {
        super("Não é possível modificar o código desse registro. Ele é utilizado em algum registro externo.");
    }

    public RegistroUtilizadoExternamenteException(String operacao, String tabela) {
        super("Não é possível " + operacao + " o código desse registro. Ele é utilizado em um(a) " + tabela);
    }
}
