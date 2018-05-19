package top.minecode.service.user;

/**
 * Created on 2018/5/19.
 * Description:
 * @author Liao
 */
public class EmailVerificationResponse {

    private String status;

    static EmailVerificationResponse duplicate() {
        return new EmailVerificationResponse("duplicate");
    }

    static EmailVerificationResponse invalid() {
        return new EmailVerificationResponse("invalid");
    }

    static EmailVerificationResponse valid() {
        return new EmailVerificationResponse("valid");
    }

    private EmailVerificationResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
