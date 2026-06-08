package com.minimarket.config;

import com.minimarket.entity.Rol;
import com.minimarket.entity.Usuario;
import com.minimarket.repository.RolRepository;
import com.minimarket.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(RolRepository rolRepository,
                               UsuarioRepository usuarioRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {

            // 1. CARGA Y CONFIGURACIÓN ROLES

            Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN")
                    .orElseGet(() -> {
                        Rol r = new Rol();
                        r.setNombre("ROLE_ADMIN");
                        return rolRepository.save(r);
                    });

            Rol rolEmpleado = rolRepository.findByNombre("ROLE_EMPLEADO")
                    .orElseGet(() -> {
                        Rol r = new Rol();
                        r.setNombre("ROLE_EMPLEADO");
                        return rolRepository.save(r);
                    });

            Rol rolCliente = rolRepository.findByNombre("ROLE_CLIENTE")
                    .orElseGet(() -> {
                        Rol r = new Rol();
                        r.setNombre("ROLE_CLIENTE");
                        return rolRepository.save(r);
                    });


            // 2. Usuarios

            // Administrador
            if (usuarioRepository.findByUsername("admin").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRoles(Set.of(rolAdmin));
                usuarioRepository.save(admin);
                System.err.println("Usuario 'admin' creado");
            }

            // Empleado
            if (usuarioRepository.findByUsername("empleado").isEmpty()) {
                Usuario empleado = new Usuario();
                empleado.setUsername("empleado");
                empleado.setPassword(passwordEncoder.encode("empleado"));
                empleado.setRoles(Set.of(rolEmpleado));
                usuarioRepository.save(empleado);
                System.err.println("Usuario 'empleado' creado");
            }

            // Cliente
            if (usuarioRepository.findByUsername("cliente").isEmpty()) {
                Usuario cliente = new Usuario();
                cliente.setUsername("cliente");
                cliente.setPassword(passwordEncoder.encode("cliente"));
                cliente.setRoles(Set.of(rolCliente));
                usuarioRepository.save(cliente);
                System.err.println("Usuario 'cliente' creado");
            }
        };
    }
}