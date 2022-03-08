package repository;

import entity.WorkSchedule;

public class DoctorsWorkScheduleRepository extends AbstractRepository<Long, WorkSchedule>{

    public DoctorsWorkScheduleRepository(Class<WorkSchedule> clazz) {
        super(clazz);
    }
}
