package com.dcservice.persistence.models.base;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

@MappedSuperclass
public class UUIDModel extends BaseModel {

    @Column(unique = true, nullable = false, columnDefinition = "uuid")
    @Type(type = "persistence.types.UUIDType")
    protected String uuid = UUID.randomUUID().toString();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Serializable getIdentifier() {
        return this.getUuid();
    }

    public boolean isNew() {
        if (getId() == null || getId() <= 0) {
            return true;
        }

        return false;
    }

    public long getLastModified() {
        if (getUpdatedDate() != null) {
//            return getUpdatedDate().getTime();
            return getUpdatedDate().toInstant().toEpochMilli();
        }

        if (getCreatedDate() != null) {
//            return getCreatedDate().getTime();
            return getCreatedDate().toInstant().toEpochMilli();
        }

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UUIDModel)) return false;

        UUIDModel uuidModel = (UUIDModel) o;

        return getUuid().equals(uuidModel.getUuid());

    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }
}
