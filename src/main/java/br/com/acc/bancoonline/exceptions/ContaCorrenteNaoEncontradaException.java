package br.com.acc.bancoonline.exceptions;

public class ContaCorrenteNaoEncontradaException extends Exception{
    public ContaCorrenteNaoEncontradaException() {
        super("A conta corrente especificada não existe na base de dados");
    }
}
