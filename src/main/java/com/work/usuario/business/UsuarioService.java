package com.work.usuario.business;

import com.work.usuario.business.converter.UsuarioConverter;
import com.work.usuario.business.dto.UsuarioDTO;
import com.work.usuario.infraestructure.entity.Usuario;
import com.work.usuario.infraestructure.exceptions.ConflictException;
import com.work.usuario.infraestructure.exceptions.ResourceNotFoundException;
import com.work.usuario.infraestructure.repositore.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
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

    public Usuario buscarUsuarioPorEmail(String email){
        return usuarioRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("Email não encontrado"+email));
    }

    public void deletaUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }




}
