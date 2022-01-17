package by.kosolobov.barbershop.model.entity;

import java.util.StringJoiner;

public class User {
    private final int userId;
    private final String username;
    private final Role userRole;
    private final String firstName;
    private final String secondName;
    private final String surName;
    private final String email;
    private final String phone;
    private final String description;

    public User(int userId,
                String username,
                Role userRole,
                String firstName,
                String secondName,
                String surName,
                String email,
                String phone,
                String description) {
        this.userId = userId;
        this.username = username;
        this.userRole = userRole;
        this.firstName = firstName;
        this.secondName = secondName;
        this.surName = surName;
        this.email = email;
        this.phone = phone;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public Role getUserRole() {
        return userRole;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getSurName() {
        return surName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (userRole != user.userRole) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (secondName != null ? !secondName.equals(user.secondName) : user.secondName != null) return false;
        if (surName != null ? !surName.equals(user.surName) : user.surName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        return description != null ? description.equals(user.description) : user.description == null;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (userRole != null ? userRole.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (surName != null ? surName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("userId=" + userId)
                .add("username='" + username + "'")
                .add("userRole=" + userRole)
                .add("firstName='" + firstName + "'")
                .add("secondName='" + secondName + "'")
                .add("surName='" + surName + "'")
                .add("email='" + email + "'")
                .add("phone='" + phone + "'")
                .add("description='" + description + "'")
                .toString();
    }

    public static class UserBuilder {
        private int userId;
        private String username;
        private Role userRole;
        private String firstName;
        private String secondName;
        private String surName;
        private String email;
        private String phone;
        private String description;

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setUserRole(Role userRole) {
            this.userRole = userRole;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setSecondName(String secondName) {
            this.secondName = secondName;
        }

        public void setSurName(String surName) {
            this.surName = surName;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public User build() {
            return new User(userId,
                            username,
                            userRole,
                            firstName,
                            secondName,
                            surName,
                            email,
                            phone,
                            description);
        }
    }

    public enum Role {
        CLIENT,
        BARBER
    }
}
