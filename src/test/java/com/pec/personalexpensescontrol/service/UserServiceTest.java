package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.infra.security.User;
import com.pec.personalexpensescontrol.repository.UserManagementRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    UserManagementRepository userManagementRepository;
    @Mock
    ExpenseService expenseService;
    @Mock
    UserService userService;

/*    @Test
    public void createAccount() {
        User user = User.create("testUser",
                "testPass",
                "testeEmail",
                USER,
                true);

        User returnedUserMock = new User();
        returnedUserMock.setId("1111");

        when(userManagementRepository.save(any(User.class))).thenReturn(returnedUserMock);
        var saveResponseMock = userManagementRepository.save(user);

        doNothing().when(expenseService).initializeExpenses(saveResponseMock.getId());
        expenseService.initializeExpenses(saveResponseMock.getId());

        assertNotNull(user.getUsername());
        assertNotNull(user.getPassword());
        assertNotNull(user.getEmail());
        assertNotNull(saveResponseMock.getId());
        assertNotNull(saveResponseMock.getId());

        verify(expenseService, times(1)).initializeExpenses(saveResponseMock.getId());
        verify(userManagementRepository, times(1)).save(any(User.class));
    }*/

    @Test
    public void updateEmail() {
        User userMock = new User();
        userMock.setEmail("oldEmail");

        when(userManagementRepository.findById(any(String.class))).thenReturn(Optional.of(new User()));
        var findResponseUserMock = userManagementRepository.findById("");
        findResponseUserMock.get().setEmail("newEmail");

        assertNotNull(findResponseUserMock);
        assertNotEquals(userMock.getEmail(), findResponseUserMock.get().getEmail());
        assertNotEquals(userMock, findResponseUserMock);

        verify(userManagementRepository, times(1)).findById(any(String.class));
    }

    @Test
    public void updateUsername() {
        User userMock = new User();
        userMock.setUsername("oldUsername");

        when(userManagementRepository.findById(any(String.class))).thenReturn(Optional.of(new User()));
        var findResponseUserMock = userManagementRepository.findById("");
        findResponseUserMock.get().setUsername("newUsername");

        assertNotNull(findResponseUserMock);
        assertNotEquals(userMock.getUsername(), findResponseUserMock.get().getUsername());
        assertNotEquals(userMock, findResponseUserMock);

        verify(userManagementRepository, times(1)).findById(any(String.class));
    }

    @Test
    public void updateUserActiveStatus() {
        when(userManagementRepository.findById(any(String.class))).thenReturn(Optional.of(new User()));
        var findResponseUserMock = userManagementRepository.findById("");
        findResponseUserMock.get().setActive(true);

        if (findResponseUserMock.get().isActive()) {
            findResponseUserMock.get().setActive(!findResponseUserMock.get().isActive());
        }

        assertNotSame(true, findResponseUserMock.get().isActive());
    }

    @Test
    public void should_throw_emailConflictException_emailExist() {
        when(userService.emailExist(any(String.class))).thenReturn(true);
        if (userService.emailExist(""))
            throw new MockitoException("emailConflictException");
    }
}