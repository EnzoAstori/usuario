package com.work.usuario.business;

import com.work.usuario.business.converter.UsuarioConverter;
import com.work.usuario.business.dto.UsuarioDTO;
import com.work.usuario.infraestructure.entity.Usuario;
import com.work.usuario.infraestructure.repositore.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepositor;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvaUsuario(UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepositor.save(usuario));
    }



}
