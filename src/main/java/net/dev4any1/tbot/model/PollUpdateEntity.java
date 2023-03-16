package net.dev4any1.tbot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.Objects;

public class PollUpdateEntity implements Serializable {
    private static final long serialVersionUID = PollUpdateEntity.class.getName().hashCode();

    @Id
    @MongoId
    private String id;

    @Indexed(unique = false)
    @Field(targetType = FieldType.STRING, write = Field.Write.NON_NULL)
    private String pollId;

    @Field(targetType = FieldType.STRING, write = Field.Write.NON_NULL)
    private String poll;

    public PollUpdateEntity(String pollId, String poll) {
        this.pollId = pollId;
        this.poll = poll;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoll() {
        return poll;
    }

    public void setPoll(String poll) {
        this.poll = poll;
    }
    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, poll);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PollUpdateEntity other = (PollUpdateEntity) obj;
        return Objects.equals(id, other.id) && Objects.equals(poll, other.poll) && Objects.equals(pollId, other.pollId);
    }

    @Override
    public String toString() {
        return "PollUpdate [id=" + id + ", pollId=" + pollId + ", poll=" + poll + "]";
    }

}
