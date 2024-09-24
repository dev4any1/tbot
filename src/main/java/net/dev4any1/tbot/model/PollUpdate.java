package net.dev4any1.tbot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.data.mongodb.core.mapping.TimeSeries;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@TimeSeries(collection="pollUpdate", timeField = "timestamp")
public class PollUpdate implements Serializable {
    private static final long serialVersionUID = PollUpdate.class.getName().hashCode();

    @Id
    @MongoId
    private String id;

    @Field(targetType = FieldType.STRING, write = Field.Write.NON_NULL)
    private Instant timestamp;
    
    @Indexed(unique = true)
    @Field(targetType = FieldType.STRING, write = Field.Write.NON_NULL)
    private String pollId;

    @Field(targetType = FieldType.STRING, write = Field.Write.NON_NULL)
    private String poll;

    public PollUpdate(String pollId, String poll, Instant timestamp) {
        this.pollId = pollId;
        this.poll = poll;
        this.setTimestamp(timestamp);
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
        PollUpdate other = (PollUpdate) obj;
        return Objects.equals(id, other.id) && Objects.equals(poll, other.poll) && Objects.equals(pollId, other.pollId);
    }

    @Override
    public String toString() {
        return "PollUpdate [id=" + id + ", pollId=" + pollId + ", poll=" + poll + "]";
    }

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

}
