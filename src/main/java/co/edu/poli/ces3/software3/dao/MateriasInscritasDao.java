package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MateriasInscritasDao {

    // Obtener materias inscritas por id_academico
    public List<String> getMateriasByAcademico(int idAcademico) {
        List<String> materias = new ArrayList<>();

        String sql = "SELECT materia FROM materias_inscritas WHERE id_academico = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAcademico);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                materias.add(rs.getString("materia"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return materias;
    }

    // Insertar materias
    public boolean insertMateria(int idAcademico, String materia) {
        String sql = "INSERT INTO materias_inscritas (id_academico, materia) VALUES (?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAcademico);
            ps.setString(2, materia);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Actualizar materias → en este caso eliminamos todas y las volvemos a insertar
    public boolean updateMaterias(int idAcademico, List<String> materias) {

        String deleteSQL = "DELETE FROM materias_inscritas WHERE id_academico = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement deletePS = con.prepareStatement(deleteSQL)) {

            deletePS.setInt(1, idAcademico);
            deletePS.executeUpdate();

            // Insertar nuevamente
            for (String materia : materias) {
                insertMateria(idAcademico, materia);
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Eliminar materia individual
    public boolean deleteMateria(int idAcademico, String materia) {
        String sql = "DELETE FROM materias_inscritas WHERE id_academico = ? AND materia = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAcademico);
            ps.setString(2, materia);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
