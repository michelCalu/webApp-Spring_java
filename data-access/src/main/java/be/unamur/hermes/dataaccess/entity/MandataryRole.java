package be.unamur.hermes.dataaccess.entity;

import java.util.Objects;

public class MandataryRole {

    private long id;
    private String name;

    public MandataryRole() {
    }

    public MandataryRole(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MandataryRole)) return false;
        MandataryRole that = (MandataryRole) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "MandataryRole{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}