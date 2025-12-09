package co.edu.poli.ces3.software3.dao;

import co.edu.poli.ces3.software3.config.DatabaseConnection;
import co.edu.poli.ces3.software3.model.Actividades;
import co.edu.poli.ces3.software3.model.DetalleMateria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ActividadesExtracurricularesDao extends DatabaseConnection implements CRUD2<Actividades, Integer> {


    @Override
    public Actividades insert(Integer preferenciasId, Actividades actividades) {
        String sql = "INSERT INTO actividades_extracurriculares (preferencias_id, actividad) VALUES (?, ?)";

        Connection conn = super.getConnection();
        try (
                PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, preferenciasId);
                ps.setString(2, actividades.getActividad());


            ps.executeQuery();

            return actividades;

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            super.closeConnection(conn);
        }
        return null;
    }

    @Override
    public void insert(Integer preferenciasId, List<Actividades> actividades){
        String sql = "INSERT INTO actividades_extracurriculares (preferencias_id, actividad) VALUES (?, ?)";

        Connection conn = super.getConnection();
        try (
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (Actividades act : actividades) {
                ps.setInt(1, preferenciasId);
                ps.setString(2, act.getActividad());
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
    public Actividades update(Integer integer, Actividades actividades) {
        return null;
    }


    @Override
    public List<Actividades> findById(Integer prefId) {
        String sql = "SELECT id, preferencias_id, actividad "+
        "FROM actividades_extracurriculares "+
        "WHERE preferencias_id = ?";

        List<Actividades> list = new ArrayList<>();

        Connection conn = super.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, prefId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Actividades act = new Actividades(
                        rs.getInt("preferencias_id"),
                        rs.getString("actividad")
                );
                list.add(act);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.closeConnection(conn);
        }

        return list;
    }

    @Override
    public boolean updatePartial(Actividades actividades) {
        return false;
    }


    @Override
    public boolean delete(Integer prefId, Integer none) {
        String sql = "DELETE FROM actividades_extracurriculares WHERE preferencias_id = ?";

        Connection conn = super.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, prefId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            super.closeConnection(conn);
        }
    }

    @Override
    public List<Actividades> findAll(Integer none) {
        return List.of();
    }


}
