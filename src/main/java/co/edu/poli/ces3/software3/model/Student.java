package co.edu.poli.ces3.software3.model;

public class Student {

    private int id;
    private String nombreCompleto;
    private int edad;
    private String correo;
    private String telefono;
    private String ciudadResidencia;


    private Academico academico;
    private Preferencias preferencias;

    public Student() {}

    public Student(int id,String nombreCompleto, int edad, String correo, String telefono,
                   String ciudadResidencia, Academico academico, Preferencias preferencias) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.correo = correo;
        this.telefono = telefono;
        this.ciudadResidencia = ciudadResidencia;
        this.academico = academico;
        this.preferencias = preferencias;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public Academico getAcademico() {
        return academico;
    }

    public void setAcademico(Academico academico) {
        this.academico = academico;
    }

    public Preferencias getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(Preferencias preferencias) {
        this.preferencias = preferencias;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}