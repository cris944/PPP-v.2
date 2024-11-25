package pe.edu.upeu.pppmanager.service;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import pe.edu.upeu.pppmanager.dto.ImportResults;
@Service
public interface ExcelService {
    ImportResults importData(MultipartFile file) throws IOException, SQLException;
}
