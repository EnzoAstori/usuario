package com.work.usuario.controller;

import com.work.usuario.business.UsuarioService;
import com.work.usuario.business.dto.UsuarioDTO;
import com.work.usuario.infraestructure.entity.Usuario;
import com.work.usuario.infraestructure.exceptions.ConflictException;
import com.work.usuario.infraestructure.repositore.UsuarioRepository;
import com.work.usuario.infraestructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor

public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO){
        return  ResponseEntity.ok(usuarioService.salvaUsuario(usuarioDTO));

    }

    public void emailExiste(String email){
        try {
            boolean existe = verificarEmailExistente(email);
            if (existe){

                throw new ConflictException("Email já cadastrado: "+email);

            }
        }catch (ConflictException e){
            throw new ConflictException("Email já cadastrado: "+ e.getCause());
        }
    }

    public boolean verificarEmailExistente(String email){
        return usuarioRepository.existsByEmail(email);
    }
    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(),usuarioDTO.getSenha()));

        return "Bearer " + jwtUtil.generateToken(authentication.getName());

    }

    @GetMapping

    public ResponseEntity<Usuario> buscaUsuarioPorEmail (@RequestParam("email")String email){
        return  ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email));
    }

    @DeleteMapping("/{email}")

    private ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email){
        usuarioService.deletaUsuarioPorEmail(email);
        return  ResponseEntity.ok().build();
    }


}
