package co.edu.poli.ces3.software3.dbo;

public enum StatusEnum {

    ACTIVE(1, "active"),
    INNACTIVE(2, "inactive");

    private final int id;
    private final String description;

    StatusEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }




}
