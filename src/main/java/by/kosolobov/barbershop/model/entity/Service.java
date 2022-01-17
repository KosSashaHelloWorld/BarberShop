package by.kosolobov.barbershop.model.entity;

import java.util.StringJoiner;

public class Service {
    private final int serviceId;
    private final String serviceName;
    private int barberId = 0;
    private int cost = 0;

    public Service(int serviceId, String serviceName) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
    }

    public Service(int serviceId, String serviceName, int barberId, int cost) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.barberId = barberId;
        this.cost = cost;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public int getBarberId() {
        return barberId;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Service.class.getSimpleName() + "[", "]")
                .add("serviceId=" + serviceId)
                .add("serviceName='" + serviceName + "'")
                .add("barberId=" + barberId)
                .add("cost=" + cost)
                .toString();
    }
}
