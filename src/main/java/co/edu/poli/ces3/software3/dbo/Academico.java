package co.edu.poli.ces3.software3.dbo;

import java.util.List;

public class Academico {

    private String programa;
    private int semestreActual;
    private double promedioAcumulado;
    private List<String> materiasInscritas;
    private List<DetalleMateria> detalleMaterias;

    public Academico(String programa, int semestreActual, double promedioAcumulado,
                     List<String> materiasInscritas, List<DetalleMateria> detalleMaterias) {
        this.programa = programa;
        this.semestreActual = semestreActual;
        this.promedioAcumulado = promedioAcumulado;
        this.materiasInscritas = materiasInscritas;
        this.detalleMaterias = detalleMaterias;
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

    public List<String> getMateriasInscritas() {
        return materiasInscritas;
    }

    public void setMateriasInscritas(List<String> materiasInscritas) {
        this.materiasInscritas = materiasInscritas;
    }

    public List<DetalleMateria> getDetalleMaterias() {
        return detalleMaterias;
    }

    public void setDetalleMaterias(List<DetalleMateria> detalleMaterias) {
        this.detalleMaterias = detalleMaterias;
    }
}