package model.users;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message",
        "user_info",
        "jwt_access_token",
        "jwt_id_token",
        "jwt_refresh_token"
})
public class User {

    @JsonProperty("message")
    private String message;
    @JsonProperty("user_info")
    private UserData data;
    @JsonProperty("jwt_access_token")
    private String jwtAccessToken;
    @JsonProperty("jwt_id_token")
    private String jwtIdToken;
    @JsonProperty("jwt_refresh_token")
    private String jwtRefreshToken;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("data")
    public UserData getUserData() {
        return data;
    }

    @JsonProperty("data")
    public void setUserData(UserData data) {
        this.data = data;
    }

    @JsonProperty("jwt_access_token")
    public String getJwtAccessToken() {
        return jwtAccessToken;
    }

    @JsonProperty("jwt_access_token")
    public void setJwtAccessToken(String jwtAccessToken) {
        this.jwtAccessToken = jwtAccessToken;
    }

    @JsonProperty("jwt_id_token")
    public String getJwtIdToken() {
        return jwtIdToken;
    }

    @JsonProperty("jwt_id_token")
    public void setJwtIdToken(String jwtIdToken) {
        this.jwtIdToken = jwtIdToken;
    }

    @JsonProperty("jwt_refresh_token")
    public String getJwtRefreshToken() {
        return jwtRefreshToken;
    }

    @JsonProperty("jwt_refresh_token")
    public void setJwtRefreshToken(String jwtRefreshToken) {
        this.jwtRefreshToken = jwtRefreshToken;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
