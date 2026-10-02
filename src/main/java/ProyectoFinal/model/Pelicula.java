package ProyectoFinal.model;

public class Pelicula {
    private String id;
    private String titulo;
    private int duracionMinutos;
    private String genero;

    public Pelicula(String id, String titulo, int duracionMinutos, String genero) {
        this.id = id;
        this.titulo = titulo;
        this.duracionMinutos = duracionMinutos;
        this.genero = genero;
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public int getDuracionMinutos() { return duracionMinutos; }
    public String getGenero() { return genero; }
}
