package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends DatabaseConnection implements CRUD<Student, Integer> {

    // INSERT
    @Override
    public Student insert(Integer none, Student s) {
        String sql = "INSERT INTO student (nombre_completo, edad, correo, telefono, ciudad_residencia) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";
        Connection conn = super.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
        }finally {
            super.closeConnection(conn);
        }
        return null;
    }


    // UPDATE → retorna Student actualizado o null si fallo
    @Override
    public Student update(Integer none,Student s) {
        String sql = "UPDATE student SET nombre_completo=?, edad=?, correo=?, telefono=?, ciudad_residencia=? " +
                "WHERE id=?";
        Connection conn = super.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {

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
        } finally {
            super.closeConnection(conn);
        }
        return null;
    }


    // SELECT BY ID
    @Override
    public Student findById(Integer id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        Student s = null;
        Connection conn = super.getConnection();
        try (
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
        }finally {
            super.closeConnection(conn);
        }

        return s;
    }

    // SELECT ALL
    @Override
    public List<Student> findAll() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM student";

        Connection conn = super.getConnection();
        try (
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
        }finally {
            super.closeConnection(conn);
        }

        return list;
    }


    // DELETE
    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM student WHERE id=?";

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



    public StudentFull getFull(int studentId) throws SQLException {

        StudentFull full = new StudentFull();

        // 1. Obtener datos base del estudiante
        Student student = findById(studentId);
        if (student == null) {
            return null;
        }
        full.setStudent(student);

        // 2. Obtener datos académicos
        AcademicoDAO academicoDAO = new AcademicoDAO();
        Academico academico = academicoDAO.findByStudentId(studentId);
        full.setAcademico(academico);

        // 3. Obtener las materias del académico
        if (academico != null) {
            DetalleMateriasDao dmDAO = new DetalleMateriasDao();
            List<DetalleMateria> materias = dmDAO.findAll(academico.getId());
            full.setMaterias(materias);
        }

         //4. Obtener preferencias
        PreferenciasDao prefDAO = new PreferenciasDao();
        Preferencias pref = prefDAO.findById(studentId);
        full.setPreferencias(pref);
//
//        // 5. Obtener actividades extracurriculares
//        if (pref != null) {
//            ActividadesExtracurricularesDao actDAO = new ActividadesExtracurricularesDao();
//            List<Actividades> actividades = actDAO.findById(pref.getId());
//            full.setActividadesExtracurriculares(actividades);
//
//            // 6. Obtener notificaciones
//            NotificacionesDao notDAO = new NotificacionesDao();
//            Notificaciones notif = notDAO.findById(pref.getId());
//            full.setNotificaciones(notif);
//        }

        return full;
    }



    @Override
    public boolean updatePartial(Student s) {
        String sql = "UPDATE student SET nombre_completo=?, edad=?, correo=?, telefono=?, ciudad_residencia=? WHERE id=?";
        Connection conn = super.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, s.getNombreCompleto());
            ps.setInt(2, s.getEdad());
            ps.setString(3, s.getCorreo());
            ps.setString(4, s.getTelefono());
            ps.setString(5, s.getCiudadResidencia());
            ps.setInt(6, s.getId());

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            super.closeConnection(conn);
        }
    }
}
