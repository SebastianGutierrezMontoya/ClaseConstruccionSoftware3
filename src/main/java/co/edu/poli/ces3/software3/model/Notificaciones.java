package co.edu.poli.ces3.software3.model;

public class Notificaciones {
    private int id;
    private int preferenciasId;
    private boolean email;
    private boolean sms;
    private boolean app;

    public Notificaciones() {

    }

    public Notificaciones(boolean email, boolean sms, boolean app) {
        this.email = email;
        this.sms = sms;
        this.app = app;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPreferenciasId() { return preferenciasId; }
    public void setPreferenciasId(int preferenciasId) { this.preferenciasId = preferenciasId; }

    public boolean isEmail() { return email; }
    public void setEmail(boolean email) { this.email = email; }

    public boolean isSms() { return sms; }
    public void setSms(boolean sms) { this.sms = sms; }

    public boolean isApp() { return app; }
    public void setApp(boolean app) { this.app = app; }
}

