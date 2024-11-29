package tiendaRopaHombre.Alpha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tiendaRopaHombre.Alpha.models.Cliente;
import tiendaRopaHombre.Alpha.repository.ClienteRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Buscando usuario con email: " + email);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("admin123");
        System.out.println(encodedPassword);

        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Cliente no encontrado con el email: " + email));


        String rol;
        if (email.equals("admin@gmail.com")) {
            rol = "ADMIN";
        } else {
            rol = "USER";
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(cliente.getEmail())
                .password(cliente.getPassword())
                .roles(rol) // Asigna el rol adecuado
                .build();
    }
}
