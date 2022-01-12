package model.transactions;

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
        "sender_id",
        "blockchain_status",
        "transaction_value",
        "updated",
        "recipient_id",
        "created",
        "transaction_id",
        "transaction_item_id",
        "type",
        "status",
        "formattedtime",
        "counterparty"
})
public class TransactionsData {

    @JsonProperty("sender_id")
    private String senderId;
    @JsonProperty("blockchain_status")
    private String blockchainStatus;
    @JsonProperty("transaction_value")
    private String transactionValue;
    @JsonProperty("updated")
    private Long updated;
    @JsonProperty("recipient_id")
    private String recipientId;
    @JsonProperty("created")
    private Long created;
    @JsonProperty("transaction_id")
    private String transactionId;
    @JsonProperty("transaction_item_id")
    private String transactionItemId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("status")
    private String status;
    @JsonProperty("formattedtime")
    private String formattedtime;
    @JsonProperty("counterparty")
    private Counterparty counterparty;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("sender_id")
    public String getSenderId() {
        return senderId;
    }

    @JsonProperty("sender_id")
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    @JsonProperty("blockchain_status")
    public String getBlockchainStatus() {
        return blockchainStatus;
    }

    @JsonProperty("blockchain_status")
    public void setBlockchainStatus(String blockchainStatus) {
        this.blockchainStatus = blockchainStatus;
    }

    @JsonProperty("transaction_value")
    public String getTransactionValue() {
        return transactionValue;
    }

    @JsonProperty("transaction_value")
    public void setTransactionValue(String transactionValue) {
        this.transactionValue = transactionValue;
    }

    @JsonProperty("updated")
    public Long getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    @JsonProperty("recipient_id")
    public String getRecipientId() {
        return recipientId;
    }

    @JsonProperty("recipient_id")
    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    @JsonProperty("created")
    public Long getCreated() {
        return created;
    }

    @JsonProperty("created")
    public void setCreated(Long created) {
        this.created = created;
    }

    @JsonProperty("transaction_id")
    public String getTransactionId() {
        return transactionId;
    }

    @JsonProperty("transaction_id")
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @JsonProperty("transaction_item_id")
    public String getTransactionItemId() {
        return transactionItemId;
    }

    @JsonProperty("transaction_item_id")
    public void setTransactionItemId(String transactionItemId) {
        this.transactionItemId = transactionItemId;
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

    @JsonProperty("formattedtime")
    public String getFormattedtime() {
        return formattedtime;
    }

    @JsonProperty("formattedtime")
    public void setFormattedtime(String formattedtime) {
        this.formattedtime = formattedtime;
    }

    @JsonProperty("counterparty")
    public Counterparty getCounterparty() {
        return counterparty;
    }

    @JsonProperty("counterparty")
    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(TransactionsData.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("senderId");
        sb.append('=');
        sb.append(((this.senderId == null)?"<null>":this.senderId));
        sb.append(',');
        sb.append("blockchainStatus");
        sb.append('=');
        sb.append(((this.blockchainStatus == null)?"<null>":this.blockchainStatus));
        sb.append(',');
        sb.append("transactionValue");
        sb.append('=');
        sb.append(((this.transactionValue == null)?"<null>":this.transactionValue));
        sb.append(',');
        sb.append("updated");
        sb.append('=');
        sb.append(((this.updated == null)?"<null>":this.updated));
        sb.append(',');
        sb.append("recipientId");
        sb.append('=');
        sb.append(((this.recipientId == null)?"<null>":this.recipientId));
        sb.append(',');
        sb.append("created");
        sb.append('=');
        sb.append(((this.created == null)?"<null>":this.created));
        sb.append(',');
        sb.append("transactionId");
        sb.append('=');
        sb.append(((this.transactionId == null)?"<null>":this.transactionId));
        sb.append(',');
        sb.append("transactionItemId");
        sb.append('=');
        sb.append(((this.transactionItemId == null)?"<null>":this.transactionItemId));
        sb.append(',');
        sb.append("type");
        sb.append('=');
        sb.append(((this.type == null)?"<null>":this.type));
        sb.append(',');
        sb.append("formattedtime");
        sb.append('=');
        sb.append(((this.formattedtime == null)?"<null>":this.formattedtime));
        sb.append(',');
        sb.append("counterparty");
        sb.append('=');
        sb.append(((this.counterparty == null)?"<null>":this.counterparty));
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append(((this.additionalProperties == null)?"<null>":this.additionalProperties));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}