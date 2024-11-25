package pe.edu.upeu.pppmanager.ServiceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import pe.edu.upeu.pppmanager.service.PasswordService;

public class PasswordServiceImpl implements PasswordService {
	
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordServiceImpl() {
        this.passwordEncoder  = new BCryptPasswordEncoder();
    }
	
	@Override
	public String encryptPassword(String plainPassword) {
		return passwordEncoder.encode(plainPassword);
	}

	@Override
	public boolean verifyPassword(String plainPassword, String encryptedPassword) {
        return passwordEncoder.matches(plainPassword, encryptedPassword);
	}

}
