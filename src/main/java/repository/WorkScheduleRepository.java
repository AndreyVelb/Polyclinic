package repository;

import entity.WorkSchedule;

public class WorkScheduleRepository extends AbstractRepository<Long, WorkSchedule>{

    public WorkScheduleRepository() {
        super(WorkSchedule.class);
    }
}
