package ProyectoFinal.patterns;

import ProyectoFinal.exception.SaldoInsuficienteException;

public class PasarelaPagoAdapter implements IPasarelaPago {

    private final BancoExternoAPI bancoApi;

    public PasarelaPagoAdapter() {
        this.bancoApi = new BancoExternoAPI();
    }

    @Override
    public boolean cobrar(String numeroTarjeta, double monto) throws SaldoInsuficienteException {
        int codigoRespuesta = bancoApi.ejecutarTransaccionDirecta(numeroTarjeta, monto, "COP");
        if (codigoRespuesta != 200) {
            throw new SaldoInsuficienteException("La transacción fue rechazada por la pasarela bancaria externa.");
        }
        return true;
    }
}