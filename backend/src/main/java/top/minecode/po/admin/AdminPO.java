package top.minecode.po.admin;

import org.hibernate.Query;
import org.hibernate.Session;
import top.minecode.dao.utils.HibernateUtils;
import top.minecode.domain.task.TaskType;
import top.minecode.domain.user.UserType;
import top.minecode.domain.user.admin.AdminAuthority;
import top.minecode.po.log.LoginLogPO;
import top.minecode.po.task.SpecificTaskPO;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created on 2018/5/16.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class AdminPO implements Serializable {

    @Id
    private String userName;

    private String password;

    @Enumerated(EnumType.STRING)
    private AdminAuthority authority;

    public AdminPO() {
    }

    public AdminPO(String userName, String password, AdminAuthority authority) {
        this.userName = userName;
        this.password = password;
        this.authority = authority;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AdminAuthority getAuthority() {
        return authority;
    }

    public void setAuthority(AdminAuthority authority) {
        this.authority = authority;
    }

    public static void main(String[] args) {
//        SpecificTaskPO task = new SpecificTaskPO();
//        task.setTaskType(TaskType.t_100);
//        task.setTaskDescription("23333");
//        task.setDollars(666);
//        List<String> labels = new ArrayList<>();
//        labels.add("OK");
//        labels.add("Mi Fans");
//        task.setLabels(labels);
//        List<Integer> subTasks = new ArrayList<>();
//        subTasks.add(3);
//        subTasks.add(2);
//        task.setSubTasks(subTasks);
//        Session session = HibernateUtils.getCurrentSession();
//        try {
//            session.getTransaction().begin();
//            session.persist(task);
//            session.flush();
//            session.getTransaction().commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            HibernateUtils.closeSession();
//        }
//        session = HibernateUtils.getCurrentSession();
        Session session = HibernateUtils.getCurrentSession();
        try {
            session.getTransaction().begin();
            Query query = session.createQuery("from " + SpecificTaskPO.class.getName());
            List<SpecificTaskPO> list = query.list();
            System.out.println(list.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
