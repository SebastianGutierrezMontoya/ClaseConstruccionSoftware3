package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadesExtracurricularesDao {

    public void insert(int preferenciasId, List<String> actividades) throws SQLException {
        String sql = "INSERT INTO actividades_extracurriculares (preferencias_id, actividad) VALUES (?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (String act : actividades) {
                ps.setInt(1, preferenciasId);
                ps.setString(2, act);
                ps.addBatch();
            }

            ps.executeBatch();
        }
    }

    public List<String> findById(int prefId) throws SQLException {
        String sql = "SELECT actividad FROM actividades_extracurriculares WHERE preferencias_id = ?";
        List<String> list = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, prefId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("actividad"));
            }
        }

        return list;
    }

    public void delete(int prefId) throws SQLException {
        String sql = "DELETE FROM actividades_extracurriculares WHERE preferencias_id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, prefId);
            ps.executeUpdate();
        }
    }


}
