package top.minecode.po.auto;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import top.minecode.po.worker.WorkerPO;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2018/5/28.
 * Description:
 *
 * @author iznauy
 */
@Entity
public class WorkerVectorPO {

    @Id
    private String email;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Double.class)
    @Fetch(FetchMode.SUBSELECT)
    private List<Double> vector;

    public WorkerVectorPO() {
    }

    public WorkerVectorPO(String email, List<Double> vector) {
        this.email = email;
        this.vector = vector;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Double> getVector() {
        return vector;
    }

    public void setVector(List<Double> vector) {
        this.vector = vector;
    }

    public static WorkerVectorPO fromWorkerEmail(String email) {
        List<Double> vector = new ArrayList<>();
        for (int i = 0; i < 15; i++) vector.add(0.0);
        return new WorkerVectorPO(email, vector);
    }

}
