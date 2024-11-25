package pe.edu.upeu.pppmanager.service;

import org.springframework.stereotype.Service;

@Service
public interface PasswordService {
    String encryptPassword(String plainPassword);
    boolean verifyPassword(String plainPassword, String encryptedPassword);
}

