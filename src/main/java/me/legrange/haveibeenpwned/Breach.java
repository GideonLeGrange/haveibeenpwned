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

    @SerializedName("Name")
    private String name;
    @SerializedName("Title")
    private String title;
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
    @SerializedName("LogoPath")
    private String logoPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean isFabricated() {
        return isFabricated;
    }

    public void setFabricated(boolean fabricated) {
        isFabricated = fabricated;
    }

    public boolean isSensitive() {
        return isSensitive;
    }

    public void setSensitive(boolean sensitive) {
        isSensitive = sensitive;
    }

    public boolean isRetired() {
        return isRetired;
    }

    public void setRetired(boolean retired) {
        isRetired = retired;
    }

    public boolean isSpamList() {
        return isSpamList;
    }

    public void setSpamList(boolean spamList) {
        isSpamList = spamList;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    @Override
    public String toString() {
        return "Breach{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", domain='" + domain + '\'' +
                ", breachDate=" + breachDate +
                ", addedDate=" + addedDate +
                ", modifiedDate=" + modifiedDate +
                ", pwnCount=" + pwnCount +
                ", description='" + description + '\'' +
                ", dataClasses=" + dataClasses +
                ", isVerified=" + isVerified +
                ", isFabricated=" + isFabricated +
                ", isSensitive=" + isSensitive +
                ", isRetired=" + isRetired +
                ", isSpamList=" + isSpamList +
                ", logoPath='" + logoPath + '\'' +
                '}';
    }
}
