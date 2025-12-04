package co.edu.poli.ces3.software3.model;

public class Notificaciones {

    private boolean email;
    private boolean sms;
    private boolean app;

    public Notificaciones(){}

    public Notificaciones(boolean email, boolean sms, boolean app) {
        this.email = email;
        this.sms = sms;
        this.app = app;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean sms) {
        this.sms = sms;
    }

    public boolean isApp() {
        return app;
    }

    public void setApp(boolean app) {
        this.app = app;
    }
}

