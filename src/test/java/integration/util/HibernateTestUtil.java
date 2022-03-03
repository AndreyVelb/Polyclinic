package integration.util;

import entity.newdb.AppointmentRecordNewDB;
import entity.newdb.DoctorNewDB;
import entity.newdb.PatientNewDB;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import util.HibernateUtil;

@Testcontainers
public class HibernateTestUtil {
    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13").withInitScript("db/init.sql");

    static {
        postgres.start();

    }

    public static SessionFactory buildTestSessionFactoryForOLDdb(){
        Configuration configuration = HibernateUtil.buildConfigurationForOLDdb();
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        configuration.configure("hibernate_old_db.cfg.xml");
        return configuration.buildSessionFactory();
    }

    public static SessionFactory buildTestSessionFactoryForNEWdb(){
        Configuration configuration = HibernateUtil.buildConfigurationForNEWdb();
        configuration.configure("hibernate_new_db.cfg.xml");
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        configuration.addAnnotatedClass(DoctorNewDB.class);
        configuration.addAnnotatedClass(PatientNewDB.class);
        configuration.addAnnotatedClass(AppointmentRecordNewDB.class);
        return configuration.buildSessionFactory();
    }
}
