/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 16476
 */
@WebServlet(urlPatterns = {"/GradeCalculationServlet"})
public class GradeCalculationServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
            String grades = (String) request.getParameter("marks");
            if (grades == null) {
                grades = request.getParameter("marks");
                session.setAttribute("marks", grades);
            }
            Double grade = Double.parseDouble(grades);
            ArrayList<Double> marks = (ArrayList<Double>) session.getAttribute("marks");
            if (marks == null) {
                marks = new ArrayList();
            }
            marks.add(grade);
            session.setAttribute("marks", marks);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<body>");
            out.println("<form action='GradeCalculationServlet' method='GET'>\n"
                    + "        Course Grade <select name='marks'>\n"
                    + "            <option></option>\n"
                    + "            <option value=\"4\">A+ (90-100%)</option>\n"
                    + "            <option value=\"4\">A (80-89%)</option>\n"
                    + "            <option value=\"3.5\">B+ (75-79%)</option>\n"
                    + "            <option value=\"3\">B (70-74%)</option>\n"
                    + "            <option value=\"2\">C+ (65-69%)</option>\n"
                    + "            <option value=\"2\">C (60-64%)</option>\n"
                    + "            <option value=\"1\">D (50-59%)</option>\n"
                    + "            <option value=\"0\">F (0-49%)</option>\n"
                    + "        </select>\n"
                    + "        <input type='submit' name='action' value='Next'>\n"
                    + "        <input type='submit' name='action' value='Done'>\n"
                    + "        <p>(Click Done when you have chosen your mark for the last question instead of Next)</p>\n"
                    + "        </form>");
            out.println("<ol>");
            for (Double m : marks) {
                out.println("<li>" + m + "</li>");
            }
            String btnValue = request.getParameter("action");
            if (btnValue.equals("Done")) {
                out.println("<br>");
                out.println("<h1>YOUR GRADES:</h1>");
                double sum = 0;
                for (Double mark : marks) {
                    sum += mark;
                }
                double studentGPAs = sum / marks.size();
                double studentGPA = Math.round(studentGPAs*100)/100;
                out.println("<br>");
                out.println("Your GPA is: " + studentGPA);
                if (studentGPA < 1.1 && studentGPA >= 0) {
                    out.println("<h2>Your academic Standing is: Cannot Continue<h2>");
                }
                if (studentGPA >= 1.20 && studentGPA <= 1.79) {
                    out.println("<h2>Your academic Standing is: Academic Probation<h2>");
                }
                if (studentGPA >= 1.80 && studentGPA <= 1.99) {
                    out.println("<h2>Your academic Standing is: Conditional Pass<h2>");
                }
                if (studentGPA >= 2.00 && studentGPA <= 3.49) {
                    out.println("<h2>Your academic Standing is: Good<h2>");
                }
                if (studentGPA >= 3.50 && studentGPA <= 3.79) {
                    out.println("<h2>Your academic Standing is: Honours<h2>");
                }
                if (studentGPA >= 3.80 && studentGPA <= 4) {
                    out.println("<h2>Your academic Standing is: Sheridan Scholar<h2>");
                }
                out.println("<form action='index.html' method='GET'>\n"
                        + " <input type='submit' value='Start Over'>\n"
                        + "        </form>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
