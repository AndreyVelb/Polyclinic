package util;

import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

@UtilityClass
public class NewDBSessionPool {
    private static final SessionFactory newDBSessionFactory = HibernateUtil.buildSessionFactoryForNEWdb();

    public static Session getSession(){
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class}, (proxy, method, args1)
                    -> method.invoke(newDBSessionFactory.getCurrentSession(), args1));
    }
}
