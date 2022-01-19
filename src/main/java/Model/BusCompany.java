package Model;

public class BusCompany extends BaseEntity<Long>{

    private String name;

    public BusCompany() {
    }

    public BusCompany(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
