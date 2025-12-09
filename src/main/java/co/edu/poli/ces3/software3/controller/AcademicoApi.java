package co.edu.poli.ces3.software3.controller;

import co.edu.poli.ces3.software3.dao.AcademicoDAO;
import co.edu.poli.ces3.software3.model.Academico;

import co.edu.poli.ces3.software3.model.DetalleMateria;
import co.edu.poli.ces3.software3.model.Student;
import co.edu.poli.ces3.software3.servlet.PatchServlet;
import com.google.gson.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name="AcademicoApi",value="/api/academico")
public class AcademicoApi extends PatchServlet {

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


        BufferedReader reader = request.getReader();
        JsonObject body = gson.fromJson(reader, JsonObject.class);
        PrintWriter out = response.getWriter();

        int studentId = body.get("studentId").getAsInt();
        Academico a = gson.fromJson(body.get("academico"), Academico.class);


        Academico inserted = dao.insert(studentId, a);

        if (inserted == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"No se pudo insertar\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_CREATED);
            Gson gson = new Gson();
            out.println(gson.toJson(inserted));
        }
    }

    // PUT (update)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        JsonObject body = gson.fromJson(reader, JsonObject.class);

        Academico a = gson.fromJson(body, Academico.class);

        Academico inserted = dao.update(null, a);

        if (inserted == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"No se pudo insertar\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_CREATED);
            Gson gson = new Gson();
            out.println(gson.toJson(inserted));
        }
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

    @Override
    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    }
}
