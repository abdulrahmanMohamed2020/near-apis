package model.nfts;

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
        "attr_name",
        "attr_value"
})
public class Attribute {

    @JsonProperty("attr_name")
    private String attrName;
    @JsonProperty("attr_value")
    private String attrValue;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("attr_name")
    public String getAttrName() {
        return attrName;
    }

    @JsonProperty("attr_name")
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    @JsonProperty("attr_value")
    public String getAttrValue() {
        return attrValue;
    }

    @JsonProperty("attr_value")
    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
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
