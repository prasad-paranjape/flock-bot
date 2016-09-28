package core;

/**
 * Created by shashwat.ku on 25/9/16.
 */
public class AgentReplyMessage {
    private String type;
    private String id;
    private String to;
    private String from;
    private String actor;
    private String text;
    private String uid;

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getActor() {
        return actor;
    }

    public String getText() {
        return text;
    }

    public String getUid() {
        return uid;
    }
}
