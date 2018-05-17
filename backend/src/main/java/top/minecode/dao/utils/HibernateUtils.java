package top.minecode.dao.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
public class HibernateUtils {

    /**
     * Session工厂类
     */
    private static final SessionFactory sessonFactory;

    /**
     * 为每个线程单独持有session（可以为空）
     */
    private static ThreadLocal<Session> sesson = new ThreadLocal<>();

    static {
        Configuration config = new Configuration().configure("hibernate.cfg.xml");
        sessonFactory = config.buildSessionFactory();
    }

    /**
     * 获取当前线程对应的session，如果没有，则创建
     * @return 当前线程对应的session
     */
    public static Session getCurrentSession() {
        Session ses = sesson.get();
        if (ses == null) {
            ses = sessonFactory.openSession();
            sesson.set(ses);
        }
        return ses;
    }

    /**
     * 关闭当前线程对应的session
     */
    public static void closeSession() {
        Session ses = sesson.get();
        if (ses != null) {
            ses.close();
        }
        sesson.set(null);
    }

}
