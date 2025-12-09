package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.Academico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcademicoDAO extends DatabaseConnection implements CRUD<Academico, Integer> {


    // INSERT
    @Override
    public Academico insert(Integer studentId, Academico a) {
        String sql = "INSERT INTO academico (student_id, programa, semestre_actual, promedio_acumulado) " +
                "VALUES (?, ?, ?, ?) RETURNING id";
        Connection conn = super.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
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
        }finally {
            super.closeConnection(conn);
        }
        return null;
    }

    // UPDATE
    @Override
    public Academico update(Integer none, Academico a) {
        String sql = "UPDATE academico SET programa=?, semestre_actual=?, promedio_acumulado=? WHERE id=?";
        Connection conn = super.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
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
        }finally {
            super.closeConnection(conn);
        }
        return null;
    }

    // SELECT BY ID
    @Override
    public Academico findById(Integer id) {
        String sql = "SELECT * FROM academico WHERE id = ?";
        Academico a = null;
        Connection conn = super.getConnection();
        try (
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
        }finally {
            super.closeConnection(conn);
        }

        return a;
    }

    // SELECT ALL
    @Override
    public List<Academico> findAll() {
        List<Academico> list = new ArrayList<>();
        String sql = "SELECT * FROM academico";
        Connection conn = super.getConnection();
        try (
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
        }finally {
            super.closeConnection(conn);
        }

        return list;
    }



    // DELETE
    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM academico WHERE id=?";
        Connection conn = super.getConnection();
        try (
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            super.closeConnection(conn);
        }
    }


    @Override
    public boolean updatePartial(Academico a) {
        String sql = "UPDATE academico SET programa = ?, semestre_actual = ?, promedio_acumulado = ?, student_id = ? WHERE id = ?";

        Connection conn = super.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, a.getPrograma());
            ps.setInt(2, a.getSemestreActual());
            ps.setDouble(3, a.getPromedioAcumulado());
            ps.setInt(4, a.getStudentId());
            ps.setInt(5, a.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            super.closeConnection(conn);
        }
    }
}
