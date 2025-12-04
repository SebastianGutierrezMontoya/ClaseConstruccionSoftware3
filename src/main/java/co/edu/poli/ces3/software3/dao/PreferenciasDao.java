package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.Notificaciones;
import co.edu.poli.ces3.software3.model.Preferencias;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PreferenciasDao {

    // Obtener preferencias
    public Preferencias getByStudent(int idStudent) {
        String sql = "SELECT * FROM preferencias WHERE id_student = ?";
        Preferencias pref = null;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idStudent);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pref = new Preferencias();

                pref.setModalidadEstudio(rs.getString("modalidad_estudio"));
                pref.setActividadesExtracurriculares((String[]) rs.getArray("actividades_extracurriculares").getArray());

                Notificaciones notif = new Notificaciones();
                notif.setEmail(rs.getBoolean("email_notif"));
                notif.setSms(rs.getBoolean("sms_notif"));
                notif.setApp(rs.getBoolean("app_notif"));

                pref.setNotificaciones(notif);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pref;
    }

    // Insertar preferencias
    public boolean insert(int idStudent, Preferencias pref) {
        String sql = """
                INSERT INTO preferencias
                (id_student, modalidad_estudio, actividades_extracurriculares, email_notif, sms_notif, app_notif)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idStudent);
            ps.setString(2, pref.getModalidadEstudio());
            ps.setArray(3, con.createArrayOf("TEXT", pref.getActividadesExtracurriculares()));
            ps.setBoolean(4, pref.getNotificaciones().isEmail());
            ps.setBoolean(5, pref.getNotificaciones().isSms());
            ps.setBoolean(6, pref.getNotificaciones().isApp());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Actualizar preferencias
    public boolean update(int idStudent, Preferencias pref) {
        String sql = """
                UPDATE preferencias SET
                modalidad_estudio = ?,
                actividades_extracurriculares = ?,
                email_notif = ?,
                sms_notif = ?,
                app_notif = ?
                WHERE id_student = ?
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pref.getModalidadEstudio());
            ps.setArray(2, con.createArrayOf("TEXT", pref.getActividadesExtracurriculares()));
            ps.setBoolean(3, pref.getNotificaciones().isEmail());
            ps.setBoolean(4, pref.getNotificaciones().isSms());
            ps.setBoolean(5, pref.getNotificaciones().isApp());
            ps.setInt(6, idStudent);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Eliminar preferencias
    public boolean delete(int idStudent) {
        String sql = "DELETE FROM preferencias WHERE id_student = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idStudent);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
