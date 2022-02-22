package com.spring.springsp.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

@Component("JWT Filter")
public class JWTAuthFilter  extends OncePerRequestFilter {
    @Value("${security.jwt.secret}")
    private String key;

    private final String HEADER ="Authorization";

    private final String PREFIX ="Bearer ";


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try{
            //Revision de envio de token
            if(existJWTToken(httpServletRequest,httpServletResponse)){
                Claims claims = validateToken(httpServletRequest);
                if(claims.get("authorities")!=null){
                    setUpSpringAuthentication(claims);
                }else{
                    SecurityContextHolder.clearContext();
                }
            }else{
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e){
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ((HttpServletResponse)httpServletResponse).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }


    /**
     * Desencripta y extrae los claims del token <br/>
     * Si el token ha expirado, la firma no es valida (Debido a una manipulacion del payload) <br/>
     * el metodo falla y arroja la excepcion correspondiente
     * @param request
     * @return
     */
    private Claims validateToken(HttpServletRequest request)  throws  SignatureException{

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(jwtToken).getBody();
    }

    /**
     * Metodo para autenticarnos dentro del flujo de Spring
     *
     * @param claims
     */
    private void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        SecurityContextHolder.getContext().setAuthentication(auth);

    }

    /**
     * Revisión de envío de token en cabecera
     * @param request  Solicitud
     * @param res      Respuesta
     * @return  boolean
     * <br/>
     */
    private boolean existJWTToken(HttpServletRequest request, HttpServletResponse res) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }

}
