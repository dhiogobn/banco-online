package br.com.acc.bancoonline.exceptions;

public class CampoVazioGenericoException extends Exception{
    public CampoVazioGenericoException() {
        super("Há campos obrigatórios que precisam ser preenchidos.");
    }
}
