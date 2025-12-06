package co.edu.poli.ces3.software3.model;

import java.util.List;

public class StudentFull {

    private Student student;
    private Academico academico;
    private List<DetalleMateria> materias;
    private Preferencias preferencias;
    private List<String> actividadesExtracurriculares;
    private Notificaciones notificaciones;

    public StudentFull() {}

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Academico getAcademico() { return academico; }
    public void setAcademico(Academico academico) { this.academico = academico; }

    public List<DetalleMateria> getMaterias() { return materias; }
    public void setMaterias(List<DetalleMateria> materias) { this.materias = materias; }

    public Preferencias getPreferencias() { return preferencias; }
    public void setPreferencias(Preferencias preferencias) { this.preferencias = preferencias; }

    public List<String> getActividadesExtracurriculares() { return actividadesExtracurriculares; }
    public void setActividadesExtracurriculares(List<String> actividadesExtracurriculares) {
        this.actividadesExtracurriculares = actividadesExtracurriculares;
    }

    public Notificaciones getNotificaciones() { return notificaciones; }
    public void setNotificaciones(Notificaciones notificaciones) { this.notificaciones = notificaciones; }
}
