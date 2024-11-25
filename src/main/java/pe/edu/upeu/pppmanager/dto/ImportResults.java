package pe.edu.upeu.pppmanager.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor 
@NoArgsConstructor  
@Getter             
@Setter             
public class ImportResults {
    private int totalRows;
    private int processedRows;
    private int skippedRows;
    private String message;
    private List<String> errorMessages = new ArrayList<>();
    
    public void addErrorMessage(String errorMessage) {
        this.errorMessages.add(errorMessage);
    }
}