package model;

import java.util.Date;

/**
 * Created by Galina on 22.03.2017.
 */
public class Attachment {

    private Long attachId;
    private String attachName;
    private String attachPath;
    private String comment;
    private Date date;
    private Long contactId;

    public Attachment(Long attachId, String attachName, String attachPath, String comment, Date date, Long contactId){
        this.attachId=attachId;
        this.attachName=attachName;
        this.attachPath=attachPath;
        this.comment=comment;
        this.date=date;
        this.contactId=contactId;

    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Date getDate() {

        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {

        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAttachPath() {

        return attachPath;
    }

    public void setAttachPath(String attachPath) {
        this.attachPath = attachPath;
    }

    public String getAttachName() {

        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public Long getAttachId() {

        return attachId;
    }

    public void setAttachId(Long attachId) {
        this.attachId = attachId;
    }
}