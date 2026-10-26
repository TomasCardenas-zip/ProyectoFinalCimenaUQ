package ProyectoFinal.exception;

public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }

    public SaldoInsuficienteException() {
        super("Saldo insuficiente para realizar la operación.");
    }
}