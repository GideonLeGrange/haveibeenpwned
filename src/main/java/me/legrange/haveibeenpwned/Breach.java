package me.legrange.haveibeenpwned;

import com.google.gson.annotations.SerializedName;
import java.util.Date;
import java.util.List;

/**
 * A HaveIBeenPwned breach 
 * 
 * @author gideon
 */
public final class Breach {

    @SerializedName("Title")
    private String title;
    @SerializedName("Name")
    private String name;
    @SerializedName("Domain")
    private String domain;
    @SerializedName("BreachDate")
    private Date breachDate;
    @SerializedName("AddedDate")
    private Date addedDate;
    @SerializedName("ModifiedDate")
    private Date modifiedDate;
    @SerializedName("PwnCount")
    private long pwnCount;
    @SerializedName("Description")
    private String description;
    @SerializedName("DataClasses")
    private List<String> dataClasses;
    @SerializedName("IsVerified")
    private boolean isVerified;
    @SerializedName("IsFabricated")
    private boolean isFabricated;
    @SerializedName("IsSensitive")
    private boolean isSensitive;
    @SerializedName("IsRetired")
    private boolean isRetired;
    @SerializedName("IsSpamList")
    private boolean isSpamList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Date getBreachDate() {
        return breachDate;
    }

    public void setBreachDate(Date breachDate) {
        this.breachDate = breachDate;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public long getPwnCount() {
        return pwnCount;
    }

    public void setPwnCount(long pwnCount) {
        this.pwnCount = pwnCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getDataClasses() {
        return dataClasses;
    }

    public void setDataClasses(List<String> dataClasses) {
        this.dataClasses = dataClasses;
    }

    public boolean isIsVerified() {
        return isVerified;
    }

    public void setIsVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public boolean isIsFabricated() {
        return isFabricated;
    }

    public void setIsFabricated(boolean isFabricated) {
        this.isFabricated = isFabricated;
    }

    public boolean isIsSensitive() {
        return isSensitive;
    }

    public void setIsSensitive(boolean isSensitive) {
        this.isSensitive = isSensitive;
    }

    public boolean isIsRetired() {
        return isRetired;
    }

    public void setIsRetired(boolean isRetired) {
        this.isRetired = isRetired;
    }

    public boolean isIsSpamList() {
        return isSpamList;
    }

    public void setIsSpamList(boolean isSpamList) {
        this.isSpamList = isSpamList;
    }

    @Override
    public String toString() {
        return "Breach{" + "title=" + title + ", name=" + name + ", domain=" + domain + ", breachDate=" + breachDate + ", addedDate=" + addedDate + ", modifiedDate=" + modifiedDate + ", pwnCount=" + pwnCount + ", description=" + description + ", dataClasses=" + dataClasses + ", isVerified=" + isVerified + ", isFabricated=" + isFabricated + ", isSensitive=" + isSensitive + ", isRetired=" + isRetired + ", isSpamList=" + isSpamList + '}';
    }


}
