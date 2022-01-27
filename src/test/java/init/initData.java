package init;

import by.kosolobov.barbershop.exception.DaoException;
import by.kosolobov.barbershop.model.dao.impl.UserDaoImpl;
import by.kosolobov.barbershop.model.entity.User;
import by.kosolobov.barbershop.util.Cryptographer;
import org.junit.jupiter.api.Test;

public class initData {
    @Test
    void add_users() throws DaoException {
        User user = new User();
        user.setUsername("alexkos1234");
        String password = Cryptographer.getSaltedHash("12345678").get();
        user.setUserRole(User.Role.CLIENT);
        user.setFirstName("Alex");
        user.setSecondName("Kos");
        user.setSurname("Petr");
        user.setEmail("s.k@m.r");
        user.setPhone("+123(45)678-90-90");
        user.setDescription("Horny");
        UserDaoImpl.getInstance().insertUser(user, password);
    }

    @Test
    void add_books() {

    }

    @Test
    void add_certs() {

    }

    @Test
    void add_services() {

    }
}
