package Account;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.AccountDTO;
import com.sax.dtos.KhachHangDTO;
import com.sax.entities.Account;
import com.sax.repositories.IAccountRepository;
import com.sax.services.impl.AccountService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SuaAC {
    private IAccountRepository repository;
    private AccountService accountService;

    @Before
    public void setUp() {
        repository = mock(IAccountRepository.class);
        accountService = new AccountService();
        accountService.setRepo(repository);
    }

    @Test
    public void testUpdate_Success() throws SQLServerException, IOException {
        // Arrange
        AccountDTO accountDTO = new AccountDTO("name","tuoi",true,"123","123","123","123", LocalDateTime.now(),true,true);
        Account account = new Account(1,"name","tuoi","ada","123","123","123",LocalDateTime.now(),false,true,true,new HashSet<>());
        when(repository.findById(anyInt())).thenReturn(java.util.Optional.of(account));
        when(repository.save(any(Account.class))).thenReturn(account);

        // Act
        KhachHangDTO result = accountService.update(accountDTO);

        // Assert
        assertNotNull(result);
        // Add additional assertions based on your requirements
    }

    @Test(expected = RuntimeException.class)
    public void testUpdate_AccountNotFound() throws SQLServerException, IOException {
        // Arrange
        AccountDTO accountDTO = new AccountDTO("name","tuoi",true,"123","123","123","123",LocalDateTime.now(),true,true);
        when(repository.findById(anyInt())).thenReturn(java.util.Optional.empty());

        // Act
        accountService.update(accountDTO);

        // Assert
        // Exception expected
    }

    @Test(expected = RuntimeException.class)
    public void testUpdate_UsernameAlreadyExists() throws SQLServerException, IOException {
        // Arrange
        AccountDTO accountDTO = new AccountDTO("name","tuoi",true,"123","123","123","123",LocalDateTime.now(),true,true);
        Account account = new Account(1,"name","tuoi","ada","123","123","123",LocalDateTime.now(),false,true,true,new HashSet<>());
        when(repository.findById(anyInt())).thenReturn(java.util.Optional.of(account));
        when(repository.findByUsername(anyString())).thenReturn(new Account());

        // Act
        accountService.update(accountDTO);

        // Assert
        // Exception expected
    }
}
