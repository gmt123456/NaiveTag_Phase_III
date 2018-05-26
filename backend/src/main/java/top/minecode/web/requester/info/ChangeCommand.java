package top.minecode.web.requester.info;

/**
 * Created on 2018/5/26.
 * Description:
 * @author Liao
 */
public interface ChangeCommand<T> {

    void change(T t) throws Exception;
}
