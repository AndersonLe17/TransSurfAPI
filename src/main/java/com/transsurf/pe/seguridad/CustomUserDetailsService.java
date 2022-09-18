package com.transsurf.pe.seguridad;

import com.transsurf.pe.entidades.Rol;
import com.transsurf.pe.entidades.Usuario;
import com.transsurf.pe.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String numDocOrEmail) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByNumDocOrEmail(numDocOrEmail, numDocOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el numDoc/Email: " + numDocOrEmail));

        return new User(usuario.getNumDoc(), usuario.getPassword(), mapearCargos(usuario.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapearCargos(Set<Rol> rols) {
        return rols.stream().map(rol -> new SimpleGrantedAuthority(rol.getTipo())).collect(Collectors.toList());
    }

}
