package servlet;


//@WebServlet("/doctor/find_patient")
//public class FindPatientByLastNameServlet extends HttpServlet {
//    SessionFactory sessionFactory = HibernateUtil.buildSessionFactoryForNEWdb();
//    Session session = sessionFactory.openSession();
//    PatientNewDBRepository patientNewDBRepository = new PatientNewDBRepository(session);
//    DoctorLoginService doctorService = new DoctorLoginService();;
////    PatientFindByLastNameDTO dto = PatientFindByLastNameDTO.builder()
////            .lastName("Иванов")
////            .build();
//
//    @Override
//    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        resp.setContentType("text/html");
//        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
//
//        try (var writer = resp.getWriter()){
//            writer.print("<h1>По вашему запросу найдены следующие пациенты:<h2>");
//            writer.write("<ul>");
//            try {
//                doctorService.findPatientByLastName("Иванов").forEach(patientNewDB -> {
//                    writer.write("""
//                            <li>
//                                %s
//                            </li>
//                            """.formatted(patientNewDB.toString()));
//                });
//            } catch (PatientNotFoundException e) {
//                writer.write(e.getMessage());
//            }
//            writer.write("</ul>");
//        }
//    }
//
//    @Override
//    public void destroy() {
//        super.destroy();
//    }
//}
