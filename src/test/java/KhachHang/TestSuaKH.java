package KhachHang;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.KhachHangDTO;
import com.sax.services.IKhachHangService;
import com.sax.utils.ContextUtils;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestSuaKH {
    KhachHangDTO kh = new KhachHangDTO("123", "0355831589", 0, LocalDateTime.now(), true);
    IKhachHangService TestKh = ContextUtils.getBean(IKhachHangService.class);


    // 10 test case update
    @Test
    public void testUpdateTenKhach() throws SQLServerException {
        kh.setTenKhach("New Name");
        KhachHangDTO updatedKhachHang = TestKh.update(kh);
        assertEquals("New Name", updatedKhachHang.getTenKhach());
    }

    @Test
    public void testUpdateSdt() throws SQLServerException {
        kh.setSdt("0987654321");
        KhachHangDTO updatedKhachHang = TestKh.update(kh);
        assertEquals("0987654321", updatedKhachHang.getSdt());
    }

    @Test
    public void testUpdateDiem() throws SQLServerException {
        kh.setDiem(50);
        KhachHangDTO updatedKhachHang = TestKh.update(kh);
        assertEquals(50, updatedKhachHang.getDiem().intValue());
    }

    @Test
    public void testUpdateNgayThem() throws SQLServerException {
        LocalDateTime newDateTime = LocalDateTime.now().minusDays(1);
        kh.setNgayThem(newDateTime);
        KhachHangDTO updatedKhachHang = TestKh.update(kh);
        assertEquals(newDateTime.truncatedTo(ChronoUnit.MINUTES), updatedKhachHang.getNgayThem().truncatedTo(ChronoUnit.MINUTES));
    }

    @Test
    public void testUpdateGioiTinh() throws SQLServerException {
        kh.setGioiTinh(false);
        KhachHangDTO updatedKhachHang = TestKh.update(kh);
        assertEquals(false, updatedKhachHang.isGioiTinh());
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateNullKhachHang() throws SQLServerException {
        KhachHangDTO nullKhachHang = null;
        TestKh.update(nullKhachHang);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUpdateNullTenKhach() throws SQLServerException {
        kh.setTenKhach(null);
        TestKh.update(kh);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateSdtInvalid() throws SQLServerException {
        kh.setSdt("invalidSdt");
        TestKh.update(kh);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateDiemInvalid() throws SQLServerException {
        kh.setDiem(-10);
        TestKh.update(kh);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUpdateNullNgayThem() throws SQLServerException {
        kh.setNgayThem(null);
        TestKh.update(kh);
    }

}
