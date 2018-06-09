package top.minecode.dao.user;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.domain.user.UserType;
import top.minecode.domain.user.admin.AdminAuthority;
import top.minecode.po.admin.AdminPO;
import top.minecode.po.admin.StaffPO;

/**
 * Created on 2018/5/29.
 * Description:
 * @author Liao
 */
@Repository("adminDaoImpl")
public class AdminDaoImpl implements AdminDao {

    private CommonOperation<AdminPO> adminOperation = new CommonOperation<>(AdminPO.class);
    private CommonOperation<StaffPO> staffOperation = new CommonOperation<>(StaffPO.class);

    @Override
    public boolean exists(String admin) {
        return adminOperation.getBySingleField("userName", admin) != null;
    }

    @Override
    public boolean hasHighestAuthority(String username) {
        AdminPO adminPO = adminOperation.getBySingleField("userName", username);
        return adminPO.getAuthority() == AdminAuthority.SUPREME;
    }

    @Override
    public boolean addStaff(String email, String password) {
        // Check duplicate
        StaffPO staffPO = staffOperation.getBySingleField("email", email);
        if (staffPO != null)
            return false;

        staffPO = new StaffPO(email, password);
        return staffOperation.add(staffPO);
    }

    @Override
    public boolean addAdmin(String username, String password) {
        // All new administrator will be assigned COMMON authority automatically
        AdminPO adminPO = new AdminPO(username, password, AdminAuthority.COMMON);
        return adminOperation.add(adminPO);
    }

    @Override
    public String getUserType(String identity) {

        AdminPO adminPO = adminOperation.getBySingleField("userName", identity);
        if (adminPO != null) {
            return "admin";
        }
        return "staff";
    }
}
