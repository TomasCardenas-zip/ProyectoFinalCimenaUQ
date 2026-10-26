package ProyectoFinal.patterns;

import ProyectoFinal.exception.SaldoInsuficienteException;

public interface IPasarelaPago {
    boolean cobrar(String numeroTarjeta, double monto) throws SaldoInsuficienteException;
}