package ProyectoFinal.patterns;

import ProyectoFinal.model.AsientoFuncion;
import ProyectoFinal.model.Entrada;
import ProyectoFinal.model.Funcion;

public class EntradaFactory {

    public Entrada crearEntrada(Funcion funcion, AsientoFuncion asientoFuncion) {
        if (asientoFuncion.isOcupado()) {
            throw new IllegalStateException("El asiento " + asientoFuncion.getAsiento().getCodigo() + " ya está ocupado.");
        }

        String idEntrada = "ENT-" + System.currentTimeMillis();
        String descripcion = "Entrada: " + funcion.getPelicula().getTitulo() + " | Silla: " + asientoFuncion.getAsiento().getCodigo();
        double precio = funcion.getPrecioEntrada();

        return new Entrada(idEntrada, descripcion, precio, funcion, asientoFuncion);
    }
}