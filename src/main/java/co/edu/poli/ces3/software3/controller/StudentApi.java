package co.edu.poli.ces3.software3.controller;

import co.edu.poli.ces3.software3.dao.StudentDAO;
import co.edu.poli.ces3.software3.model.Student;
import com.google.gson.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name="StudentApi", value="/api/student")
public class StudentApi extends HttpServlet {

    private StudentDAO dao = new StudentDAO();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    // GET (all or by id)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("application/json");

        String idParam = request.getParameter("id");

        if (idParam == null) {
            List<Student> list = dao.findAll();
            response.getWriter().write(gson.toJson(list));
        } else {
            int id = Integer.parseInt(idParam);
            Student s = dao.findById(id);
            response.getWriter().write(gson.toJson(s));
        }
    }

    // POST (insert)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String body = request.getReader().lines().reduce("", (acc, line) -> acc + line);
        Student s = gson.fromJson(body, Student.class);

        boolean ok = dao.insert(s);

        JsonObject json = new JsonObject();
        json.addProperty("inserted", ok);
        json.add("student", gson.toJsonTree(s));

        response.getWriter().write(json.toString());
    }

    // PUT (update)
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String body = request.getReader().lines().reduce("", (acc, line) -> acc + line);
        Student s = gson.fromJson(body, Student.class);

        boolean ok = dao.update(s);

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
