package integration.util;

import entity.AppointmentRecord;
import entity.Doctor;
import entity.Patient;
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

    public static SessionFactory buildTestSessionFactory(){
        Configuration configuration = HibernateUtil.buildConfiguration();
        configuration.configure("hibernate.cfg.xml");
        configuration.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgres.getUsername());
        configuration.setProperty("hibernate.connection.password", postgres.getPassword());
        configuration.addAnnotatedClass(Doctor.class);
        configuration.addAnnotatedClass(Patient.class);
        configuration.addAnnotatedClass(AppointmentRecord.class);
        return configuration.buildSessionFactory();
    }
}
