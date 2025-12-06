package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.Academico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcademicoDAO {

    // INSERT
    public Academico insert(int studentId, Academico a) {
        String sql = "INSERT INTO academico (student_id, programa, semestre_actual, promedio_acumulado) " +
                "VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setString(2, a.getPrograma());
            ps.setInt(3, a.getSemestreActual());
            ps.setDouble(4, a.getPromedioAcumulado());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                a.setId(rs.getInt("id"));
                a.setStudentId(studentId);
                return a;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // UPDATE
    public Academico update(Academico a) {
        String sql = "UPDATE academico SET programa=?, semestre_actual=?, promedio_acumulado=? WHERE id=?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getPrograma());
            ps.setInt(2, a.getSemestreActual());
            ps.setDouble(3, a.getPromedioAcumulado());
            ps.setInt(4, a.getId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                return a;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // SELECT BY ID
    public Academico findById(int id) {
        String sql = "SELECT * FROM academico WHERE id = ?";
        Academico a = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                a = new Academico();
                a.setId(rs.getInt("id"));
                a.setStudentId(rs.getInt("student_id"));
                a.setPrograma(rs.getString("programa"));
                a.setSemestreActual(rs.getInt("semestre_actual"));
                a.setPromedioAcumulado(rs.getDouble("promedio_acumulado"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return a;
    }

    // SELECT ALL
    public List<Academico> findAll() {
        List<Academico> list = new ArrayList<>();
        String sql = "SELECT * FROM academico";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Academico a = new Academico();
                a.setId(rs.getInt("id"));
                a.setStudentId(rs.getInt("student_id"));
                a.setPrograma(rs.getString("programa"));
                a.setSemestreActual(rs.getInt("semestre_actual"));
                a.setPromedioAcumulado(rs.getDouble("promedio_acumulado"));
                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }



    // DELETE
    public boolean delete(int id) {
        String sql = "DELETE FROM academico WHERE id=?";

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
