package com.samsara.paladin.configuration.bean;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.samsara.paladin.model.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {BeanConfiguration.class})
class BeanConfigurationTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    void modelMapper() {
        User firstUser = User.builder()
                .id(1L)
                .username("Test")
                .email("test@mail.com")
                .build();

        User secondUser = modelMapper.map(firstUser, User.class);

        assertAll(
                "Grouped Assertions of User",
                () -> assertEquals(firstUser.getId(), secondUser.getId(), "User id should be '1L"),
                () -> assertEquals(firstUser.getUsername(), secondUser.getUsername(), "Username should be 'Test"),
                () -> assertEquals(firstUser.getEmail(), secondUser.getEmail(), "Email should be 'test@mail.com"),
                () -> assertNull(secondUser.getPassword(), "Password should be null")
        );
    }
}