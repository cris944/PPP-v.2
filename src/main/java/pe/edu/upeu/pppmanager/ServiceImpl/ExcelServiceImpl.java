package pe.edu.upeu.pppmanager.ServiceImpl;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pe.edu.upeu.pppmanager.dto.ImportResults;
import pe.edu.upeu.pppmanager.service.ExcelService;
@Service
public class ExcelServiceImpl implements ExcelService {


	private static final Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);
	
    private final DataSource dataSource;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ExcelServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    @Transactional
    public ImportResults importData(MultipartFile file) throws IOException, SQLException {
        ImportResults result = new ImportResults();
        int totalRows = 0;
        int processedRows = 0;
        int skippedRows = 0;

        
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream());
             Connection conn = dataSource.getConnection()) {

            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            Map<String, Integer> columnIndices = getColumnIndices(headerRow);
            validarColumnas(columnIndices);
            

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                if (row == null || isRowEmpty(row)) {
                    continue;
                }

                for (Cell cell : row) {
                    logger.info("Fila: {}, Columna: {}, Tipo: {}, Valor: {}", 
                                i, 
                                cell.getColumnIndex(), 
                                cell.getCellType(), 
                                getCellValueAsString(cell));
                }

                if (isRowEmpty(row)) {
                    skippedRows++;
                    logger.warn("Fila {} está vacía y será omitida.", i);
                    continue;
                }
                try {
                String apellido = getCellValueAsString(row.getCell(columnIndices.get("Apellido")));
                String nombre = getCellValueAsString(row.getCell(columnIndices.get("Nombre")));
                String religion = getCellValueAsString(row.getCell(columnIndices.get("Religión")));
                
                String correo_institucional = getCellValueAsString(row.getCell(columnIndices.get("Correo Institucional")));
                if (correo_institucional != null && existeCorreoInstitucional(conn, correo_institucional)) {
                    skippedRows++;
                    result.addErrorMessage("Fila " + i + ": correo institucional ya existe o está vacío.");
                    continue;
                }
                String ciclo = getCellValueAsString(row.getCell(columnIndices.get("Ciclo")));
                String modalidad = getCellValueAsString(row.getCell(columnIndices.get("Modalidad estudio")));
                String contrato = getCellValueAsString(row.getCell(columnIndices.get("Modo contrato")));
                String grupo = getCellValueAsString(row.getCell(columnIndices.get("Grupo")));
                String pais = getCellValueAsString(row.getCell(columnIndices.get("Pais")));


                String img_perfil = getCellValueAsString(row.getCell(columnIndices.get("Foto")));
                if (img_perfil == null || img_perfil.trim().isEmpty()) {
                	img_perfil = "https://i.postimg.cc/zG9cQ5sg/585e4beacb11b227491c3399-2.png"; 
                }
                String dni = getCellValueAsString(row.getCell(columnIndices.get("Documento")));

                if (dni == null || dni.trim().isEmpty() || existeDni(conn, dni)) {
                    skippedRows++;
                    result.addErrorMessage("Fila " + i + ": DNI ya existe o está vacío.");
                    continue;
                }
                
                String username = getCellValueAsString(row.getCell(columnIndices.get("Usuario")));
                if (username == null || username.trim().isEmpty()) {
                	username = dni;
                	
                }
                
                String correo = getCellValueAsString(row.getCell(columnIndices.get("Correo")));
                if (correo != null && existeCorreo(conn, correo)) {
                    skippedRows++;
                    result.addErrorMessage("Fila " + i + ": correo ya existe o está vacío.");
                    continue;
                }

                String telefono = getCellValueAsString(row.getCell(columnIndices.get("Celular")));
                if (telefono == null || telefono.trim().isEmpty()) {
                    telefono = "Sin telefono";
                }

                String codigo = getCellValueAsString(row.getCell(columnIndices.get("Código estudiante")));
                if (codigo != null && existeCodigo(conn, codigo)) {
                    skippedRows++;
                    result.addErrorMessage("Fila " + i + ": código ya existe o está vacío.");
                    continue;
                }
                String sede = getCellValueAsString(row.getCell(columnIndices.get("Sede")));
                if (sede == null || sede.trim().isEmpty()) {
                    throw new IllegalArgumentException("La columna 'Sede' está vacía en la fila " + i);
                }
                int id_Sede = obtenerIdSede(conn, sede);

                String facultad = getCellValueAsString(row.getCell(columnIndices.get("Unidad académica")));
                if (facultad == null || facultad.trim().isEmpty()) {
                    throw new IllegalArgumentException("La columna 'Unidad académica' está vacía en la fila " + i);
                }
                int id_Facultad = obtenerIdFacultad(conn, facultad, id_Sede);

                String carrera = getCellValueAsString(row.getCell(columnIndices.get("Programa estudio")));
                if (carrera == null || carrera.trim().isEmpty()) {
                    throw new IllegalArgumentException("La columna 'Programa estudio' está vacía en la fila " + i);
                }
                int id_Carrera = obtenerIdCarrera(conn, carrera, id_Facultad);

                int id_Carrera_Plan = obtenerIdCarreraPlanActivo(conn, id_Carrera);
                
                String passwordEncrypted = passwordEncoder.encode(codigo);

                int idPersona = 
                insertarPersona(conn, apellido, nombre, dni, correo,religion,telefono,pais, "A");
                insertarUsuario(conn, idPersona,username,passwordEncrypted,img_perfil, "A");
                
                if (idPersona <= 0) {
                    throw new IllegalArgumentException("ID_PERSONA no válido generado para la fila " + i);
                }

                int idEstudiante =
                insertarEstudiante(conn, idPersona,ciclo,grupo, correo_institucional,codigo, "A");
                insertarMatricula(conn, idEstudiante, id_Carrera_Plan, modalidad, contrato,"A");
                
                if (idEstudiante <= 0) {
                    throw new IllegalArgumentException("ID_ESTUDIANTE no válido generado para la fila " + i);
                }

                
                processedRows++;
            }catch (Exception e) {
                skippedRows++;
                logger.error("Error procesando la fila {}: {}", i, e.getMessage(), e);
                result.addErrorMessage("Fila " + i + ": " + e.getMessage());
            }
        }
    }
        
        result.setTotalRows(totalRows);
        result.setProcessedRows(processedRows);
        result.setSkippedRows(skippedRows);
        result.setMessage("Importación completada exitosamente.");
        return result;
}

    private Map<String, Integer> getColumnIndices(Row headerRow) {
        Map<String, Integer> columnIndices = new HashMap<>();
        for (Cell cell : headerRow) {
            columnIndices.put(cell.getStringCellValue(), cell.getColumnIndex());
        }
        return columnIndices;
    }

    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellType() != CellType.BLANK && !getCellValueAsString(cell).trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean existeDni(Connection conn, String dni) throws SQLException {
        String consulta = "SELECT 1 FROM PERSONA WHERE DNI = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(consulta)) {
            pstmt.setString(1, dni);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    private boolean existeCorreo(Connection conn, String correo) throws SQLException {
        String consulta = "SELECT 1 FROM PERSONA WHERE CORREO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(consulta)) {
            pstmt.setString(1, correo);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private boolean existeCorreoInstitucional (Connection conn, String correo_institucional) throws SQLException {
        String consulta = "SELECT 1 FROM ESTUDIANTE WHERE CORREO_INSTITUCIONAL = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(consulta)) {
            pstmt.setString(1, correo_institucional);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    
    private boolean existeCodigo(Connection conn, String codigo) throws SQLException {
        String consulta = "SELECT 1 FROM ESTUDIANTE WHERE CODIGO = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(consulta)) {
            pstmt.setString(1, codigo);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue()); 
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return cell.getStringCellValue(); 
                } catch (IllegalStateException e) {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BLANK:
                return "";
            default:
                return "";
        }
    }

    
    private void validarColumnas(Map<String, Integer> columnIndices) throws IllegalArgumentException {
        List<String> requiredColumns = Arrays.asList("Apellido", "Nombre", "Documento", "Correo", "Celular", "Código estudiante", "Religión", "Modalidad estudio",
        		"Ciclo", "Grupo", "Sede", "Correo Institucional", "Modo contrato","Programa estudio","Usuario","Pais","Unidad académica");
        for (String column : requiredColumns) {
            if (!columnIndices.containsKey(column)) {
                throw new IllegalArgumentException("Falta la columna requerida: " + column);
            }
        }
    }


    
    private int insertarPersona(Connection conn, String apellido, String nombre, String dni, String correo, String religion, String telefono,String pais, String estado) throws SQLException {
        String insertPersonaSQL = "INSERT INTO PERSONA (APELLIDO, NOMBRE, DNI, CORREO, RELIGION, TELEFONO, PAIS, ESTADO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertPersonaSQL, new String[]{"ID_PERSONA"})) {
            pstmt.setString(1, apellido);
            pstmt.setString(2, nombre);
            pstmt.setString(3, dni);
            pstmt.setString(4, correo);
            pstmt.setString(5, religion);
            pstmt.setString(6, telefono);
            pstmt.setString(7, pais);
            pstmt.setString(8, estado);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Error al insertar en PERSONA.");
    }
    
    private void insertarUsuario(Connection conn, int idPersona, String nombre_usuario, String passwordEncrypted, String img_perfil, String estado) throws SQLException {
        String insertUsuarioSQL = "INSERT INTO USUARIO (ID_PERSONA, NOMBRE_USUARIO, CONTRASENIA, IMG_PERFIL, ESTADO) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertUsuarioSQL)) {
            pstmt.setInt(1, idPersona);
            pstmt.setString(2, nombre_usuario);
            pstmt.setString(3, passwordEncrypted);
            pstmt.setString(4, img_perfil);
            pstmt.setString(5, estado);
            pstmt.executeUpdate();
        }
    }
    private int insertarEstudiante(Connection conn, int idPersona, String ciclo, String grupo, String correo_institucional, String codigo, String estado) throws SQLException {
        String insertEstudianteSQL = "INSERT INTO ESTUDIANTE (ID_PERSONA, CODIGO, CICLO, GRUPO, CORREO_INSTITUCIONAL, ESTADO) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertEstudianteSQL, new String[]{"ID_ESTUDIANTE"})) {
            pstmt.setInt(1, idPersona);
            pstmt.setString(2, codigo);
            pstmt.setString(3, ciclo);
            pstmt.setString(4, grupo);
            pstmt.setString(5, correo_institucional);
            pstmt.setString(6, estado);
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); 
                }
            }
        }
        throw new SQLException("Error al insertar en ESTUDIANTE.");
        }
    
    
    
