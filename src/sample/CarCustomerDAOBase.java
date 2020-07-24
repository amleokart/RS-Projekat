package sample;

import java.sql.SQLException;

public interface CarCustomerDAOBase {

    boolean validate(String user, String password) throws SQLException;
}
