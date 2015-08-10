/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zbum.example.springweb.somebiz.service.impl;

import com.zbum.example.springweb.repository.User;
import com.zbum.example.springweb.repository.UserRepository;
import com.zbum.example.springweb.somebiz.service.SomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jibeom Jung
 */
@Service
public class SomeServiceImpl implements SomeService{

    @Autowired
    private UserRepository userRepository;

    public List<User> findUser() {
        return userRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
    }
}
