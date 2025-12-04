package co.edu.poli.ces3.software3.servlet;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import co.edu.poli.ces3.software3.service.SubjectService;
import co.edu.poli.ces3.software3.dbo.StatusEnum;
import co.edu.poli.ces3.software3.dbo.Subject;
import java.util.Vector;
import com.google.gson.*;

import java.util.Arrays;


@WebServlet(name = "SubjectServlet", value = "/subject-servlet")
public class SubjectServlet extends HttpServlet {

    private SubjectService Service;
    @Override
    public void init() throws ServletException {
        super.init();
        Service = new SubjectService();

    }

    protected JsonObject getParamsFromBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line = reader.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = reader.readLine();
        }
        reader.close();
        return JsonParser.parseString(sb.toString()).getAsJsonObject();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        Gson gson = new Gson();

        if (request.getParameter("idSubject") != null) {

            Subject s = Service.findById(request.getParameter("idSubject"));
            out.print(gson.toJson(s));
        } else {

            Vector<Subject> subjects = Service.find();



            out.print(gson.toJson(subjects));

        }


        out.flush();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");


        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        Gson gson = new Gson();


        Subject s = Service.add(getParamsFromBody(request));

        out.print(gson.toJson(s));

        out.flush();

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");

        PrintWriter out = response.getWriter();
        Gson gson = new Gson();
        Subject s = Service.update(getParamsFromBody(request));

        out.print(gson.toJson(s));
    }
}

