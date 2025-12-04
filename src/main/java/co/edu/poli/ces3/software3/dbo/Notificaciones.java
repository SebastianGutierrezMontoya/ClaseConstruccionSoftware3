package co.edu.poli.ces3.software3.dbo;

public class Notificaciones {

    private boolean email;
    private boolean sms;
    private boolean app;

    public Notificaciones(boolean email, boolean sms, boolean app) {
        this.email = email;
        this.sms = sms;
        this.app = app;
    }
}

