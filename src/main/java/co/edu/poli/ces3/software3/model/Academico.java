package co.edu.poli.ces3.software3.model;

import java.util.List;

public class Academico {


    private int id;
    private String programa;
    private int semestreActual;
    private double promedioAcumulado;
    private int StudentId;

    public Academico() {

    }

    public Academico(int id, String programa, int semestreActual, double promedioAcumulado) {

        this.id = id;
        this.programa = programa;
        this.semestreActual = semestreActual;
        this.promedioAcumulado = promedioAcumulado;

    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public int getSemestreActual() {
        return semestreActual;
    }

    public void setSemestreActual(int semestreActual) {
        this.semestreActual = semestreActual;
    }

    public double getPromedioAcumulado() {
        return promedioAcumulado;
    }

    public void setPromedioAcumulado(double promedioAcumulado) {
        this.promedioAcumulado = promedioAcumulado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }
}