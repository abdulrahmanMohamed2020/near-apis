package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "transaction_id",
        "sender_id",
        "recipient_id",
        "transaction_item_id",
        "transaction_value",
        "type",
        "status",
        "sender",
        "counterparty",
        "created",
        "updated",
        "formattedtime"
})
@Generated("jsonschema2pojo")
public class Transactions {

    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("sender_id")
    private String senderId;
    @JsonProperty("recipient_id")
    private List<String> recipientId = null;
    @JsonProperty("transaction_item_id")
    private String transactionItemId;
    @JsonProperty("transaction_value")
    private String transactionValue;
    @JsonProperty("type")
    private String type;
    @JsonProperty("status")
    private String status;
    @JsonProperty("sender")
    private Boolean sender;
    @JsonProperty("counterparty")
    private User counterparty;
    @JsonProperty("created")
    private Long created;
    @JsonProperty("updated")
    private Long updated;
    @JsonProperty("formattedtime")
    private String formattedtime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("transaction_id")
    public String getTransactionId() {
        return transactionId;
    }

    @JsonProperty("transaction_id")
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @JsonProperty("sender_id")
    public String getSenderId() {
        return senderId;
    }

    @JsonProperty("sender_id")
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @JsonProperty("recipient_id")
    public List<String> getRecipientId() {
        return recipientId;
    }

    @JsonProperty("recipient_id")
    public void setRecipientId(List<String> recipientId) {
        this.recipientId = recipientId;
    }

    @JsonProperty("transaction_item_id")
    public String getTransactionItemId() {
        return transactionItemId;
    }

    @JsonProperty("transaction_item_id")
    public void setTransactionItemId(String transactionItemId) {
        this.transactionItemId = transactionItemId;
    }

    @JsonProperty("transaction_value")
    public String getTransactionValue() {
        return transactionValue;
    }

    @JsonProperty("transaction_value")
    public void setTransactionValue(String transactionValue) {
        this.transactionValue = transactionValue;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("sender")
    public Boolean getSender() {
        return sender;
    }

    @JsonProperty("sender")
    public void setSender(Boolean sender) {
        this.sender = sender;
    }

    @JsonProperty("counterparty")
    public User getCounterparty() {
        return counterparty;
    }

    @JsonProperty("counterparty")
    public void setCounterparty(User counterparty) {
        this.counterparty = counterparty;
    }

    @JsonProperty("created")
    public Long getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(Long created) {
        this.created = created;
    }

    @JsonProperty("updated")
    public Long getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    @JsonProperty("formattedtime")
    public String getFormattedtime() {
        return formattedtime;
    }

    @JsonProperty("formattedtime")
    public void setFormattedtime(String formattedtime) {
        this.formattedtime = formattedtime;
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