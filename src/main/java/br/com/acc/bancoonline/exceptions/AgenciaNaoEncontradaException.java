package br.com.acc.bancoonline.exceptions;

public class AgenciaNaoEncontradaException extends Exception{
    public AgenciaNaoEncontradaException() {
        super("Agência não encontrada.");
    }
}
