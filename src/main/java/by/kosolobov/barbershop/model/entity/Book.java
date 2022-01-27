package by.kosolobov.barbershop.model.entity;

import java.sql.Date;
import java.sql.Time;
import java.util.StringJoiner;

public class Book {
    private String clientUsername;
    private String barberUsername;
    private String serviceName;
    private Date date;
    private Time time;
    private BookState state;

    public Book() {
    }

    public Book(String clientUsername, String barberUsername, String serviceName, Date date, Time time, BookState state) {
        this.clientUsername = clientUsername;
        this.barberUsername = barberUsername;
        this.serviceName = serviceName;
        this.date = date;
        this.time = time;
        this.state = state;
    }

    public String getClient() {
        return clientUsername;
    }

    public void setClient(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getBarber() {
        return barberUsername;
    }

    public void setBarber(String barberUsername) {
        this.barberUsername = barberUsername;
    }

    public String getService() {
        return serviceName;
    }

    public void setService(String serviceName) {
        this.serviceName = serviceName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public BookState getState() {
        return state;
    }

    public void setState(BookState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (clientUsername != null ? !clientUsername.equals(book.clientUsername) : book.clientUsername != null)
            return false;
        if (barberUsername != null ? !barberUsername.equals(book.barberUsername) : book.barberUsername != null)
            return false;
        if (serviceName != null ? !serviceName.equals(book.serviceName) : book.serviceName != null) return false;
        if (date != null ? !date.equals(book.date) : book.date != null) return false;
        if (time != null ? !time.equals(book.time) : book.time != null) return false;
        return state == book.state;
    }

    @Override
    public int hashCode() {
        int result = clientUsername != null ? clientUsername.hashCode() : 0;
        result = 31 * result + (barberUsername != null ? barberUsername.hashCode() : 0);
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]")
                .add("clientUsername='" + clientUsername + "'")
                .add("barberUsername='" + barberUsername + "'")
                .add("serviceName='" + serviceName + "'")
                .add("date=" + date)
                .add("time=" + time)
                .add("state=" + state)
                .toString();
    }

    public enum BookState {
        ORDERED,
        PAYED,
        DONE,
        MISSED
    }
}
