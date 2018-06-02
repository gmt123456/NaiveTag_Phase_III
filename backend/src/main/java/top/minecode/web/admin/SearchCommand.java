package top.minecode.web.admin;

/**
 * Created on 2018/6/2.
 * Description:
 * @author Liao
 */
public class SearchCommand {

    private String key;
    private String userType;
    private int page;
    private int pageSize;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
