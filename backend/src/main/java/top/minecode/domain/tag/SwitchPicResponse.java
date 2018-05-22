package top.minecode.domain.tag;

/**
 * Created on 2018/5/22.
 * Description:
 *
 * @author iznauy
 */
public class SwitchPicResponse {

    public static final String NO_MORE = "no more picture";

    public static final String INVALID = "invalid url";

    private String url;

    private String description;

    public SwitchPicResponse() {
    }

    public SwitchPicResponse(String url, String description) {
        this.url = url;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
