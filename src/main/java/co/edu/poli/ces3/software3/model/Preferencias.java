package co.edu.poli.ces3.software3.model;

import java.util.List;

public class Preferencias {
    private int id;
    private int studentId;
    private String modalidadEstudio;

    private List<Actividades> actividadesExtracurriculares;
    private Notificaciones notificaciones;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getModalidadEstudio() { return modalidadEstudio; }
    public void setModalidadEstudio(String modalidadEstudio) { this.modalidadEstudio = modalidadEstudio; }

    public List<Actividades> getActividadesExtracurriculares() { return actividadesExtracurriculares; }
    public void setActividadesExtracurriculares(List<Actividades> actividadesExtracurriculares) {
        this.actividadesExtracurriculares = actividadesExtracurriculares;
    }

    public Notificaciones getNotificaciones() { return notificaciones; }
    public void setNotificaciones(Notificaciones notificaciones) {
        this.notificaciones = notificaciones;
    }
}