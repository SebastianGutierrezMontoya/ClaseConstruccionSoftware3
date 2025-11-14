package co.edu.poli.ces3.software3;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import co.edu.poli.ces3.software3.dbo.Student;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private LocalDate date;

//    List<Student> students = new ArrayList<>();
//
//    public HelloServlet() {
//
//        students.add(new Student("Sebastian", "Gutierrez", LocalDate.parse("12/01/2004", formato), "sebas@gmail"));
//        students.add(new Student("Maria", "Lopez", LocalDate.parse("23/05/2003", formato), "maria.lopez@gmail"));
//        students.add(new Student("Juan", "Perez", LocalDate.parse("15/09/2002", formato), "juanperez@gmail"));
//        students.add(new Student("Carla", "Ramirez", LocalDate.parse("02/11/2005", formato), "carla.ramirez@gmail"));
//        students.add(new Student("Andres", "Martinez", LocalDate.parse("30/07/2004", formato), "andres.m@gmail"));
//
//
//    }

    public void init() {

        message = "Hola desde el politecnico!";
        //student = new Student("sebas","gutierrez", new Date("12-01-2004"), "sebasgm");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("{");
        out.println("\"Nombre\": \""+ request.getParameter("name") + "\",");
        out.println("\"Cedula\": \"" + request.getParameter("cedula") + "\",");
        out.println("\"perfil\":{\"url\":\"vvv\",\"facebook\":\"facebook.com\"},");
        out.println("\"materias\":[\"ciencias\", \"matematicas\"]");
        out.println("}");

        response.setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

    }

    public void destroy() {
    }
}