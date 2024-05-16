package com.allanweber.customers;

public record CustomerAddress(String country, String postalCode, String houseNumber) {

    @Override
    public String toString() {
        return "CustomerAddress{" +
                "country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                '}';
    }
}
