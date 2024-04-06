package cs.vapo.bringit.core.model.user;

public class CreateUserResponse {

    private String userId;

    private String jwt;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
