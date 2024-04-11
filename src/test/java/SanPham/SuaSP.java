package SanPham;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.DanhMucDTO;
import com.sax.dtos.SachDTO;
import com.sax.entities.Sach;
import com.sax.repositories.ISachRepository;
import com.sax.services.impl.SachService;
import com.sax.utils.ImageUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class SuaSP {
    @InjectMocks
    SachService sachService;

    @Mock
    ISachRepository sachRepository;



    SachDTO sachDTO = new SachDTO(123,"123","Sach1",13L,12,true, "i.img","nxb1",new HashSet<>());



    @Test(expected = NullPointerException.class)
    public void testUpdateSach_NonExistingSach() throws SQLServerException {
        sachDTO.setId(999); // Thiết lập ID của sách không tồn tại
        sachService.update(sachDTO);
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateSach_DuplicateBarcode() throws SQLServerException {
        SachDTO existingSachDTO = new SachDTO(20128,"197979","Vui vẻ",123213L,11,false, "no-image.png","Kim Đồng",new HashSet<>());
        SachDTO newSachDTO = new SachDTO(123,"197979","Sach1",13L,12,true, "i.img","nxb1",new HashSet<>());
        newSachDTO.setBarCode(existingSachDTO.getBarCode());
        when(sachRepository.findByBarCode(newSachDTO.getBarCode())).thenReturn(Optional.of(new Sach()));
        sachService.update(newSachDTO);
    }


    @Test(expected = RuntimeException.class)
    public void testUpdateSach_ImageSaveFailure() throws SQLServerException,IOException {
        SachDTO sachDTO = new SachDTO(123,"123","Sach1",13L,12,true, "i.img","nxb1",new HashSet<>());
        sachDTO.setId(20128); // Thiết lập ID của sách cần cập nhật
        sachDTO.setHinhAnh("newImagePath"); // Thiết lập đường dẫn hình ảnh mới
        when(sachRepository.save(any(Sach.class))).thenReturn(new Sach());
        when(ImageUtils.saveImage(any(File.class))).thenThrow(new IOException());
        sachService.update(sachDTO);
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateSach_NonExistingDanhMuc() throws SQLServerException {
        SachDTO sachDTO = new SachDTO(123,"123","Sach1",13L,12,true, "i.img","nxb1",new HashSet<>());
        sachDTO.setId(20128); // Thiết lập ID của sách cần cập nhật
        sachDTO.setSetDanhMuc((Set<DanhMucDTO>) Collections.singletonList(new DanhMucDTO()));
        when(sachRepository.save(any(Sach.class))).thenReturn(new Sach());
        sachService.update(sachDTO);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateSach_NegativePrice() throws SQLServerException {
        SachDTO sachDTO = new SachDTO(123,"123","Sach1",13L,12,true, "i.img","nxb1",new HashSet<>());
        sachDTO.setId(1); // Thiết lập ID của sách cần cập nhật
        sachDTO.setGiaBan(-100L); // Thiết lập giá sách âm
        sachService.update(sachDTO);
    }

    @Test(expected = NullPointerException.class)
    public void testUpdateSach_NegativeQuantity() throws SQLServerException {
        SachDTO sachDTO = new SachDTO(/* Thông tin sách cần cập nhật */);
        sachDTO.setId(1); // Thiết lập ID của sách cần cập nhật
        sachDTO.setSoLuong(-10); // Thiết lập số lượng sách âm
        sachService.update(sachDTO);
    }

    @Test(expected = RuntimeException.class)
    public void testUpdateSach_BarcodeChangeWithDuplicate() throws SQLServerException {
        SachDTO sachDTO = new SachDTO(/* Thông tin sách cần cập nhật */);
        sachDTO.setId(1); // Thiết lập ID của sách cần cập nhật
        sachDTO.setBarCode("newBarcode"); // Thiết lập barcode mới
        when(sachRepository.findByBarCode(sachDTO.getBarCode())).thenReturn(Optional.of(new Sach()));
        sachService.update(sachDTO);
    }

}
