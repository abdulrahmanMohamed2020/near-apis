package model.users;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message",
        "user_info",
        "jwt_access_token",
        "jwt_id_token",
        "jwt_refresh_token"
})
@Getter
@Setter
public class User {

    @JsonProperty("message")
    private String message;
    @JsonProperty("data")
    private UserData userData;
    @JsonProperty("user_info")
    private UserData user_info = userData;
    @JsonProperty("jwt_access_token")
    private String jwtAccessToken;
    @JsonProperty("jwt_id_token")
    private String jwtIdToken;
    @JsonProperty("jwt_refresh_token")
    private String jwtRefreshToken;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
