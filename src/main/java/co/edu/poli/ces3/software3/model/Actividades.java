package co.edu.poli.ces3.software3.model;

public class Actividades {

    int preferencias_id;
    String actividad;

    public Actividades() { }

    public Actividades(int preferenciasId, String actividad) {

        this.preferencias_id = preferenciasId;
        this.actividad = actividad;
    }




    public int getPreferenciasId() {
        return preferencias_id;
    }

    public void setPreferenciasId(int preferencias_id) {
        this.preferencias_id = preferencias_id;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
}
