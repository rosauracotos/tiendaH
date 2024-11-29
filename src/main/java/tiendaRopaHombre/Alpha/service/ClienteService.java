package tiendaRopaHombre.Alpha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tiendaRopaHombre.Alpha.models.Cliente;
import tiendaRopaHombre.Alpha.repository.ClienteRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ArrayList<Cliente> obtenerClientes(){
        return (ArrayList<Cliente>) clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerPorId(Integer id){
        return clienteRepository.findById(id) ;
    }

    public  Cliente guardarCliente(Cliente cliente){
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));
        return clienteRepository.save(cliente);    }

    public Cliente editarCliente(Cliente cliente, Integer id){
        Optional<Cliente> clienteBDOpt = clienteRepository.findById(id);
        Cliente clienteBD = clienteBDOpt.get();

        clienteBD.setNombre(cliente.getNombre());
        clienteBD.setEmail(cliente.getEmail());
        clienteBD.setPassword(cliente.getPassword());
        clienteBD.setDireccion(cliente.getDireccion());
        clienteBD.setTelefono(cliente.getTelefono());

        return clienteRepository.save(clienteBD);
    }

    public boolean eleminarCliente(Integer id){

        try{
            clienteRepository.deleteById(id);
            return true;
        } catch (Exception err){
            return false;
        }

    }


    public boolean validarLogin(String email, String password) {
        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(email);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            // Compara la contraseña ingresada con la almacenada en la base de datos
            return cliente.getPassword().equals(password);
        }

        return false; // Retorna falso si el cliente no existe o la contraseña es incorrecta
    }

    public Cliente obtenerClientePorEmail(String email) {
        return clienteRepository.findByEmail(email).orElse(null);
    }


}
