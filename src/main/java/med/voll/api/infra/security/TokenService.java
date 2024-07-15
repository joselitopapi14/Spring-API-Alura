package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.secret}")
    private String APISecret;
    
    public String generateToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(APISecret);
            return JWT.create().withIssuer("DB_Voll").withSubject(usuario.getUser()).withClaim("id", usuario.getId())
                      .withExpiresAt(generateExpirationTime()).sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("JWT generation failed", exception);
        }
    }
    
    public String getSubject(String token) {
        if (token == null){
            throw new RuntimeException("Token is null");
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(APISecret);
            verifier = JWT.require(algorithm)
                                     .withIssuer("DB_Voll")
                                     .build()
                                     .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
        
        }
        if (verifier.getSubject() == null) {
            throw new RuntimeException("JWT verification failed");
        }
        return verifier.getSubject();
    }
    
    private Instant generateExpirationTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }
}
