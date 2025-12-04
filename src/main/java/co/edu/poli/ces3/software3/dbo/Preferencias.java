package co.edu.poli.ces3.software3.dbo;
import co.edu.poli.ces3.software3.dbo.Notificaciones;

public class Preferencias {

    private String modalidadEstudio;
    private String[] actividadesExtracurriculares;
    private Notificaciones notificaciones;

    public Preferencias(String modalidadEstudio, String[] actividadesExtracurriculares,
                        Notificaciones notificaciones) {
        this.modalidadEstudio = modalidadEstudio;
        this.actividadesExtracurriculares = actividadesExtracurriculares;
        this.notificaciones = notificaciones;
    }

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