package co.edu.poli.ces3.software3.controller;

import co.edu.poli.ces3.software3.dao.AcademicoDAO;
import co.edu.poli.ces3.software3.model.Academico;

import com.google.gson.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet(name="AcademicoApi",value="/api/academico")
public class AcademicoApi extends HttpServlet {

    private AcademicoDAO dao = new AcademicoDAO();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // GET (all or by id)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        String idParam = request.getParameter("id");

        if (idParam == null) {
            List<Academico> list = dao.findAll();
            response.getWriter().write(gson.toJson(list));
        } else {
            int id = Integer.parseInt(idParam);
            Academico a = dao.findById(id);
            response.getWriter().write(gson.toJson(a));
        }
    }

    // POST (insert)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String body = request.getReader().lines().reduce("", (acc, line) -> acc + line);

        JsonObject jsonBody = JsonParser.parseString(body).getAsJsonObject();

        int studentId = jsonBody.get("studentId").getAsInt();
        Academico a = gson.fromJson(jsonBody.get("academico"), Academico.class);

        boolean ok = dao.insert(a, studentId);

        JsonObject json = new JsonObject();
        json.addProperty("inserted", ok);
        json.add("academico", gson.toJsonTree(a));

        response.getWriter().write(json.toString());
    }

    // PUT (update)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String body = request.getReader().lines().reduce("", (acc, line) -> acc + line);
        Academico a = gson.fromJson(body, Academico.class);

        boolean ok = dao.update(a);

        JsonObject json = new JsonObject();
        json.addProperty("updated", ok);

        response.getWriter().write(json.toString());
    }

    // DELETE (delete by id)
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String idParam = request.getParameter("id");
        JsonObject json = new JsonObject();

        if (idParam == null) {
            json.addProperty("error", "Debe enviar ?id=");
        } else {
            boolean ok = dao.delete(Integer.parseInt(idParam));
            json.addProperty("deleted", ok);
        }

        response.getWriter().write(json.toString());
    }
}
