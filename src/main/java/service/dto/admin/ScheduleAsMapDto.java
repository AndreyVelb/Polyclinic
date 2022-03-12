package service.dto.admin;

import entity.Doctor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import service.dto.Dto;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ScheduleAsMapDto implements Dto {

    Doctor doctor;

    Map<DayOfWeek, Boolean> scheduleAsMap;

}
