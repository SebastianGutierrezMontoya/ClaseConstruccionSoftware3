package co.edu.poli.ces3.software3.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ResourceBundle;

public abstract class PatchServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getMethod();
        if (method.equals("PATCH")) {
            this.doPatch(req, resp);
        } else {
            super.service(req, resp);
        }
    }

    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String msg = ResourceBundle.getBundle("jakarta.servlet.http.LocalStrings").getString("http.method_get_not_supported");
        resp.sendError(405, msg);
    }

}