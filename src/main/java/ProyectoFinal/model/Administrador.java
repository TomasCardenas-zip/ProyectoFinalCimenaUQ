package ProyectoFinal.model;

public class Administrador extends Persona{
    private String codigoEmpleado;

    public Administrador(String id, String nombre, String correo, String telefono, String codigoEmpleado) {
        super(id, nombre, correo, telefono);
        this.codigoEmpleado = codigoEmpleado;
    }

    public void recargarTarjeta(TarjetaVirtual tarjeta, double monto) {
        if (tarjeta != null && monto > 0) {
            tarjeta.recargar(monto);
        }
    }

    public String getCodigoEmpleado() { return codigoEmpleado; }
    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado; }
}

