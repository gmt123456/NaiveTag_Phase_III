package top.minecode.dao.staff;

import org.springframework.stereotype.Repository;
import top.minecode.dao.utils.CommonOperation;
import top.minecode.po.admin.StaffPO;

/**
 * Created on 2018/6/1.
 * Description:
 *
 * @author iznauy
 */
@Repository
public class StaffDao {

    private CommonOperation<StaffPO> staffHelper = new CommonOperation<>(StaffPO.class);

    public StaffPO getStaffByEmail(String email) {
        return staffHelper.getBySingleField("email", email);
    }

    public void updateStaff(StaffPO staffPO) {
        staffHelper.update(staffPO);
    }

    public void addStaff(StaffPO staffPO) {
        staffHelper.add(staffPO);
    }

}
