package com.example.user_service.security;

import com.example.user_service.user_principle.UserPrinciple;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    public static final Logger log= LoggerFactory.getLogger(JwtProvider.class);

    private final  String JWT_SECRET="secretaaaaaaaaaaaaahhhhhhhhh" +
            "hhhhhhhhhhhhhhhhhhhhhaaaaaaaaaaaaaaatttttttttttttttttttttttttttttttttt";
//    Biến JWT_SECRET lưu trữ khóa bí mật được sử dụng để ký JWT

    private final long JWT_EXPIRATION=86400000L;
//    Thời gian chết của token

    public String generateToken(Authentication authentication){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+JWT_EXPIRATION);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
//        Lấy thông tin người dùng từ đối tượng Authentication.

        List<String> roles = userPrinciple.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
//        Lấy danh sách các vai trò của người dùng.

        return Jwts.builder()
                .claim("id",userPrinciple.getId().toString())
                .setSubject(userPrinciple.getUsername())
                .claim("roles",roles)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith( key(), SignatureAlgorithm.HS512)
                .compact();
    }
    private Key key(){return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));}

    public String getUserFromJwt(String token){
        Claims claims=Jwts.parserBuilder()
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
        }  catch (ExpiredJwtException ex) {
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


//    public static final Logger log = LoggerFactory.getLogger(JwtEntryPoint.class);
//
//    private final String JWT_SECRET="cristianoronaldosiuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu" +
//            "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuggggggggggggggggggggggggggggggggggggggggggggggguuuu";
//    private final long JWT_EXPIRATION=86400000L;
//
//    public String generateToken(Authentication authentication){
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime()+JWT_EXPIRATION);
//        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
//
//        List<String> roles = userPrinciple.getAuthorities().stream()
//                .map(authority -> authority.getAuthority()).collect(Collectors.toList());
//        return Jwts.builder()
//                .claim("id",userPrinciple.getId().toString())
//                .setSubject(userPrinciple.getUsername())
//                .claim("roles",roles)
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith( key(), SignatureAlgorithm.ES512)
//                .compact();
//    }
//
//
//    private Key key(){return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));}
//
//    public String getUserFromJwt(String token){
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(key())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        return claims.getSubject();
//    }
//
//    public boolean validateToken(String authToken) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(authToken);
//            return true;
//        } catch (MalformedJwtException ex) {
//            log.error("Invalid format jwt->Message:{}",ex);
//        }  catch (ExpiredJwtException ex) {
//            log.error("Expired jwt token->Message:{}",ex);
//        } catch (UnsupportedJwtException e){
//            log.error("Unsupported jwt token->Message:{}",e);
//        } catch (IllegalArgumentException e){
//            log.error("jwt class is empty->Message:{}",e);
//        }
//        catch (Exception e){
//            throw new RuntimeException(e);
//        }
//        return false;
//    }

}
