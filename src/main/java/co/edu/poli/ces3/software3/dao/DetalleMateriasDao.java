package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.DetalleMateria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleMateriasDao {

    // Obtener lista de detalles por id_academico
    public List<DetalleMateria> getDetallesByAcademico(int idAcademico) {
        List<DetalleMateria> lista = new ArrayList<>();

        String sql = "SELECT * FROM detalle_materias WHERE id_academico = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAcademico);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DetalleMateria dm = new DetalleMateria();
                dm.setNombre(rs.getString("nombre"));
                dm.setCreditos(rs.getInt("creditos"));
                dm.setDocente(rs.getString("docente"));
                dm.setEstado(rs.getString("estado"));
                lista.add(dm);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Insertar un detalle
    public boolean insertDetalle(int idAcademico, DetalleMateria dm) {
        String sql = "INSERT INTO detalle_materias (id_academico, nombre, creditos, docente, estado) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAcademico);
            ps.setString(2, dm.getNombre());
            ps.setInt(3, dm.getCreditos());
            ps.setString(4, dm.getDocente());
            ps.setString(5, dm.getEstado());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Actualizar un detalle (por nombre de materia)
    public boolean updateDetalle(int idAcademico, DetalleMateria dm) {
        String sql = "UPDATE detalle_materias SET creditos = ?, docente = ?, estado = ? WHERE id_academico = ? AND nombre = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dm.getCreditos());
            ps.setString(2, dm.getDocente());
            ps.setString(3, dm.getEstado());
            ps.setInt(4, idAcademico);
            ps.setString(5, dm.getNombre());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Eliminar un detalle por nombre
    public boolean deleteDetalle(int idAcademico, String nombreMateria) {
        String sql = "DELETE FROM detalle_materias WHERE id_academico = ? AND nombre = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAcademico);
            ps.setString(2, nombreMateria);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
