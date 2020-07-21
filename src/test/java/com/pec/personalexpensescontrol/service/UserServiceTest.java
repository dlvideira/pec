package com.pec.personalexpensescontrol.service;

import com.pec.personalexpensescontrol.infra.security.User;
import com.pec.personalexpensescontrol.repository.UserManagementRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.pec.personalexpensescontrol.infra.security.Role.USER;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    UserManagementRepository userManagementRepository;
    @Mock
    ExpenseService expenseService;
    @Mock
    BankAccountService bankAccountService;
    @InjectMocks
    UserService userService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Before
    public void initialize(){
        MockitoAnnotations.initMocks(this);
    }

    private User createUserMock(){
        return User.create("testUser",
                "testPass",
                "testeEmail",
                USER,
                true);
    }

    @Test
    public void createAccount() {
        User userSut = createUserMock();

        User returnedUserMock = new User();
        returnedUserMock.setId("1111");

        when(userManagementRepository.save(any(User.class))).thenReturn(returnedUserMock);
        var saveResponseMock = userManagementRepository.save(userSut);

        doNothing().when(expenseService).initializeExpenses(saveResponseMock.getId());
        expenseService.initializeExpenses(saveResponseMock.getId());

        assertNotNull(userSut.getUsername());
        assertNotNull(userSut.getPassword());
        assertNotNull(userSut.getEmail());
        assertNotNull(saveResponseMock.getId());
        assertNotNull(saveResponseMock.getId());

        verify(expenseService, times(1)).initializeExpenses(saveResponseMock.getId());
        verify(userManagementRepository, times(1)).save(any(User.class));
    }

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenNewUserRequestIsNull() throws Exception {
        userService.createAccount(null);
    }

    @Test
    public void shouldReturnUserWithIdAfterSaveWhenCreateAnAccount() {
        //Arrange
        User returnedUserMock = new User();
        returnedUserMock.setId("1111");
        when(userManagementRepository.save(any(User.class))).thenReturn(returnedUserMock);

        //Act
        var saveResponseMock = userManagementRepository.save(returnedUserMock);

        //Assert
        assertNotNull(saveResponseMock.getId());
    }

    @Test
    public void shouldInitializeExpensesWhenCreateAnAccount() throws Exception {
        //Arrange
        User userMock = createUserMock();
        userMock.setId("1111");
        when(userManagementRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(userManagementRepository.save(any(User.class))).thenReturn(userMock);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPass");

        //Act
        userService.createAccount(userMock);

        //Assert
        verify(expenseService, times(1)).initializeExpenses(userMock.getId());
    }

    @Test
    public void shouldInitializeBanksWhenCreateAnAccount() throws Exception {
        //Arrange
        User userMock = createUserMock();
        userMock.setId("1111");
        when(userManagementRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        when(userManagementRepository.save(any(User.class))).thenReturn(userMock);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPass");

        //Act
        userService.createAccount(userMock);

        //Assert
        verify(bankAccountService, times(1)).initializeBankAccounts(userMock.getId());
    }

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

    @Test(expected = MockitoException.class)
    public void should_throw_emailConflictException_emailExist() {
        when(userService.emailExist(any(String.class))).thenReturn(true);
        if (userService.emailExist(""))
            throw new MockitoException("emailConflictException");
    }
}