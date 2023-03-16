package net.dev4any1.tbot.model;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Field.Write;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

public class UpdateEntity implements Serializable {

	private static final long serialVersionUID = UpdateEntity.class.getName().hashCode();

	@Id
	@MongoId
	private String id;

	@Field(targetType = FieldType.STRING, write = Write.NON_NULL)
	private String update;

	public UpdateEntity(String update) {
		this.update = update;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpdate() {
		return update;
	}

	public void setUpdate(String update) {
		this.update = update;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, update);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdateEntity other = (UpdateEntity) obj;
		return Objects.equals(id, other.id) && Objects.equals(update, other.update);
	}

	@Override
	public String toString() {
		return "UpdateDoc [id=" + id + ", update=" + update + "]";
	}

}
