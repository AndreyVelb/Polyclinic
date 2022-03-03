package servlet;

//@WebServlet("/patient")
//public class PatientRegistrationServlet extends HttpServlet {
//    PatientRegistrationService patientRegistrationService = new PatientRegistrationService();
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
//        try (PrintWriter writer = resp.getWriter()){
//            patientRegistrationService.registration(writer, req, resp);
//        } catch (IOException e) {
//            try {
//                new ExceptionResponse().send(resp.getWriter(), resp, new ServerTechnicalProblemsException(), SC_INTERNAL_SERVER_ERROR);
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//    }
//}
