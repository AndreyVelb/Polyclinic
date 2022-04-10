package service.patient;

import entity.Doctor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.DoctorRepository;
import service.Mapper;
import service.dto.doctor.DoctorDto;
import util.SessionPool;

import java.util.ArrayList;

@RequiredArgsConstructor
public class DoctorChoiceService {

    private final DoctorRepository doctorRepository;

    private final Mapper mapper;

    @SneakyThrows
    public ArrayList<DoctorDto> getAllDoctors(){
        Session session = SessionPool.getSession();
        ArrayList<Doctor> doctorsList;
        try {
            session.beginTransaction();
            doctorsList = (ArrayList<Doctor>) doctorRepository.findAllDoctorsExceptAdmins(session);
            session.getTransaction().commit();
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
        ArrayList<DoctorDto> doctorDtoList = new ArrayList<>();
        doctorsList.forEach(doctor -> doctorDtoList.add(mapper.mapToDoctorDto(doctor)));
        return doctorDtoList;
    }
}
