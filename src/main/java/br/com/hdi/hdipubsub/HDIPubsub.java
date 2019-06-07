package br.com.hdi.hdipubsub;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "hdipubsub")
public class HDIPubsub {

    @Id
    private String id;
    private String messageId;
    private String data;

    public HDIPubsub() {}

    public HDIPubsub(String messageId, String data) {
        this.messageId = messageId;
        this.data = data;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HDIPubsub{" +
                "id='" + id + '\'' +
                ", messageId='" + messageId + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

}
