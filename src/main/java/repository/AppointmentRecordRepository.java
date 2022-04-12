package repository;

import entity.AppointmentRecord;

public class AppointmentRecordRepository extends AbstractRepository<Long, AppointmentRecord> {

    public AppointmentRecordRepository() {
        super(AppointmentRecord.class);

    }
}
