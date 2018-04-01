package me.legrange.haveibeenpwned;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 * Each paste contains a number of attributes describing it. FIXME bad
 * description
 *
 * @author gideon
 */
public final class Paste {

    @SerializedName("Source")
    private String source;
    @SerializedName("Id")
    private String id;
    @SerializedName("Title")
    private String title;
    @SerializedName("Date")
    private Date date;
    @SerializedName("EmailCount")
    private int emailCount;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(int emailCount) {
        this.emailCount = emailCount;
    }

    @Override
    public String toString() {
        return "Paste{" + "source=" + source + ", id=" + id + ", title=" + title + ", date=" + date + ", emailCount=" + emailCount + '}';
    }
    
    

}
