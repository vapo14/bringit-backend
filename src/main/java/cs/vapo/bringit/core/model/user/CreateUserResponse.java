package cs.vapo.bringit.core.model.user;

public class CreateUserResponse {

    private long userId;

    private String jwt;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
