package SanPham;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.sax.dtos.SachDTO;
import com.sax.entities.Sach;
import com.sax.repositories.ISachRepository;
import com.sax.services.impl.SachService;
import com.sax.utils.ImageUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class ThemSP {
    @InjectMocks
    SachService sachService;
    SachDTO sachDTO = new SachDTO(123,"123","Sach1",13L,12,true, "i.img","nxb1",new HashSet<>());

    @Mock
    ISachRepository sachRepository;

@Test
    public void testInsertSach_Success() throws SQLServerException {
        SachDTO sachDTO = new SachDTO(123,"123","Sach1",13L,12,true, "i.img","nxb1",new HashSet<>());
        when(sachRepository.save(any(Sach.class))).thenReturn(new Sach());
        assertNotNull(sachService.insert(sachDTO));
    }

    @Test(expected = NullPointerException.class)
    public void testInsertSach_NullSachDTO() throws SQLServerException {
        SachDTO sachDTOnl = null;
        sachService.insert(sachDTOnl);
    }

    @Test(expected = RuntimeException.class)
    public void testInsertSach_DuplicateBarcode() throws SQLServerException {
        when(sachRepository.findByBarCode(Mockito.anyString())).thenReturn(Optional.of(new Sach()));
        sachService.insert(sachDTO);
    }

    @Test(expected = RuntimeException.class)
    public void testInsertSach_NullBarcode() throws SQLServerException {
        SachDTO sachDTO = new SachDTO(123,null,"Sach1",13L,12,true, "i.img","nxb1",new HashSet<>());
        sachService.insert(sachDTO);
    }

    @Test(expected = RuntimeException.class)
    public void testInsertSach_NullName() throws SQLServerException {
        SachDTO sachDTO = new SachDTO(123,"123","",13L,12,true, "i.img","nxb1",new HashSet<>());
        sachService.insert(sachDTO);
    }

    @Test(expected = RuntimeException.class)
    public void testInsertSach_NegativePrice() throws SQLServerException {
        SachDTO sachDTO = new SachDTO(123,"123","Sach1",-13L,12,true, "i.img","nxb1",new HashSet<>());
        sachService.insert(sachDTO);
    }

    @Test(expected = RuntimeException.class)
    public void testInsertSach_NegativeQuantity() throws SQLServerException {
        SachDTO sachDTO = new SachDTO(123,"123","Sach1",13L,-12,true, "i.img","nxb1",new HashSet<>());
        sachService.insert(sachDTO);
    }

    @Test(expected = RuntimeException.class)
    public void testInsertSach_InvalidImage() throws SQLServerException {
        SachDTO sachDTO = new SachDTO(123,"123","Sach1",13L,12,true, "i.img","nxb1",new HashSet<>());
        sachService.insert(sachDTO);
    }

    @Test(expected = RuntimeException.class)
    public void testInsertSach_FailedToSaveImage() throws SQLServerException, IOException {
        SachDTO sachDTO = new SachDTO(123,"123","Sach1",13L,12,true, "i.img","nxb1",new HashSet<>());
        when(sachRepository.save(any(Sach.class))).thenReturn(new Sach());
        when(ImageUtils.saveImage(any(File.class))).thenThrow(new IOException()); // Sửa đổi ở đây
        sachService.insert(sachDTO);
    }

    @Test(expected = RuntimeException.class)
    public void testInsertSach_NullCategory() throws SQLServerException, SQLServerException {
        SachDTO sachDTO = new SachDTO(123,"123","Sach1",13L,12,true, "i.img","nxb1",null);
        sachService.insert(sachDTO);
    }
}
