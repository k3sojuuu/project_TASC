package com.example.order_service.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.List;

@Component
public class JwtProvider {
    public static final Logger log= LoggerFactory.getLogger(JwtProvider.class);
    private final String JWT_SECRET="secretaaaaaaaaaaaaahhhhhhhhh" +
            "hhhhhhhhhhhhhhhhhhhhhaaaaaaaaaaaaaaatttttttttttttttttttttttttttttttttt";
    private final long JWT_EXPIRATION=86400000L;


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    }
    //get user from jwt
    public String getUserFromJwt(String token){
        Claims claims= Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid format jwt->Message:{}",ex);
        } catch (SignatureException ex){
            log.error("Invalid jwt signature->Message:{}",ex);
        } catch (ExpiredJwtException ex) {
            log.error("Expired jwt token->Message:{}",ex);
        } catch (UnsupportedJwtException e){
            log.error("Unsupported jwt token->Message:{}",e);
        } catch (IllegalArgumentException e){
            log.error("jwt class is empty->Message:{}",e);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        return false;
    }
    public List<String> getRoleFromToken(String token){
        Claims claims =Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody();
        return claims.get("roles",List.class);
    }

}

