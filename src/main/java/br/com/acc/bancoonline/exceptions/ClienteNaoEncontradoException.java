package br.com.acc.bancoonline.exceptions;

public class ClienteNaoEncontradoException extends Exception{
    public ClienteNaoEncontradoException() {
        super("O cliente especificado não existe na base de dados");
    }
}
