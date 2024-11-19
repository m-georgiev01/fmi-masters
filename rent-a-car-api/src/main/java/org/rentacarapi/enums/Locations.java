package org.rentacarapi.enums;

public enum Locations {
    PLOVDIV(1),
    SOFIA(2),
    VARNA(3),
    BURGAS(4);

    private final int dbId;

    Locations(int dbId) {
        this.dbId = dbId;
    }

    public int getDbId() {
        return dbId;
    }

    public static Locations fromString(String cityName) {
        for (Locations location : Locations.values()) {
            if (location.name().equalsIgnoreCase(cityName)) {
                return location;
            }
        }
        return null;
    }
}