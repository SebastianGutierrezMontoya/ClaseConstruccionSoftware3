package co.edu.poli.ces3.software3.controller;

import co.edu.poli.ces3.software3.dao.DetalleMateriasDao;
import co.edu.poli.ces3.software3.model.DetalleMateria;
import co.edu.poli.ces3.software3.servlet.PatchServlet;
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
public class DetalleMateriasApi extends PatchServlet {

    private DetalleMateriasDao dao = new DetalleMateriasDao();
    private Gson gson = new Gson();

    // GET → lista de detalles por idAcademico
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        int idAcademico = Integer.parseInt(req.getParameter("idAcademico"));

        List<DetalleMateria> detalles = dao.findById(idAcademico, null);

        resp.getWriter().print(gson.toJson(detalles));
    }

    // POST → insertar detalle
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        BufferedReader reader = req.getReader();
        JsonObject body = gson.fromJson(reader, JsonObject.class);
        PrintWriter out = resp.getWriter();

        int idAcademico = body.get("idAcademico").getAsInt();
        DetalleMateria materia = gson.fromJson(body.get("detalleMateria"), DetalleMateria.class);

        //boolean ok = dao.insert(idAcademico, materia);
        DetalleMateria inserted = dao.insert(idAcademico, materia);

        if (inserted == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"No se pudo insertar\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            Gson gson = new Gson();
            out.println(gson.toJson(inserted));
        }

    }

    // PUT → actualizar detalle
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        BufferedReader reader = req.getReader();
        JsonObject body = gson.fromJson(reader, JsonObject.class);
        PrintWriter out = resp.getWriter();

        int idAcademico = body.get("idAcademico").getAsInt();
        DetalleMateria materia = gson.fromJson(body.get("detalleMateria"), DetalleMateria.class);

        DetalleMateria inserted = dao.update(idAcademico, materia);

        if (inserted == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"No se pudo insertar\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            Gson gson = new Gson();
            out.println(gson.toJson(inserted));
        }
    }

    // DELETE → eliminar por nombre
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int idAcademico = Integer.parseInt(req.getParameter("idAcademico"));
        int nombreMateria = Integer.parseInt(req.getParameter("id"));

        boolean ok = dao.delete(idAcademico, nombreMateria);

        JsonObject json = new JsonObject();
        json.addProperty("success", ok);

        resp.getWriter().print(json.toString());
    }



    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }
}
