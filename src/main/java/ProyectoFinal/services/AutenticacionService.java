package ProyectoFinal.services;

import ProyectoFinal.model.Cliente;
import ProyectoFinal.model.Persona;
import java.util.List;

public class AutenticacionService {
    private Persona usuarioAutenticado;

    public boolean autenticarUsuario(List<Persona> personas, String correo) {
        Persona persona = personas.stream()
                .filter(p -> p.getCorreo().equalsIgnoreCase(correo))
                .findFirst()
                .orElse(null);

        if (persona != null) {
            this.usuarioAutenticado = persona;
            return true;
        }
        return false;
    }

    public void cerrarSesion() {
        this.usuarioAutenticado = null;
    }

    public Persona getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}