package co.edu.poli.ces3.software3.controller;

import co.edu.poli.ces3.software3.dao.DetalleMateriasDao;
import co.edu.poli.ces3.software3.model.DetalleMateria;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "DetalleMateriasApi", value = "/api/detallematerias")
public class DetalleMateriasApi extends HttpServlet {

    private DetalleMateriasDao dao = new DetalleMateriasDao();
    private Gson gson = new Gson();

    // GET → lista de detalles por idAcademico
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        int idAcademico = Integer.parseInt(req.getParameter("idAcademico"));

        List<DetalleMateria> detalles = dao.getDetallesByAcademico(idAcademico);

        resp.getWriter().print(gson.toJson(detalles));
    }

    // POST → insertar detalle
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        BufferedReader reader = req.getReader();
        JsonObject body = gson.fromJson(reader, JsonObject.class);

        int idAcademico = body.get("idAcademico").getAsInt();
        DetalleMateria materia = gson.fromJson(body.get("detalleMateria"), DetalleMateria.class);

        boolean ok = dao.insertDetalle(idAcademico, materia);

        JsonObject json = new JsonObject();
        json.addProperty("success", ok);

        resp.getWriter().print(json.toString());
    }

    // PUT → actualizar detalle
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        BufferedReader reader = req.getReader();
        JsonObject body = gson.fromJson(reader, JsonObject.class);

        int idAcademico = body.get("idAcademico").getAsInt();
        DetalleMateria materia = gson.fromJson(body.get("detalleMateria"), DetalleMateria.class);

        boolean ok = dao.updateDetalle(idAcademico, materia);

        JsonObject json = new JsonObject();
        json.addProperty("success", ok);

        resp.getWriter().print(json.toString());
    }

    // DELETE → eliminar por nombre
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int idAcademico = Integer.parseInt(req.getParameter("idAcademico"));
        String nombreMateria = req.getParameter("nombre");

        boolean ok = dao.deleteDetalle(idAcademico, nombreMateria);

        JsonObject json = new JsonObject();
        json.addProperty("success", ok);

        resp.getWriter().print(json.toString());
    }
}
