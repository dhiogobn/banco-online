package br.com.acc.bancoonline.exceptions;

public class CpfInvalidoException extends Exception{
    public CpfInvalidoException() {
        super("CPF inválido, insira um CPF válido.");
    }
}
