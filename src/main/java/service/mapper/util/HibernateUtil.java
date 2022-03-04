package service.mapper.util;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import entity.AppointmentRecord;
import entity.Doctor;
import entity.Patient;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


@UtilityClass
public class HibernateUtil {
    public static SessionFactory buildSessionFactory() {
        Configuration configurationForNEWdb = buildConfiguration().configure("hibernate.cfg.xml");;
        return configurationForNEWdb.buildSessionFactory();
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Doctor.class);
        configuration.addAnnotatedClass(Patient.class);
        configuration.addAnnotatedClass(AppointmentRecord.class);
        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.registerTypeOverride(new JsonType());
        return configuration;
    }


}
