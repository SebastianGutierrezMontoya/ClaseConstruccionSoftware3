package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.Academico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcademicoDAO {

    // INSERT
    public boolean insert(Academico a, int studentId) {
        String sql = "INSERT INTO academico (student_id, programa, semestre_actual, promedio_acumulado) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, studentId);
            stmt.setString(2, a.getPrograma());
            stmt.setInt(3, a.getSemestreActual());
            stmt.setDouble(4, a.getPromedioAcumulado());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                a.setId(rs.getInt(1));
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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

    // UPDATE
    public boolean update(Academico a) {
        String sql = "UPDATE academico SET programa=?, semestre_actual=?, promedio_acumulado=? " +
                "WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, a.getPrograma());
            stmt.setInt(2, a.getSemestreActual());
            stmt.setDouble(3, a.getPromedioAcumulado());
            stmt.setInt(4, a.getId());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
