package repository;

import entity.DoctorsAppointment;

public class DoctorsAppointmentRepository extends AbstractRepository<Long, DoctorsAppointment>{

    public DoctorsAppointmentRepository(Class<DoctorsAppointment> clazz) {
        super(clazz);
    }
}
