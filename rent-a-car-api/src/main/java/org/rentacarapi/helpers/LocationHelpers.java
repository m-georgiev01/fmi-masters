package org.rentacarapi.helpers;

import org.rentacarapi.enums.Locations;

public class LocationHelpers {
    public static boolean isLocationAvailableByCityName(String cityName) {
        Locations locationEnum = Locations.fromString(cityName);

        return locationEnum != null;
    }

    public static boolean isLocationAvailableByCityId(int id) {
        for (Locations location : Locations.values()) {
            if (location.getDbId() == id) {
                return true;
            }
        }
        return false;
    }

    public static int getCityDbId(String location){
        Locations locationEnum = Locations.fromString(location);
        if (locationEnum != null) {
            return locationEnum.getDbId();
        }

        return -1;
    }
}
