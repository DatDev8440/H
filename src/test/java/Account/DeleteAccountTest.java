package Account;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.entities.Account;
import com.sax.repositories.IAccountRepository;
import com.sax.services.impl.AccountService;
import org.junit.Test;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DeleteAccountTest {

    @Test
    public void testDeleteExistingAccount() throws SQLServerException {
        // Arrange
        int existingAccountId = 1;
        Account existingAccount = new Account();
        existingAccount.setId(existingAccountId);
        IAccountRepository accountRepository = mock(IAccountRepository.class);
        when(accountRepository.findById(existingAccountId)).thenReturn(Optional.of(existingAccount));
        AccountService accountService = new AccountService();
        accountService.setRepo(accountRepository);

        // Act
        boolean result = accountService.delete(existingAccountId);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testDeleteNonExistingAccount() throws SQLServerException {
        // Arrange
        int nonExistingAccountId = 2;
        IAccountRepository accountRepository = mock(IAccountRepository.class);
        when(accountRepository.findById(nonExistingAccountId)).thenReturn(Optional.empty());
        AccountService accountService = new AccountService();
        accountService.setRepo(accountRepository);

        // Act
        boolean result = accountService.delete(nonExistingAccountId);

        // Assert
        assertFalse(result);
    }

    @Test(expected = RuntimeException.class)
    public void testDeleteThrowsException() throws SQLServerException {
        // Arrange
        int existingAccountId = 1;
        Account existingAccount = new Account();
        existingAccount.setId(existingAccountId);
        IAccountRepository accountRepository = mock(IAccountRepository.class);
        doThrow(new RuntimeException("Unable to delete Account")).when(accountRepository).deleteById(existingAccountId);
        AccountService accountService = new AccountService();
        accountService.setRepo(accountRepository);

        // Act
        accountService.delete(existingAccountId);

        // Assert
        // Expecting RuntimeException
    }

    @Test
    public void testDeleteAllAccounts() throws SQLServerException {
        // Arrange
        int existingAccountId = 1;
        int nonExistingAccountId = 2;
        Set<Integer> accountIds = new HashSet<>();
        accountIds.add(existingAccountId);
        accountIds.add(nonExistingAccountId);

        Account existingAccount = new Account();
        existingAccount.setId(existingAccountId);

        IAccountRepository accountRepository = mock(IAccountRepository.class);
        when(accountRepository.findById(existingAccountId)).thenReturn(Optional.of(existingAccount));
        AccountService accountService = new AccountService();
        accountService.setRepo(accountRepository);

        // Act
        boolean result = accountService.deleteAll(accountIds);

        // Assert
        assertFalse(result);
    }
}
