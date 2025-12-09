package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.Notificaciones;

import java.sql.*;
import java.util.List;

public class NotificacionesDao extends DatabaseConnection implements CRUD<Notificaciones, Integer> {

    @Override
    public Notificaciones insert(Integer none,Notificaciones n){
        String sql = "INSERT INTO notificaciones (preferencias_id, email, sms, app) " +
                "VALUES (?, ?, ?, ?) RETURNING id";

        Connection conn = super.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, n.getPreferenciasId());
            ps.setBoolean(2, n.isEmail());
            ps.setBoolean(3, n.isSms());
            ps.setBoolean(4, n.isApp());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                n.setId(rs.getInt("id"));
                return n;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            super.closeConnection(conn);
        }

        return null;
    }


    @Override
    public Notificaciones findById(Integer prefId) {
        String sql = "SELECT * FROM notificaciones WHERE preferencias_id = ?";
        Connection conn = super.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, prefId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Notificaciones n = new Notificaciones();
                n.setId(rs.getInt("id"));
                n.setPreferenciasId(prefId);
                n.setEmail(rs.getBoolean("email"));
                n.setSms(rs.getBoolean("sms"));
                n.setApp(rs.getBoolean("app"));
                return n;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            super.closeConnection(conn);
        }

        return null;
    }

    @Override
    public Notificaciones update(Integer prefId, Notificaciones n) {

        String sql = """
                UPDATE notificaciones
                SET email = ?, sms = ?, app = ?
                WHERE preferencias_id = ?
                """;
        Connection conn = super.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, n.isEmail());
            ps.setBoolean(2, n.isSms());
            ps.setBoolean(3, n.isApp());
            ps.setInt(4, prefId);
            ps.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            super.closeConnection(conn);
        }
        return null;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public List<Notificaciones> findAll() {
        return List.of();
    }


}
