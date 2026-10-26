package ProyectoFinal.services;

import ProyectoFinal.model.AsientoFuncion;
import ProyectoFinal.model.Funcion;
import ProyectoFinal.model.enums.EstadoAsiento;

import java.util.List;

public class ReservaService {

    public Funcion buscarFuncionPorId(List<Funcion> funciones, String idFuncion) {
        return funciones.stream()
                .filter(f -> f.getId().equalsIgnoreCase(idFuncion))
                .findFirst()
                .orElse(null);
    }

    public AsientoFuncion buscarAsiento(Funcion funcion, String codigoAsiento) {
        return funcion.getAsientosFuncion().stream()
                .filter(af -> af.getAsiento().getCodigo().equalsIgnoreCase(codigoAsiento))
                .findFirst()
                .orElse(null);
    }

    public void ocuparAsiento(AsientoFuncion asientoFuncion) {
        if (asientoFuncion.isOcupado()) {
            throw new IllegalStateException("El asiento " + asientoFuncion.getAsiento().getCodigo() + " ya se encuentra ocupado.");
        }
        asientoFuncion.setEstado(EstadoAsiento.OCUPADO);
    }
}