package br.com.acc.bancoonline.exceptions;

public class ExtratoNaoEncontradoException extends Exception{
    public ExtratoNaoEncontradoException() {
        super("O extrato especificado não está na base de dados.");
    }
}
