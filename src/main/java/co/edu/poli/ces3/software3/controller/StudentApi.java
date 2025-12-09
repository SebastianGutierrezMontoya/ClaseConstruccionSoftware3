package co.edu.poli.ces3.software3.controller;

import co.edu.poli.ces3.software3.dao.StudentDAO;
import co.edu.poli.ces3.software3.model.DetalleMateria;
import co.edu.poli.ces3.software3.model.Student;
import co.edu.poli.ces3.software3.model.StudentFull;
import co.edu.poli.ces3.software3.servlet.PatchServlet;
import com.google.gson.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name="StudentApi", value="/api/student")
public class StudentApi extends PatchServlet {

    private StudentDAO dao = new StudentDAO();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // GET (all or by id)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        String idParam = request.getParameter("id");
        String fulltree = request.getParameter("tree");

        if (idParam == null) {
            List<Student> list = dao.findAll();
            response.getWriter().write(gson.toJson(list));
        } else {
            if (fulltree == null) {
                int id = Integer.parseInt(idParam);
                Student s = dao.findById(id);
                response.getWriter().write(gson.toJson(s));
            }
            int id = Integer.parseInt(idParam);
            StudentFull full = null;
            try {
                full = dao.getFull(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            response.getWriter().write(gson.toJson(full));
        }
    }

    // POST (insert)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        BufferedReader reader = request.getReader();
        JsonObject body = gson.fromJson(reader, JsonObject.class);


        Student s = gson.fromJson(body, Student.class);

        Student inserted = dao.insert(null, s);

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

        Student s = gson.fromJson(body, Student.class);

        Student inserted = dao.update(null,s);

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
