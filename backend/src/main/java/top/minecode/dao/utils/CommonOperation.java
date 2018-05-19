package top.minecode.dao.utils;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created on 2018/5/19.
 * Description:
 *
 * @author iznauy
 */
public class CommonOperation<T> {

    private String className;

    public CommonOperation(String className) {
        this.className = className;
    }

    public CommonOperation(Class<T> clazz) {
        this.className = clazz.getSimpleName();
    }

    public List<T> getAll() {
        Session session = HibernateUtils.getCurrentSession();
        List<T> jrs = null;
        try {
            session.getTransaction().begin();
            String sql = "select t from " + className + " t ";
            Query query = session.createQuery(sql);
            jrs = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            HibernateUtils.closeSession();
        }
        return jrs;
    }

    public boolean add(T t) {
        Session session = HibernateUtils.getCurrentSession();
        try {
            session.getTransaction().begin();
            session.persist(t);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            HibernateUtils.closeSession();
        }
        return true;
    }

    public boolean update(T tpo) {
        Session session = HibernateUtils.getCurrentSession();
        try {
            session.getTransaction().begin();
            session.update(tpo);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        } finally {
            HibernateUtils.closeSession();
        }
        return true;
    }

    public <V> T getBySingleField(String fieldName, V target) {
        T t = null;
        Session session = HibernateUtils.getCurrentSession();
        try {
            session.getTransaction().begin();
            String sql = "select t from " + className + " t where t." + fieldName + "=:field";
            Query query = session.createQuery(sql);
            query.setParameter("field", target);
            t = (T) query.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            HibernateUtils.closeSession();
        }
        return t;
    }

    public <V> List<T> getListBySingleField(String fieldName, V target) {
        List<T> ts = null;
        Session session = HibernateUtils.getCurrentSession();
        try {
            session.getTransaction().begin();
            String sql = "select t from " + className + " t where t." + fieldName + "=:field";
            Query query = session.createQuery(sql);
            query.setParameter("field", target);
            ts = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            HibernateUtils.closeSession();
        }
        return ts;
    }

}
