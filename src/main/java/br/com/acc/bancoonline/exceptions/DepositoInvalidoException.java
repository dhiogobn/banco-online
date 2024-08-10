package br.com.acc.bancoonline.exceptions;

public class DepositoInvalidoException extends Exception{
    public DepositoInvalidoException() {
        super("O valor do depósito não pode ser menor que zero.");
    }
}
