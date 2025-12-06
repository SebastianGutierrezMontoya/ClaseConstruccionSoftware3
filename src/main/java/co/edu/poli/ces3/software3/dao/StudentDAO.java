package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // INSERT
    public Student insert(Student s) {
        String sql = "INSERT INTO student (nombre_completo, edad, correo, telefono, ciudad_residencia) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getNombreCompleto());
            ps.setInt(2, s.getEdad());
            ps.setString(3, s.getCorreo());
            ps.setString(4, s.getTelefono());
            ps.setString(5, s.getCiudadResidencia());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int newId = rs.getInt("id");
                s.setId(newId);
                return s;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // UPDATE → retorna Student actualizado o null si fallo
    public Student update(Student s) {
        String sql = "UPDATE student SET nombre_completo=?, edad=?, correo=?, telefono=?, ciudad_residencia=? " +
                "WHERE id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getNombreCompleto());
            ps.setInt(2, s.getEdad());
            ps.setString(3, s.getCorreo());
            ps.setString(4, s.getTelefono());
            ps.setString(5, s.getCiudadResidencia());
            ps.setInt(6, s.getId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                return s;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // SELECT BY ID
    public Student findById(int id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        Student s = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                s = new Student();
                s.setId(rs.getInt("id"));
                s.setNombreCompleto(rs.getString("nombre_completo"));
                s.setEdad(rs.getInt("edad"));
                s.setCorreo(rs.getString("correo"));
                s.setTelefono(rs.getString("telefono"));
                s.setCiudadResidencia(rs.getString("ciudad_residencia"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s;
    }

    // SELECT ALL
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setNombreCompleto(rs.getString("nombre_completo"));
                s.setEdad(rs.getInt("edad"));
                s.setCorreo(rs.getString("correo"));
                s.setTelefono(rs.getString("telefono"));
                s.setCiudadResidencia(rs.getString("ciudad_residencia"));
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM student WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
