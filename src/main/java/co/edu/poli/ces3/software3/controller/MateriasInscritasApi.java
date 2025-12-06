package co.edu.poli.ces3.software3.controller;

import co.edu.poli.ces3.software3.dao.MateriasInscritasDao;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "MateriasInscritasApi", value = "/api/materiasinscritas")
public class MateriasInscritasApi extends HttpServlet {

    private MateriasInscritasDao materiasDao = new MateriasInscritasDao();
    private Gson gson = new Gson();

    // GET → obtener materias inscritas por idAcademico
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        int idAcademico = Integer.parseInt(req.getParameter("idAcademico"));

        List<String> materias = materiasDao.findById(idAcademico);

        String jsonResponse = gson.toJson(materias);

        PrintWriter out = resp.getWriter();
        out.print(jsonResponse);
    }

    // POST → agregar materias nuevas
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        BufferedReader reader = req.getReader();
        JsonObject body = gson.fromJson(reader, JsonObject.class);

        int idAcademico = body.get("idAcademico").getAsInt();
        String materia = body.get("materia").getAsString();

        boolean ok = materiasDao.insert(idAcademico, materia);

        JsonObject json = new JsonObject();
        json.addProperty("success", ok);

        resp.getWriter().print(json.toString());
    }

    // PUT → reemplazar toda la lista de materias
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        BufferedReader reader = req.getReader();
        JsonObject body = gson.fromJson(reader, JsonObject.class);

        int idAcademico = body.get("idAcademico").getAsInt();
        List<String> materias = gson.fromJson(body.get("materias"), List.class);

        boolean ok = materiasDao.update(idAcademico, materias);

        JsonObject json = new JsonObject();
        json.addProperty("success", ok);

        resp.getWriter().print(json.toString());
    }

    // DELETE → eliminar una materia específica
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int idAcademico = Integer.parseInt(req.getParameter("idAcademico"));
        String materia = req.getParameter("materia");

        boolean ok = materiasDao.delete(idAcademico, materia);

        JsonObject json = new JsonObject();
        json.addProperty("success", ok);

        resp.getWriter().print(json.toString());
    }
}
