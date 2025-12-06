package co.edu.poli.ces3.software3.model;

public class DetalleMateria {

    private int id;
    private int academicoId;
    private String nombre;
    private int creditos;
    private String docente;
    private String estado;

    public DetalleMateria() {}

    public DetalleMateria(int id, String nombre, int creditos, String docente, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.creditos = creditos;
        this.docente = docente;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAcademicoId() {
        return academicoId;
    }

    public void setAcademicoId(int academicoId) {
        this.academicoId = academicoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}