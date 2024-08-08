package br.com.acc.bancoonline.exceptions;

public class CpfInvalidoException extends Exception{
    public CpfInvalidoException() {
        super("Cpf invalido, insira um cpf válido.");
    }
}
