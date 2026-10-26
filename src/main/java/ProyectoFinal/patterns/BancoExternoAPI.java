package ProyectoFinal.patterns;

public class BancoExternoAPI {

    public int ejecutarTransaccionDirecta(String tarjeta, double monto, String moneda) {
        if (tarjeta == null || tarjeta.isBlank() || monto <= 0) {
            return 400; // Solicitud inválida
        }
        // Simulación de transacción bancaria aprobada
        return 200; // OK
    }
}
