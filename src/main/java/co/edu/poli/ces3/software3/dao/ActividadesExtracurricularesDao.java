package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.DetalleMateria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadesExtracurricularesDao extends DatabaseConnection implements CRUD2<DetalleMateria, Integer> {

    @Override
    public DetalleMateria insert(Integer integer, DetalleMateria detalleMateria) {
        return null;
    }

    @Override
    public void insert(Integer preferenciasId, List<String> actividades){
        String sql = "INSERT INTO actividades_extracurriculares (preferencias_id, actividad) VALUES (?, ?)";

        Connection conn = super.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (String act : actividades) {
                ps.setInt(1, preferenciasId);
                ps.setString(2, act);
                ps.addBatch();
            }

            ps.executeBatch();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            super.closeConnection(conn);
        }
    }

    @Override
    public DetalleMateria update(Integer integer, DetalleMateria detalleMateria) {
        return null;
    }

    @Override
    public List<String> findById(Integer prefId){
        String sql = "SELECT actividad FROM actividades_extracurriculares WHERE preferencias_id = ?";
        List<String> list = new ArrayList<>();

        Connection conn = super.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, prefId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("actividad"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            super.closeConnection(conn);
        }

        return list;
    }

    @Override
    public List<DetalleMateria> findById(Integer integer, DetalleMateria detalleMateria) {
        return List.of();
    }

    @Override
    public boolean delete(Integer prefId, Integer none) {
        String sql = "DELETE FROM actividades_extracurriculares WHERE preferencias_id = ?";

        Connection conn = super.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, prefId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            super.closeConnection(conn);
        }
        return false;
    }

    @Override
    public List<DetalleMateria> findAll() {
        return List.of();
    }


}
