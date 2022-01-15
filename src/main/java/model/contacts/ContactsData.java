package model.contacts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "contact_id",
        "job_title",
        "status",
        "created",
        "companies",
        "address",
        "email",
        "owner_id",
        "archived_date",
        "app_id",
        "groups",
        "updated",
        "import_source",
        "last_name",
        "dob",
        "first_name",
        "phone",
        "birthday"
})
public class ContactsData {

    @JsonProperty("contact_id")
    private String contactId;
    @JsonProperty("job_title")
    private String jobTitle;
    @JsonProperty("status")
    private String status;
    @JsonProperty("created")
    private Long created;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty("companies")
    private List<String> companies = null;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty("address")
    private List<Address> address = null;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty("email")
    private List<Email> email = null;
    @JsonProperty("owner_id")
    private String ownerId;
    @JsonProperty("archived_date")
    private String archivedDate;
    @JsonProperty("app_id")
    private String appId;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty("groups")
    private List<String> groups = null;
    @JsonProperty("updated")
    private Long updated;
    @JsonProperty("import_source")
    private String importSource;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("first_name")
    private String firstName;
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JsonProperty("phone")
    private List<Phone> phone = null;
    @JsonProperty("birthday")
    private String birthday;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("contact_id")
    public String getContactId() {
        return contactId;
    }

    @JsonProperty("contact_id")
    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    @JsonProperty("job_title")
    public String getJobTitle() {
        return jobTitle;
    }

    @JsonProperty("job_title")
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

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

    @JsonProperty("companies")
    public List<String> getCompanies() {
        return companies;
    }

    @JsonProperty("companies")
    public void setCompanies(List<String> companies) {
        this.companies = companies;
    }

    @JsonProperty("address")
    public List<Address> getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(List<Address> address) {
        this.address = address;
    }

    @JsonProperty("email")
    public List<Email> getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(List<Email> email) {
        this.email = email;
    }

    @JsonProperty("owner_id")
    public String getOwnerId() {
        return ownerId;
    }

    @JsonProperty("owner_id")
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @JsonProperty("archived_date")
    public String getArchivedDate() {
        return archivedDate;
    }

    @JsonProperty("archived_date")
    public void setArchivedDate(String archivedDate) {
        this.archivedDate = archivedDate;
    }

    @JsonProperty("app_id")
    public String getAppId() {
        return appId;
    }

    @JsonProperty("app_id")
    public void setAppId(String appId) {
        this.appId = appId;
    }

    @JsonProperty("groups")
    public List<String> getGroups() {
        return groups;
    }

    @JsonProperty("groups")
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    @JsonProperty("updated")
    public Long getUpdated() {
        return updated;
    }

    @JsonProperty("updated")
    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    @JsonProperty("import_source")
    public String getImportSource() {
        return importSource;
    }

    @JsonProperty("import_source")
    public void setImportSource(String importSource) {
        this.importSource = importSource;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("dob")
    public String getDob() {
        return dob;
    }

    @JsonProperty("dob")
    public void setDob(String dob) {
        this.dob = dob;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("phone")
    public List<Phone> getPhone() {
        return phone;
    }

    @JsonProperty("phone")
    public void setPhone(List<Phone> phone) {
        this.phone = phone;
    }

    @JsonProperty("birthday")
    public String getBirthday() {
        return birthday;
    }

    @JsonProperty("birthday")
    public void setBirthday(String birthday) {
        this.birthday = birthday;
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