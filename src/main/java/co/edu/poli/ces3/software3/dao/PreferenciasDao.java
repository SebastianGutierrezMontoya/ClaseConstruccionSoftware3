package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.Preferencias;

import java.sql.*;

public class PreferenciasDao {

    private ActividadesExtracurricularesDao actividadesDao = new ActividadesExtracurricularesDao();
    private NotificacionesDao notificacionesDao = new NotificacionesDao();

    // INSERT — crea todo el árbol completo
    public Preferencias insert(int studentId, Preferencias pref) {
        String sql = "INSERT INTO preferencias (student_id, modalidad_estudio) VALUES (?, ?) RETURNING id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

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
                    notificacionesDao.insert(pref.getNotificaciones());
                }

                return findById(studentId); // return fully reloaded tree
            }

        } catch (Exception e) { e.printStackTrace(); }

        return null;
    }

    // UPDATE — actualiza preferencias, actividades y notificaciones
//    public Preferencias update(Preferencias pref) {
//        String sql = "UPDATE preferencias SET modalidad_estudio = ? WHERE id = ?";
//
//        try (Connection con = DatabaseConnection.getConnection();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setString(1, pref.getModalidadEstudio());
//            ps.setInt(2, pref.getId());
//
//            int rows = ps.executeUpdate();
//            if (rows > 0) {
//
//                // Replace activities
//                actividadesDao.delete(pref.getId());
//                if (pref.getActividadesExtracurriculares() != null) {
//                    actividadesDao.insert(pref.getId(), pref.getActividadesExtracurriculares());
//                }
//
//                // Update notifications
//                if (pref.getNotificaciones() != null) {
//                    pref.getNotificaciones().setPreferenciasId(pref.getId());
//                    notificacionesDao.update(pref.getId(), pref.getNotificaciones());
//                }
//
//                return findById(pref.getStudentId());
//            }
//
//        } catch (Exception e) { e.printStackTrace(); }
//
//        return null;
//    }

    // GET — obtener todo el árbol
    public Preferencias findById(int studentId) {
        String sql = "SELECT * FROM preferencias WHERE student_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

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

                return pref;
            }

        } catch (Exception e) { e.printStackTrace(); }

        return null;
    }


    public Preferencias update(Preferencias pref) {

        String sqlUpdate = """
                UPDATE preferencias 
                SET modalidad_estudio = ?
                WHERE student_id = ?
                RETURNING id
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sqlUpdate)) {

            ps.setString(1, pref.getModalidadEstudio());
            ps.setInt(2, pref.getStudentId());

            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            int prefId = rs.getInt("id");
            pref.setId(prefId);

            // Eliminar actividades actuales y reemplazar
            actividadesDao.delete(prefId);
            actividadesDao.insert(prefId, pref.getActividadesExtracurriculares());

            // Actualizar notificaciones
            notificacionesDao.update(prefId, pref.getNotificaciones());

            return findById(pref.getStudentId());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}