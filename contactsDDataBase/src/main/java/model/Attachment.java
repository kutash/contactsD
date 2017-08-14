package model;

import java.util.Date;

public class Attachment {

    private Long attachId;
    private String attachName;
    private String attachPath;
    private String comment;
    private Date date;
    private Long contactId;

    public Attachment(){
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
