package co.edu.poli.ces3.software3.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;

import co.edu.poli.ces3.software3.dbo.Student;
import co.edu.poli.ces3.software3.dbo.Academico;
import co.edu.poli.ces3.software3.dbo.DetalleMateria;
import co.edu.poli.ces3.software3.dbo.Notificaciones;
import co.edu.poli.ces3.software3.dbo.Preferencias;

import com.google.gson.*;

@WebServlet(name = "StudentServlet", value = "/student-servlet")
public class StudentServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        out.println("{");
        out.println("\"mensaje\" : \"Hola\"");
        out.println("}");


        out.flush();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json; charset=UTF-8");

        // ----- DATOS INVENTADOS -----
        Notificaciones notif = new Notificaciones(true, false, true);

        Preferencias preferencias = new Preferencias(
                "híbrida",
                new String[]{"ajedrez", "fotografía", "club de programación"},
                notif
        );

        DetalleMateria m1 = new DetalleMateria("Algoritmos", 3, "Laura Meneses", "cursando");
        DetalleMateria m2 = new DetalleMateria("Base de Datos", 4, "Carlos Ríos", "cursando");
        DetalleMateria m3 = new DetalleMateria("Matemáticas Discretas", 3, "Diana Duarte", "aprobada");

        Academico academico = new Academico(
                "Ingeniería de Sistemas",
                4,
                4.2,
                Arrays.asList("Algoritmos", "Base de Datos", "Matemáticas Discretas"),
                Arrays.asList(m1, m2, m3)
        );

        Student estudiante = new Student(
                "Marcos Herrera Ocampo",
                20,
                "marcos.herrera@example.edu",
                "3115558899",
                "Medellín",
                academico,
                preferencias
        );



        PrintWriter out = response.getWriter();

//        out.println("{");
//        out.println("  \"nombreCompleto\": \"Marcos Herrera Ocampo\",");
//        out.println("  \"edad\": 20,");
//        out.println("  \"correo\": \"marcos.herrera@example.edu\",");
//        out.println("  \"telefono\": \"3115558899\",");
//        out.println("  \"ciudadResidencia\": \"Medellín\",");
//        out.println("  \"academico\": {");
//        out.println("    \"programa\": \"Ingeniería de Sistemas\",");
//        out.println("    \"semestreActual\": 4,");
//        out.println("    \"promedioAcumulado\": 4.2,");
//        out.println("    \"materiasInscritas\": [");
//        out.println("      \"Algoritmos\",");
//        out.println("      \"Base de Datos\",");
//        out.println("      \"Matemáticas Discretas\"");
//        out.println("    ],");
//        out.println("    \"detalleMaterias\": [");
//        out.println("      {");
//        out.println("        \"nombre\": \"Algoritmos\",");
//        out.println("        \"creditos\": 3,");
//        out.println("        \"docente\": \"Laura Meneses\",");
//        out.println("        \"estado\": \"cursando\"");
//        out.println("      },");
//        out.println("      {");
//        out.println("        \"nombre\": \"Base de Datos\",");
//        out.println("        \"creditos\": 4,");
//        out.println("        \"docente\": \"Carlos Ríos\",");
//        out.println("        \"estado\": \"cursando\"");
//        out.println("      },");
//        out.println("      {");
//        out.println("        \"nombre\": \"Matemáticas Discretas\",");
//        out.println("        \"creditos\": 3,");
//        out.println("        \"docente\": \"Diana Duarte\",");
//        out.println("        \"estado\": \"aprobada\"");
//        out.println("      }");
//        out.println("    ]");
//        out.println("  },");
//        out.println("  \"preferencias\": {");
//        out.println("    \"modalidadEstudio\": \"híbrida\",");
//        out.println("    \"actividadesExtracurriculares\": [");
//        out.println("      \"ajedrez\",");
//        out.println("      \"fotografía\",");
//        out.println("      \"club de programación\"");
//        out.println("    ],");
//        out.println("    \"notificaciones\": {");
//        out.println("      \"email\": true,");
//        out.println("      \"sms\": false,");
//        out.println("      \"app\": true");
//        out.println("    }");
//        out.println("  }");
//        out.println("}");

        Gson gson = new Gson();
        String jsonResponse = gson.toJson(estudiante);

        out.print(jsonResponse);
        out.flush();

    }


}

