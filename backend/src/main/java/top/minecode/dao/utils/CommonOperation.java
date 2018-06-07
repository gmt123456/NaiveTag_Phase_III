package top.minecode.dao.utils;

import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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

    public T executeHQL(String sql) {
        T t = null;
        Session session = HibernateUtils.getCurrentSession();
        try {
            session.getTransaction().begin();
            Query query = session.createQuery(sql);
            t = (T)query.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            HibernateUtils.closeSession();
        }
        return t;
    }

    public void batchUpdate(List<T> tpo) {
        Session session = HibernateUtils.getCurrentSession();
        try {
            session.getTransaction().begin();
            for (T t: tpo) {
                session.update(t);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            HibernateUtils.closeSession();
        }
    }

    public List<T> executeSQL(String sql, Object... params) {
        List<T> ts = null;
        Session session = HibernateUtils.getCurrentSession();
        try {
            session.getTransaction().begin();
            Query query = session.createQuery(sql);
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

    public <V> List<T> getValuesInSpecificSet(List<V> set, String fieldName) {
        if (set.size() == 0)
            return new ArrayList<>();
        Session session = HibernateUtils.getCurrentSession();
        List<T> ts = null;
        try {
            session.getTransaction().begin();
            String hql = "from " + className + " t where t." + fieldName + " in (:se)";
            Query query = session.createQuery(hql);
            query.setParameterList("se", set);
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

    public static <R> List<R> executeSQL(final Class<R> clazz, final String sql, final Object... values) {
        List<R> ts = null;
        Session session = HibernateUtils.getCurrentSession();
        try {
            session.getTransaction().begin();
            Query query = session.createQuery(sql);
            if (values != null) {
                for(int i = 0; i < values.length; ++i) {
                    query.setParameter(i, values[i]);
                }
            }
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

    public static <R> R template(Function<Session, R> supplier) {
        R result = null;
        Session session = HibernateUtils.getCurrentSession();
        try {
            session.getTransaction().begin();
            result = supplier.apply(session);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            HibernateUtils.closeSession();
        }
        return result;
    }

    public static boolean template(Consumer<Session> consumer) {
        Session session = HibernateUtils.getCurrentSession();
        try {
            session.getTransaction().begin();
            consumer.accept(session);
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

    public void delete(T tpo) {
        Session session = HibernateUtils.getCurrentSession();
        try {
            session.getTransaction().begin();
            session.delete(tpo);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            HibernateUtils.closeSession();
        }
    }

}
