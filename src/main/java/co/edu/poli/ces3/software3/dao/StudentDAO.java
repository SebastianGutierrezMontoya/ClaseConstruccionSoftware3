package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // INSERT
    public boolean insert(Student s) {
        String sql = "INSERT INTO student (nombre_completo, edad, correo, telefono, ciudad_residencia) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, s.getNombreCompleto());
            stmt.setInt(2, s.getEdad());
            stmt.setString(3, s.getCorreo());
            stmt.setString(4, s.getTelefono());
            stmt.setString(5, s.getCiudadResidencia());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                s.setId(rs.getInt(1));
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    // UPDATE
    public boolean update(Student s) {
        String sql = "UPDATE student SET nombre_completo=?, edad=?, correo=?, telefono=?, ciudad_residencia=? " +
                "WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getNombreCompleto());
            stmt.setInt(2, s.getEdad());
            stmt.setString(3, s.getCorreo());
            stmt.setString(4, s.getTelefono());
            stmt.setString(5, s.getCiudadResidencia());
            stmt.setInt(6, s.getId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
