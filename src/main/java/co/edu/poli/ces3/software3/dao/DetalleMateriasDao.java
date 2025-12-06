package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.controller.DetalleMateriasApi;
import co.edu.poli.ces3.software3.model.DetalleMateria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleMateriasDao {

    // Obtener lista de detalles por id_academico
    public List<DetalleMateria> findById(int idAcademico) {
        List<DetalleMateria> lista = new ArrayList<>();

        String sql = "SELECT * FROM detalle_materia WHERE academico_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAcademico);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DetalleMateria dm = new DetalleMateria();
                dm.setId(rs.getInt("id"));
                dm.setAcademicoId(rs.getInt("academico_id"));
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
    public DetalleMateria insert(int idAcademico, DetalleMateria dm) {
        String sql = "INSERT INTO detalle_materia (academico_id, nombre, creditos, docente, estado) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAcademico);
            ps.setString(2, dm.getNombre());
            ps.setInt(3, dm.getCreditos());
            ps.setString(4, dm.getDocente());
            ps.setString(5, dm.getEstado());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dm.setId(rs.getInt("id"));
                dm.setAcademicoId(idAcademico);
                return dm;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // UPDATE → retorna DetalleMateria actualizado o null
    public DetalleMateria update(int idAcademico, DetalleMateria dm) {
        String sql = "UPDATE detalle_materia SET creditos=?, docente=?, estado=?, nombre=? " +
                "WHERE academico_id=? AND id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, dm.getCreditos());
            ps.setString(2, dm.getDocente());
            ps.setString(3, dm.getEstado());
            ps.setString(4, dm.getNombre());
            ps.setInt(5, idAcademico);
            ps.setInt(6, dm.getId());


            int rows = ps.executeUpdate();
            if (rows > 0) {
                dm.setAcademicoId(idAcademico);
                return dm;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // Eliminar un detalle por nombre
    public boolean delete(int idAcademico, int id) {
        String sql = "DELETE FROM detalle_materia WHERE academico_id = ? AND id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idAcademico);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
