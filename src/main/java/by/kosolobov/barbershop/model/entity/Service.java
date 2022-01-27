package by.kosolobov.barbershop.model.entity;

import java.util.StringJoiner;

public class Service {
    private String serviceName;
    private String barberUsername;
    private double price;

    public Service(String serviceName) {
        this.serviceName = serviceName;
    }

    public Service(String serviceName, String barberUsername, double price) {
        this.serviceName = serviceName;
        this.barberUsername = barberUsername;
        this.price = price;
    }

    public Service() {
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getBarberUsername() {
        return barberUsername;
    }

    public void setBarberUsername(String barberUsername) {
        this.barberUsername = barberUsername;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Service service = (Service) o;

        if (Double.compare(service.price, price) != 0) return false;
        if (serviceName != null ? !serviceName.equals(service.serviceName) : service.serviceName != null) return false;
        return barberUsername != null ? barberUsername.equals(service.barberUsername) : service.barberUsername == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = serviceName != null ? serviceName.hashCode() : 0;
        result = 31 * result + (barberUsername != null ? barberUsername.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Service.class.getSimpleName() + "[", "]")
                .add("serviceName='" + serviceName + "'")
                .add("barberUsername='" + barberUsername + "'")
                .add("price=" + price)
                .toString();
    }
}
