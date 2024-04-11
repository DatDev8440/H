package KhachHang;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.KhachHangDTO;
import com.sax.services.IKhachHangService;
import com.sax.utils.ContextUtils;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class XoaKH {
    KhachHangDTO kh = new KhachHangDTO("123", "0355831589", 0, LocalDateTime.now(), true);
    IKhachHangService TestKh = ContextUtils.getBean(IKhachHangService.class);
    @Test
public  void XoaKh1() throws SQLServerException {
    IKhachHangService TestKh = ContextUtils.getBean(IKhachHangService.class);
    KhachHangDTO kh = new KhachHangDTO();
    kh.setId(4014);
        TestKh.delete(kh.getId());

}
    @Test
    public void testDeleteExistingKhachHang() throws SQLServerException {
        assertFalse(TestKh.delete(4025));
    }

    @Test
    public void testDeleteNonExistingKhachHang() throws SQLServerException {
        assertFalse(TestKh.delete(4026));
    }


    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteEmptyId() throws SQLServerException {
        TestKh.delete(0);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testDeleteInvalidId() throws SQLServerException {
        TestKh.delete(-2);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void testDeleteWithNullKhachHang() throws SQLServerException {

        TestKh.delete(null);
    }
}
