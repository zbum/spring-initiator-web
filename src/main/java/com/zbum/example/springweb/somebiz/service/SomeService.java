package com.zbum.example.springweb.somebiz.service;

import com.zbum.example.springweb.repository.User;

import java.util.List;

/**
 * @author Jibeom Jung
 */
public interface SomeService {
    List<User> findUser();
}
