package maelton.casal.vehicle_rental_api.api.v1.enums;

public enum WheelType {
    TUBE_TYPE_WHEEL("Tube-type"), TUBELESS_WHELL("Spoked Wheels");
    
    final String description;
    
    WheelType(String description) {
        this.description = description;
    }
}
