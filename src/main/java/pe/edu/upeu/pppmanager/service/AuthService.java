package pe.edu.upeu.pppmanager.service;

import pe.edu.upeu.pppmanager.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
