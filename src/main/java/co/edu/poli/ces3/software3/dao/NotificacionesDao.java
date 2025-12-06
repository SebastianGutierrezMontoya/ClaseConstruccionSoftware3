package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.Notificaciones;

import java.sql.*;

public class NotificacionesDao {

    public Notificaciones insert(Notificaciones n) throws SQLException {
        String sql = "INSERT INTO notificaciones (preferencias_id, email, sms, app) " +
                "VALUES (?, ?, ?, ?) RETURNING id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

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

        return null;
    }



    public Notificaciones getByPreferenciasId(int prefId) throws SQLException {
        String sql = "SELECT * FROM notificaciones WHERE preferencias_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

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

        return null;
    }


    public void update(int prefId, Notificaciones n) throws SQLException {

        String sql = """
                UPDATE notificaciones
                SET email = ?, sms = ?, app = ?
                WHERE preferencias_id = ?
                """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, n.isEmail());
            ps.setBoolean(2, n.isSms());
            ps.setBoolean(3, n.isApp());
            ps.setInt(4, prefId);
            ps.executeUpdate();
        }
    }

}
