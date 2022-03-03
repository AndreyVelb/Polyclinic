package servlet.response;

//public class ResponseFactory {
//    private Map<Dto, ResponseInterface> responseTypes = new HashMap<>();
//
//    ResponseFactory(){
//        responseTypes.put(PatientDto.class, new PatientRegistrationResponse());
//
//    }
//
//
//
//    public void sendResponse(O object, PrintWriter writer, HttpServletResponse response){
//
//    }
//
//    private void createResponse(Object object, PrintWriter writer, HttpServletResponse response){
//        for (var entry : responseTypes.entrySet()) {
//            if (o instanceof entry.getKey()){
//
//            }
//        }
//        if (object instanceof PatientDto){
//            new PatientRegistrationResponse().send(writer, response, (PatientDto) object, SC_CREATED);
//        }
//    }
//}
