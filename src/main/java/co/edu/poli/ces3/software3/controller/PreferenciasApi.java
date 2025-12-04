package co.edu.poli.ces3.software3.controller;

import co.edu.poli.ces3.software3.dao.PreferenciasDao;
import co.edu.poli.ces3.software3.model.Preferencias;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "PreferenciasApi", value = "/api/preferencias")
public class PreferenciasApi extends HttpServlet {

    private PreferenciasDao dao = new PreferenciasDao();
    private Gson gson = new Gson();

    // GET → obtener preferencias
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idStudent = Integer.parseInt(req.getParameter("idStudent"));
        Preferencias pref = dao.getByStudent(idStudent);

        resp.setContentType("application/json");
        resp.getWriter().print(gson.toJson(pref));
    }

    // POST → insertar
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        JsonObject body = gson.fromJson(reader, JsonObject.class);

        int idStudent = body.get("idStudent").getAsInt();
        Preferencias pref = gson.fromJson(body.get("preferencias"), Preferencias.class);

        boolean ok = dao.insert(idStudent, pref);

        JsonObject json = new JsonObject();
        json.addProperty("success", ok);
        resp.getWriter().print(json.toString());
    }

    // PUT → actualizar
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        JsonObject body = gson.fromJson(reader, JsonObject.class);

        int idStudent = body.get("idStudent").getAsInt();
        Preferencias pref = gson.fromJson(body.get("preferencias"), Preferencias.class);

        boolean ok = dao.update(idStudent, pref);

        JsonObject json = new JsonObject();
        json.addProperty("success", ok);
        resp.getWriter().print(json.toString());
    }

    // DELETE → eliminar
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idStudent = Integer.parseInt(req.getParameter("idStudent"));

        boolean ok = dao.delete(idStudent);

        JsonObject json = new JsonObject();
        json.addProperty("success", ok);
        resp.getWriter().print(json.toString());
    }
}
