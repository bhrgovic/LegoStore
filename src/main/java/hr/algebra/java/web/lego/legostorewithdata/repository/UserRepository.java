package hr.algebra.java.web.lego.legostorewithdata.repository;

import hr.algebra.java.web.lego.legostorewithdata.domain.User;

import java.util.List;

public interface UserRepository {

    void createUser();
    User getUser();
    List<User> getUsers();
    void deleteUser();
}
