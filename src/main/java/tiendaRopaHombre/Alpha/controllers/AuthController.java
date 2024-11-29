package tiendaRopaHombre.Alpha.controllers;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tiendaRopaHombre.Alpha.models.Cliente;
import tiendaRopaHombre.Alpha.service.ClienteService;
import tiendaRopaHombre.Alpha.service.JwtService;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private ClienteService clienteService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> validarLogin(@RequestBody Map<String, String> loginData, HttpSession session) {
        String email = loginData.get("email");
        String password = loginData.get("password");

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("El email y la contraseña son obligatorios");
        }

        boolean esValido = clienteService.validarLogin(email, password);

        if (esValido) {
            // Obtén el cliente desde el servicio
            Cliente cliente = clienteService.obtenerClientePorEmail(email);

            // Genera un token JWT con el nombre del cliente y otros datos relevantes
            String token = jwtService.generateToken(cliente.getEmail());

            // Guarda datos importantes en la sesión
            session.setAttribute("nombreUsuario", cliente.getNombre());
            session.setAttribute("token", token);

            // Devuelve el token y la URL de redirección
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("url", "/");
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Correo o contraseña incorrectos");
        }
    }

}
