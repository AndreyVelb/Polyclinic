package service.patient;

import entity.Doctor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.hibernate.Session;
import repository.DoctorRepository;
import service.dto.doctor.DoctorDto;
import service.mapper.DoctorDtoMapper;
import util.SessionPool;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DoctorChoiceService {

    private final DoctorRepository doctorRepository;

    private final DoctorDtoMapper doctorDtoMapper;

    @SneakyThrows
    public ArrayList<DoctorDto> getAllDoctors(){
        Session session = SessionPool.getSession();
        ArrayList<Doctor> doctorsList;
        try {
            session.beginTransaction();
            doctorsList = doctorRepository.findAll(session);
            session.getTransaction().commit();
        }catch (Exception exception){
            session.getTransaction().rollback();
            throw exception;
        }
        ArrayList<DoctorDto> doctorDtoList = new ArrayList<>();
        doctorsList.forEach(doctor -> doctorDtoList.add(doctorDtoMapper.mapFrom(doctor)));
        return doctorDtoList;
    }
}
