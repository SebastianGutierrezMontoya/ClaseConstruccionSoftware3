package co.edu.poli.ces3.software3.controller;

import co.edu.poli.ces3.software3.dao.ActividadesExtracurricularesDao;
import co.edu.poli.ces3.software3.dao.NotificacionesDao;
import co.edu.poli.ces3.software3.dao.PreferenciasDao;
import co.edu.poli.ces3.software3.model.Actividades;
import co.edu.poli.ces3.software3.model.Notificaciones;
import co.edu.poli.ces3.software3.model.Preferencias;

import co.edu.poli.ces3.software3.servlet.PatchServlet;
import com.google.gson.*;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "PreferenciasApi", value = "/api/preferencias")
public class PreferenciasApi extends PatchServlet {

    private PreferenciasDao preferenciasDAO = new PreferenciasDao();
    private ActividadesExtracurricularesDao actividadesDAO = new ActividadesExtracurricularesDao();
    private NotificacionesDao NotificacionDAO = new NotificacionesDao();
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


            Type listType = new TypeToken<List<Actividades>>(){}.getType();
            List<Actividades> actividades =
                    gson.fromJson(json.get("actividadesExtracurriculares"), listType);


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

            Type listType = new TypeToken<List<Actividades>>(){}.getType();
            List<Actividades> actividades =
                    gson.fromJson(json.get("actividadesExtracurriculares"), listType);

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

            Preferencias actualizado = preferenciasDAO.update(null, pref);

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
        // 2. Validar ID
        // ==============================
        if (req.getParameter("id") == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Debe enviar id\"}");
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));

        // ==============================
        // 3. Buscar Preferencias por ID
        // ==============================
        Preferencias pref = preferenciasDAO.findById(id);

        if (pref == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"Preferencias no encontrada\"}");
            return;
        }

        // ==============================
        // 4. Actualizar campos simples
        // ==============================
        if (json.has("modalidadEstudio") && !json.get("modalidadEstudio").isJsonNull()) {
            pref.setModalidadEstudio(json.get("modalidadEstudio").getAsString());
        }

        // ==============================
        // 5. Actualizar Notificaciones
        // ==============================
        if (json.has("notificaciones") && json.get("notificaciones").isJsonObject()) {
            JsonObject jsonNotif = json.get("notificaciones").getAsJsonObject();

            Notificaciones noti = NotificacionDAO.findById(pref.getId());

            if (noti == null) {
                noti = new Notificaciones();
                noti.setPreferenciasId(pref.getId());
            }

            if (jsonNotif.has("email")) {
                noti.setEmail(jsonNotif.get("email").getAsBoolean());
            }
            if (jsonNotif.has("sms")) {
                noti.setSms(jsonNotif.get("sms").getAsBoolean());
            }
            if (jsonNotif.has("app")) {
                noti.setApp(jsonNotif.get("app").getAsBoolean());
            }

            NotificacionDAO.update(pref.getId(), noti);
            pref.setNotificaciones(noti);
        }

        // ==============================
        // 6. Actualizar ActividadesExtracurriculares
        // ==============================
        if (json.has("actividadesExtracurriculares") && json.get("actividadesExtracurriculares").isJsonArray()) {


            JsonArray array = json.get("actividadesExtracurriculares").getAsJsonArray();
            List<Actividades> nuevas = new ArrayList<>();

            for (JsonElement el : array) {
                JsonObject obj = el.getAsJsonObject();

                Actividades act = new Actividades();
                act.setPreferenciasId(pref.getId());
                act.setActividad(obj.get("actividad").getAsString());

                act = actividadesDAO.insert(pref.getId(), act);
                nuevas.add(act);
            }

            pref.setActividadesExtracurriculares(nuevas);
        }

        // ==============================
        // 7. Guardar Preferencias
        // ==============================
        preferenciasDAO.updatePartial(pref);

        // ==============================
        // 8. Recargar objeto completo actualizado
        // ==============================
        Preferencias resultado = preferenciasDAO.findById(pref.getStudentId());
        System.out.println("resultado: " + resultado);
        resp.getWriter().write(gson.toJson(resultado));

    }


}
