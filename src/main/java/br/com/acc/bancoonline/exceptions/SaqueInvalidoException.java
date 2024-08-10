package br.com.acc.bancoonline.exceptions;

public class SaqueInvalidoException extends Exception{
    public SaqueInvalidoException() {
        super("O valor do saque precisa ser menor que o seu saldo.");
    }
}
