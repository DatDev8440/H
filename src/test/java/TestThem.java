import com.sax.dtos.KhachHangDTO;
import com.sax.entities.KhachHang;
import com.sax.services.IKhachHangService;
import com.sax.utils.ContextUtils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

public class TestThem {
    @Test
    public  void ThemKh1(){
        IKhachHangService TestKh = ContextUtils.getBean(IKhachHangService.class);
        KhachHangDTO kh = new KhachHangDTO("123","123", 0,LocalDateTime.now() ,true);
        try{
            TestKh.insert(kh);
        }catch (com.microsoft.sqlserver.jdbc.SQLServerException throwables){
            throw new RuntimeException(throwables);
        }
    }
    @Test
    public  void XoaKh1(){
        IKhachHangService TestKh = ContextUtils.getBean(IKhachHangService.class);
        KhachHangDTO kh = new KhachHangDTO();
        kh.setId(4011);
        try{
         TestKh.delete(kh.getId());
        }catch (com.microsoft.sqlserver.jdbc.SQLServerException throwables){
            throw new RuntimeException(throwables);
        }
    }
    @Test
    public  void SuaKh1(){
        IKhachHangService TestKh = ContextUtils.getBean(IKhachHangService.class);
        KhachHangDTO kh = new KhachHangDTO("123","123456", 0,LocalDateTime.now() ,true);
        kh.setId(4014);
        try{
            TestKh.update(kh);
        }catch (com.microsoft.sqlserver.jdbc.SQLServerException throwables){
            throw new RuntimeException(throwables);
        }
    }
}