//fumada :v
    private int obtenerIdSede(Connection conn, String nombreSede) throws SQLException {
        String query = "SELECT ID_SEDE FROM SEDE WHERE NOMBRE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombreSede);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_SEDE");
                }
            }
        }
        throw new SQLException("Sede no encontrada: " + nombreSede);
}
    private int obtenerIdFacultad(Connection conn, String nombreFacultad, int idSede) throws SQLException {
        String query = "SELECT ID_FACULTAD FROM FACULTAD WHERE NOMBRE = ? AND ID_SEDE = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombreFacultad);
            pstmt.setInt(2, idSede);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_FACULTAD");
                }
            }
        }
        throw new SQLException("Facultad no encontrada: " + nombreFacultad + " en la sede: " + idSede);
    }
    
    private int obtenerIdCarrera(Connection conn, String nombreCarrera, int idFacultad) throws SQLException {
        String query = "SELECT ID_CARRERA FROM CARRERA WHERE NOMBRE = ? AND ID_FACULTAD = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombreCarrera);
            pstmt.setInt(2, idFacultad);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_CARRERA");
                }
            }
        }
        throw new SQLException("Carrera no encontrada: " + nombreCarrera + " en la facultad: " + idFacultad);
    }

    private int obtenerIdCarreraPlanActivo(Connection conn, int idCarrera) throws SQLException {
        String query = "SELECT ID_CARRERA_PLAN FROM CARRERA_PLAN WHERE ID_CARRERA = ? AND ESTADO = 'A'";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idCarrera);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ID_CARRERA_PLAN");
                }
            }
        }
        throw new SQLException("No se encontró un plan activo para la carrera: " + idCarrera);
    }
    
    private void insertarMatricula(Connection conn, int idEstudiante, int idCarreraPlan, String modalidad, String contrato,String estado ) throws SQLException {
        String insertMatriculaSQL = "INSERT INTO MATRICULA (ID_ESTUDIANTE, ID_CARRERA_PLAN, MODALIDAD, CONTRATO, ESTADO) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertMatriculaSQL)) {
            pstmt.setInt(1, idEstudiante);
            pstmt.setInt(2, idCarreraPlan);
            pstmt.setString(3, modalidad);
            pstmt.setString(4, contrato);
            pstmt.setString(5, estado);
            pstmt.executeUpdate();
        }
    }

}
