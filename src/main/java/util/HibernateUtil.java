package util;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonType;
import entity.newdb.AppointmentRecordNewDB;
import entity.newdb.DoctorNewDB;
import entity.newdb.PatientNewDB;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;


@UtilityClass
public class HibernateUtil {
    public static SessionFactory buildSessionFactoryForOLDdb() {
        Configuration configurationForOLDdb = buildConfigurationForOLDdb().configure("hibernate_old_db.cfg.xml");;
        return configurationForOLDdb.buildSessionFactory();
    }

    public static SessionFactory buildSessionFactoryForNEWdb() {
        Configuration configurationForNEWdb = buildConfigurationForNEWdb().configure("hibernate_new_db.cfg.xml");;
        return configurationForNEWdb.buildSessionFactory();
    }

    public static Configuration buildConfigurationForOLDdb(){
        Configuration configuration = new Configuration();
        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.registerTypeOverride(new JsonType());
        return configuration;
    }

    public static Configuration buildConfigurationForNEWdb() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(DoctorNewDB.class);
        configuration.addAnnotatedClass(PatientNewDB.class);
        configuration.addAnnotatedClass(AppointmentRecordNewDB.class);
        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.registerTypeOverride(new JsonType());
//        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());

        return configuration;
    }


}
