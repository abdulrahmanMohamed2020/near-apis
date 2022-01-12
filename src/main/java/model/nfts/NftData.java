package model.nfts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;
import model.users.UserData;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "status",
        "created",
        "attributes",
        "owner_id",
        "nft_id",
        "updated",
        "action_type",
        "tracker",
        "category",
        "description",
        "file_url",
        "title",
        "owner"
})
public class NftData {

    @JsonProperty("status")
    private String status;
    @JsonProperty("created")
    private Long created;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty("attributes")
    private List<Attribute> attributes = null;
    @JsonProperty("owner_id")
    private String ownerId;
    @JsonProperty("nft_id")
    private String nftId;
    @JsonProperty("updated")
    private Long updated;
    @JsonProperty("action_type")
    private String actionType;
    @JsonProperty("tracker")
    private Tracker tracker;
    @JsonProperty("category")
    private String category;
    @JsonProperty("description")
    private String description;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("title")
    private String title;
    @JsonProperty("owner")
    private UserData owner;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("created")
    public Long getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(Long created) {
        this.created = created;
    }

    @JsonProperty("attributes")
    public List<Attribute> getAttributes() {
        return attributes;
    }

    @JsonProperty("attributes")
    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    @JsonProperty("owner_id")
    public String getOwnerId() {
        return ownerId;
    }

    @JsonProperty("owner_id")
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @JsonProperty("nft_id")
    public String getNftId() {
        return nftId;
    }

    @JsonProperty("nft_id")
    public void setNftId(String nftId) {
        this.nftId = nftId;
    }

    @JsonProperty("updated")
    public Long getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    @JsonProperty("action_type")
    public String getActionType() {
        return actionType;
    }

    @JsonProperty("action_type")
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    @JsonProperty("tracker")
    public Tracker getTracker() {
        return tracker;
    }

    @JsonProperty("tracker")
    public void setTracker(Tracker tracker) {
        this.tracker = tracker;
    }

    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("file_url")
    public String getFileUrl() {
        return fileUrl;
    }

    @JsonProperty("file_url")
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("owner")
    public UserData getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(UserData owner) {
        this.owner = owner;
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