package com.avalos.tienda.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try{
            //extraer jwt del header
            String jwt = getJwtFromRequest(request);
            System.out.println("jwt: " + jwt);
            //validar que existe y es valido
            if(jwt != null && jwtTokenProvider.validateToken(jwt)){
                //extraer email del token
                String email = jwtTokenProvider.getEmailFromToken(jwt);
                String rol = jwtTokenProvider.getRolFromToken(jwt);
                //lista de authorities con prefijo correcto
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + rol));
                //crear objeto de autenticacion
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);
                //establecer la autenticacion Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exception){
            // Si hay cualquier error, continuamos sin autenticación
            // (El siguiente filtro o controller decidirá qué hacer)
        }
        //pasar al siguiente filtro en la cadena
        filterChain.doFilter(request, response);
    }
    private String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
