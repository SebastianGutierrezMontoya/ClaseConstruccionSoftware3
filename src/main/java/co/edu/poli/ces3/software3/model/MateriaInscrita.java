package co.edu.poli.ces3.software3.model;

public class MateriaInscrita {
    private int id;
    private int academicoId;
    private String nombre;

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
}