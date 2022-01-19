package Model;

import java.io.Serializable;

public abstract class BaseEntity<ID extends Serializable> {
    private ID id;

    protected BaseEntity() {
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
