package com.hotel.model;

public class HotelDTO {
    private String name;
    private Long addressId;

    public HotelDTO() {
    }

    public HotelDTO(String name, Long addressId) {
        this.name = name;
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
