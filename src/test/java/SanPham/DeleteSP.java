package SanPham;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.KhachHangDTO;
import com.sax.entities.Sach;
import com.sax.repositories.ISachRepository;
import com.sax.services.IKhachHangService;
import com.sax.services.ISachService;
import com.sax.services.impl.SachService;
import com.sax.utils.ContextUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


public class DeleteSP {
    ISachService TestSP = ContextUtils.getBean(ISachService.class);
    @Test
    public  void XoaKh1() throws SQLServerException {
        Sach sach = new Sach();
        sach.setId(21126);
        TestSP.delete(sach.getId());

    }
    @Test
    public void testDeleteExistingKhachHang() throws SQLServerException {
        assertFalse(TestSP.delete(20126));
    }



    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteEmptyId() throws SQLServerException {
        TestSP.delete(0);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteInvalidId() throws SQLServerException {
        TestSP.delete(-20127);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void testDeleteWithNullKhachHang() throws SQLServerException {
        TestSP.delete(null);
    }
}
