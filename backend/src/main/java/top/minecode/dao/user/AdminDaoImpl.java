package top.minecode.dao.user;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.user.admin.AdminAuthority;
import top.minecode.po.admin.AdminPO;

/**
 * Created on 2018/5/29.
 * Description:
 * @author Liao
 */
@Repository("adminDaoImpl")
public class AdminDaoImpl implements AdminDao {

    private CommonOperation<AdminPO> adminOperation = new CommonOperation<>(AdminPO.class);

    @Override
    public boolean checkAuthority(String username) {
        AdminPO adminPO = adminOperation.getBySingleField("username", username);
        return adminPO.getAuthority() == AdminAuthority.SUPREME;
    }

    @Override
    public boolean addAdmin(String username, String password) {
        // All new administrator will be assigned COMMON authority automatically
        AdminPO adminPO = new AdminPO(username, password, AdminAuthority.COMMON);
        return adminOperation.add(adminPO);
    }
}
