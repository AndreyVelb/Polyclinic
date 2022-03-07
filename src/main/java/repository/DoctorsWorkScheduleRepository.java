package repository;

import entity.DoctorsWorkSchedule;

public class DoctorsWorkScheduleRepository extends AbstractRepository<Long, DoctorsWorkSchedule>{

    public DoctorsWorkScheduleRepository(Class<DoctorsWorkSchedule> clazz) {
        super(clazz);
    }
}
