package com.example.accessingdatamysql.user;

import com.example.accessingdatamysql.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}