package co.edu.poli.ces3.software3.model;

public class Preferencias {
    private String modalidadEstudio;
    private String[] actividadesExtracurriculares;
    private Notificaciones notificaciones;

    public String getModalidadEstudio() {
        return modalidadEstudio;
    }

    public void setModalidadEstudio(String modalidadEstudio) {
        this.modalidadEstudio = modalidadEstudio;
    }

    public String[] getActividadesExtracurriculares() {
        return actividadesExtracurriculares;
    }

    public void setActividadesExtracurriculares(String[] actividadesExtracurriculares) {
        this.actividadesExtracurriculares = actividadesExtracurriculares;
    }

    public Notificaciones getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(Notificaciones notificaciones) {
        this.notificaciones = notificaciones;
    }
}
