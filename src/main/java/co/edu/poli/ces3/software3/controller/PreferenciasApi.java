package co.edu.poli.ces3.software3.controller;

import co.edu.poli.ces3.software3.dao.PreferenciasDao;
import co.edu.poli.ces3.software3.model.Notificaciones;
import co.edu.poli.ces3.software3.model.Preferencias;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "PreferenciasApi", value = "/api/preferencias")
public class PreferenciasApi extends HttpServlet {

    private PreferenciasDao preferenciasDAO = new PreferenciasDao();
    private Gson gson = new Gson();


    // ==========================================
    // GET /api/preferencias?studentId=#
    // ==========================================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String studentIdParam = req.getParameter("studentId");

        if (studentIdParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"Debe enviar studentId\"}");
            return;
        }

        int studentId = Integer.parseInt(studentIdParam);

        Preferencias p = preferenciasDAO.findById(studentId);

        if (p == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("{\"error\":\"No existen preferencias para este estudiante\"}");
            return;
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        out.println(gson.toJson(p));
    }


    // ==========================================
    // POST → INSERTAR
    // ==========================================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        BufferedReader reader = req.getReader();
        JsonObject json = gson.fromJson(reader, JsonObject.class);

        try {
            int studentId = json.get("studentId").getAsInt();
            String modalidad = json.get("modalidadEstudio").getAsString();

            // actividadesExtracurriculares como List<String>
            Type listType = new TypeToken<List<String>>(){}.getType();
            List<String> actividades = gson.fromJson(
                    json.get("actividadesExtracurriculares"), listType
            );

            // notificaciones
            JsonObject notifJson = json.getAsJsonObject("notificaciones");
            Notificaciones notif = new Notificaciones(
                    notifJson.get("email").getAsBoolean(),
                    notifJson.get("sms").getAsBoolean(),
                    notifJson.get("app").getAsBoolean()
            );

            Preferencias pref = new Preferencias();
            pref.setStudentId(studentId);
            pref.setModalidadEstudio(modalidad);
            pref.setActividadesExtracurriculares(actividades);
            pref.setNotificaciones(notif);

            Preferencias insertado = preferenciasDAO.insert(studentId, pref);

            if (insertado == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\":\"No se pudo insertar\"}");
                return;
            }

            resp.setStatus(HttpServletResponse.SC_CREATED);
            out.println(gson.toJson(insertado));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"JSON inválido o incompleto\"}");
            e.printStackTrace();
        }
    }


    // ==========================================
    // PUT → ACTUALIZAR
    // ==========================================
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        BufferedReader reader = req.getReader();
        JsonObject json = gson.fromJson(reader, JsonObject.class);


        try {
            int studentId = json.get("studentId").getAsInt();
            String modalidad = json.get("modalidadEstudio").getAsString();

            Type listType = new TypeToken<List<String>>(){}.getType();
            List<String> actividades = gson.fromJson(
                    json.get("actividadesExtracurriculares"), listType
            );

            JsonObject notifJson = json.getAsJsonObject("notificaciones");
            Notificaciones notif = new Notificaciones(
                    notifJson.get("email").getAsBoolean(),
                    notifJson.get("sms").getAsBoolean(),
                    notifJson.get("app").getAsBoolean()
            );

            Preferencias pref = new Preferencias();
            pref.setStudentId(studentId);
            pref.setModalidadEstudio(modalidad);
            pref.setActividadesExtracurriculares(actividades);
            pref.setNotificaciones(notif);

            Preferencias actualizado = preferenciasDAO.update(pref);

            if (actualizado == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("{\"error\":\"No se pudo actualizar\"}");
                return;
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            out.println(gson.toJson(actualizado));

        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("{\"error\":\"JSON inválido o incompleto\"}");
            e.printStackTrace();
        }
    }





}
