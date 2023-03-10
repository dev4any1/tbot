package net.dev4any1.tbot.model;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Field.Write;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

public class Subscriber implements Serializable{

	private static final long serialVersionUID = Subscriber.class.getName().hashCode();

	@Id
	@MongoId
	private String id;

	@Indexed(unique = true)
	@Field(targetType = FieldType.STRING, write = Write.NON_NULL)
	private String gsmNo;

	public Subscriber() {
	}

	public Subscriber(String gsmNo) {
		this.gsmNo = gsmNo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(gsmNo, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subscriber other = (Subscriber) obj;
		return Objects.equals(gsmNo, other.gsmNo) && Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Subscriber [id=" + id + ", gsmNo=" + gsmNo + "]";
	}

}
