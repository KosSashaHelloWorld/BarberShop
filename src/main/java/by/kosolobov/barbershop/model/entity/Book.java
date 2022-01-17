package by.kosolobov.barbershop.model.entity;

import java.sql.Date;
import java.sql.Time;
import java.util.StringJoiner;

public class Book {
    private final int clientId;
    private final int barberId;
    private final int serviceId;
    private final Date bookDate;
    private final Time bookTime;
    private final boolean isActive;

    public Book(int clientId, int barberId, int serviceId, Date bookDate, Time bookTime, boolean isActive) {
        this.clientId = clientId;
        this.barberId = barberId;
        this.serviceId = serviceId;
        this.bookDate = bookDate;
        this.bookTime = bookTime;
        this.isActive = isActive;
    }

    public int getClientId() {
        return clientId;
    }

    public int getBarberId() {
        return barberId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public Time getBookTime() {
        return bookTime;
    }

    public boolean isActive() {
        return isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (clientId != book.clientId) return false;
        if (barberId != book.barberId) return false;
        if (serviceId != book.serviceId) return false;
        if (isActive != book.isActive) return false;
        if (bookDate != null ? !bookDate.equals(book.bookDate) : book.bookDate != null) return false;
        return bookTime != null ? bookTime.equals(book.bookTime) : book.bookTime == null;
    }

    @Override
    public int hashCode() {
        int result = clientId;
        result = 31 * result + barberId;
        result = 31 * result + serviceId;
        result = 31 * result + (bookDate != null ? bookDate.hashCode() : 0);
        result = 31 * result + (bookTime != null ? bookTime.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]")
                .add("clientId=" + clientId)
                .add("barberId=" + barberId)
                .add("serviceId=" + serviceId)
                .add("bookDate=" + bookDate)
                .add("bookTime=" + bookTime)
                .add("isActive=" + isActive)
                .toString();
    }

    public static class BookBuilder {
        private int clientId;
        private int barberId;
        private int serviceId;
        private Date bookDate;
        private Time bookTime;
        private boolean isActive;

        public void setClientId(int clientId) {
            this.clientId = clientId;
        }

        public void setBarberId(int barberId) {
            this.barberId = barberId;
        }

        public void setServiceId(int serviceId) {
            this.serviceId = serviceId;
        }

        public void setBookDate(Date bookDate) {
            this.bookDate = bookDate;
        }

        public void setBookTime(Time bookTime) {
            this.bookTime = bookTime;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public Book build() {
            return new Book(clientId, barberId, serviceId, bookDate, bookTime, isActive);
        }
    }
}
