package top.minecode.web.requester.info;

/**
 * Created on 2018/5/23.
 * Description:
 * @author Liao
 */
public class PageCommand {

    private int page;
    private int pageSize;

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
}
