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
        "updated",
        "user_id",
        "wallet_status",
        "status",
        "created",
        "full_name",
        "wallet_id",
        "email",
        "phone",
        "verified"
})
@Getter
@Setter
public class UserData {

    @JsonProperty("updated")
    private Long updated;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("wallet_status")
    private String walletStatus;
    @JsonProperty("status")
    private String status;
    @JsonProperty("created")
    private Long created;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("wallet_id")
    private String walletName;
    @JsonProperty("walletName")
    private String walletId = walletName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("verified")
    private Boolean verified;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
