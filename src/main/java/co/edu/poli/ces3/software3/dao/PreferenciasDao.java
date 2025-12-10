package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.Notificaciones;
import co.edu.poli.ces3.software3.model.Preferencias;

import java.sql.*;
import java.util.List;

public class PreferenciasDao extends DatabaseConnection implements CRUD<Preferencias, Integer> {

    private ActividadesExtracurricularesDao actividadesDao = new ActividadesExtracurricularesDao();
    private NotificacionesDao notificacionesDao = new NotificacionesDao();


    // INSERT — crea todo el árbol completo
    @Override
    public Preferencias insert(Integer studentId, Preferencias pref) {
        String sql = "INSERT INTO preferencias (student_id, modalidad_estudio) VALUES (?, ?) RETURNING id";
        Connection conn = super.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
            ps.setString(2, pref.getModalidadEstudio());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int prefId = rs.getInt("id");
                pref.setId(prefId);
                pref.setStudentId(studentId);

                // Insert activities
                if (pref.getActividadesExtracurriculares() != null) {
                    actividadesDao.insert(prefId, pref.getActividadesExtracurriculares());
                }

                // Insert notifications
                if (pref.getNotificaciones() != null) {
                    pref.getNotificaciones().setPreferenciasId(prefId);
                    Notificaciones nof = notificacionesDao.insert(null,pref.getNotificaciones());
                }

                return findById(studentId); // return fully reloaded tree
            }

        } catch (Exception e) { e.printStackTrace(); }
    finally {
        super.closeConnection(conn);
    }

        return null;
    }


    // GET — obtener todo el árbol
    @Override
    public Preferencias findById(Integer studentId) {
        String sql = "SELECT * FROM preferencias WHERE student_id = ?";
        Connection conn = super.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                Preferencias pref = new Preferencias();
                pref.setId(rs.getInt("id"));
                pref.setStudentId(studentId);
                pref.setModalidadEstudio(rs.getString("modalidad_estudio"));

                // load activities
                pref.setActividadesExtracurriculares(
                        actividadesDao.findById(pref.getId())
                );

                // load notifications
                pref.setNotificaciones(
                        notificacionesDao.findById(pref.getId())
                );
                System.out.println(pref.getModalidadEstudio());
                return pref;
            }

        } catch (Exception e) { e.printStackTrace(); }
        finally {
            super.closeConnection(conn);
        }

        return null;
    }

    @Override
    public boolean updatePartial(Preferencias pref) {
        String sql = "UPDATE preferencias SET "
                + "modalidad_estudio = COALESCE(?, modalidad_estudio) "
                + "WHERE student_id = ?";
        Connection conn = super.getConnection();
        try (
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Campo parcial
            stmt.setString(1, pref.getModalidadEstudio());
            stmt.setInt(2, pref.getStudentId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            super.closeConnection(conn);
        }
    }

    @Override
    public Preferencias update(Integer none, Preferencias pref) {

        String sqlUpdate = """
                UPDATE preferencias 
                SET modalidad_estudio = ?
                WHERE student_id = ?
                RETURNING id
                """;

        Connection conn = super.getConnection();

        try (
             PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {

            ps.setString(1, pref.getModalidadEstudio());
            ps.setInt(2, pref.getStudentId());

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            int prefId = rs.getInt("id");
            pref.setId(prefId);

            // Eliminar actividades actuales y reemplazar
            actividadesDao.delete(prefId, null);
            actividadesDao.insert(prefId, pref.getActividadesExtracurriculares());

            // Actualizar notificaciones
            notificacionesDao.update(prefId, pref.getNotificaciones());

            return findById(pref.getStudentId());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            super.closeConnection(conn);
    }
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public List<Preferencias> findAll() {
        return List.of();
    }

}