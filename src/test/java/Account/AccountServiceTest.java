package Account;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.AccountDTO;
import com.sax.entities.Account;
import com.sax.repositories.IAccountRepository;
import com.sax.services.impl.AccountService;
import com.sax.utils.ImageUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private IAccountRepository repository;
    private AccountService accountService;

    @Before
    public void setUp() {
        repository = mock(IAccountRepository.class);
        accountService = new AccountService();
        accountService.setRepo(repository);
    }

    @Test
    public void testInsert_Success() throws SQLServerException, IOException {
        // Arrange
        AccountDTO accountDTO = new AccountDTO("name","tuoi",true,"123","123","123","123",LocalDateTime.now(),true,true);
        Account account = new Account(1,"name","tuoi","ada","123","123","123",LocalDateTime.now(),false,true,true,new HashSet<>());
        when(repository.save(any(Account.class))).thenReturn(account);

        // Act
        AccountDTO result = accountService.insert(accountDTO);

        // Assert
        assertNotNull(result);
        // Add additional assertions based on your requirements
    }

    @Test(expected = RuntimeException.class)
    public void testInsert_FailedToSave() throws SQLServerException, IOException {
        // Arrange
        AccountDTO accountDTO = new AccountDTO("name","tuoi",true,"123","123","123","123",LocalDateTime.now(),true,true);
        when(repository.save(any(Account.class))).thenThrow(new RuntimeException());

        // Act
        accountService.insert(accountDTO);

        // Assert
        // Exception expected
    }

    @Test(expected = RuntimeException.class)
    public void testInsert_FailedToSaveImage() throws SQLServerException, IOException {
        // Arrange
        AccountDTO accountDTO = new AccountDTO("name","tuoi",true,"123","123","123","123",LocalDateTime.now(),true,true);
        when(repository.save(any(Account.class))).thenReturn(new Account());
        doThrow(new IOException()).when(ImageUtils.class);
        // Add more mocking setup for ImageUtils if needed

        // Act
        accountService.insert(accountDTO);

        // Assert
        // Exception expected
    }

    // Add more test cases for various scenarios

}
