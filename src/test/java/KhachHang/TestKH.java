package KhachHang;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.KhachHangDTO;
import com.sax.services.IKhachHangService;
import com.sax.services.impl.KhachHangService;
import com.sax.utils.ContextUtils;
import org.apache.commons.math3.analysis.function.Max;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestKH {
    KhachHangDTO kh = new KhachHangDTO("123", "0355831589", 0, LocalDateTime.now(), true);
    IKhachHangService TestKh = ContextUtils.getBean(IKhachHangService.class);
    @Test
    public void ThemKh1() throws SQLServerException {
        TestKh.insert(kh);
    }

    @Test(expected = NullPointerException.class)
    public void testInsert_NullKhachHangDTO() throws SQLServerException {
        KhachHangDTO khDTO = null;
        TestKh.insert(khDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsert_ValidKhachHangDTO() throws SQLServerException {
        KhachHangDTO khDTO = new KhachHangDTO();
        TestKh.insert(khDTO);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testInsert_NullTenTO() throws SQLServerException {
        // Thiết lập các trường thông tin bắt buộc là null
        kh.setTenKhach(null);
        // Thiết lập các trường thông tin khác cần thiết là null hoặc không hợp lệ
        TestKh.insert(kh);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsert_NgayThem() throws SQLServerException {
        // Arrange
        KhachHangDTO khachHangDTO = new KhachHangDTO();
        // Act
        KhachHangDTO insertedKhachHang = TestKh.insert(khachHangDTO);
        // Assert
        assertNotNull(insertedKhachHang.getNgayThem());
        assertEquals(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), insertedKhachHang.getNgayThem().truncatedTo(ChronoUnit.MINUTES));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSDT_chu() throws SQLServerException {
        kh.setSdt("123fafffff");
        TestKh.insert(kh);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDiemMax() throws SQLServerException {
        kh.setDiem(Integer.MAX_VALUE);
        TestKh.insert(kh);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDiemMin() throws SQLServerException {
        kh.setDiem(Integer.MIN_VALUE);
        TestKh.insert(kh);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSdtLon() throws SQLServerException {
        kh.setSdt("097182781274812");
        TestKh.insert(kh);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSdtNho() throws SQLServerException {
        kh.setSdt("1091209");
        TestKh.insert(kh);
    }
    @Test(expected = NullPointerException.class)
    public void testDiemNull() throws SQLServerException {
        kh.setDiem(null);
        TestKh.insert(kh);
    }
}
