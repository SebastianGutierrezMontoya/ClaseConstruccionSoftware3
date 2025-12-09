package co.edu.poli.ces3.software3.controller;

import co.edu.poli.ces3.software3.dao.DetalleMateriasDao;
import co.edu.poli.ces3.software3.model.DetalleMateria;
import co.edu.poli.ces3.software3.servlet.PatchServlet;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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


        List<DetalleMateria> detalles = dao.findAll(idAcademico);

        DetalleMateria resultado = null;

        if (req.getParameter("id") != null) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            resultado = detalles.stream()
                    .filter(d -> d.getId() == id)
                    .findFirst()
                    .orElse(null);

            if (resultado != null) {
                resp.getWriter().print(gson.toJson(resultado));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().print("{\"error\": \"Detalle no encontrado\"}");
            }
        } else {
            resp.getWriter().print(gson.toJson(detalles));
        }


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

        resp.setContentType("application/json");

        Gson gson = new Gson();

        // ==============================
        // 1. Leer JSON del body
        // ==============================
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            sb.append(line);
        }
        JsonObject json = JsonParser.parseString(sb.toString()).getAsJsonObject();

        // ==============================
        // 2. Obtener parámetros
        // ==============================
        if (req.getParameter("idAcademico") == null || req.getParameter("id") == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Debe enviar idAcademico y id\"}");
            return;
        }

        int idAcademico = Integer.parseInt(req.getParameter("idAcademico"));
        int idDetalle = Integer.parseInt(req.getParameter("id"));

        // ==============================
        // 3. Buscar el detalle dentro de findAll(idAcademico)
        // ==============================
        List<DetalleMateria> lista = dao.findAll(idAcademico);

        DetalleMateria actual = lista.stream()
                .filter(x -> x.getId() == idDetalle)
                .findFirst()
                .orElse(null);

        if (actual == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"DetalleMateria no encontrado\"}");
            return;
        }

        // ==============================
        // 4. Actualizar solo los campos enviados
        // ==============================
        if (json.has("academicoId") && !json.get("academicoId").isJsonNull()) {
            actual.setAcademicoId(json.get("academicoId").getAsInt());
        }

        if (json.has("nombre") && !json.get("nombre").isJsonNull()) {
            actual.setNombre(json.get("nombre").getAsString());
        }

        if (json.has("creditos") && !json.get("creditos").isJsonNull()) {
            actual.setCreditos(json.get("creditos").getAsInt());
        }

        if (json.has("docente") && !json.get("docente").isJsonNull()) {
            actual.setDocente(json.get("docente").getAsString());
        }

        if (json.has("estado") && !json.get("estado").isJsonNull()) {
            actual.setEstado(json.get("estado").getAsString());
        }

        // ==============================
        // 5. Guardar cambios en la BD
        // ==============================
        boolean ok = dao.updatePartial(actual);

        if (!ok) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"No se pudo actualizar el registro\"}");
            return;
        }

        // ==============================
        // 6. Devolver modelo actualizado
        // ==============================
        resp.getWriter().write(gson.toJson(actual));
    }
}
