package com.project.questapp.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${questapp.app.secret}")
    private String APP_SECRET; //token için key oluştururken oluşturacağımız key.
    @Value("${questapp.expires.init}")
    private Long EXPIRES_IN; //saniye cinsinden kaç saniyede tokenlar expire oluyor.

    //tokenın generete olacağı method.
    public String generateJwtToken(Authentication auth){
        JwtUserDetails userDetails = (JwtUserDetails) auth.getPrincipal(); //authentication edeceğimiz user demek. onu alıp jwt user objesine cast ettim
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        //token olusturalım.
        return Jwts.builder().setSubject(Long.toString(userDetails.getId()))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,APP_SECRET)
                .compact();
    }

    public String generateJwtTokenByUserId(Long userId) {
        Date expireDate = new Date(new Date().getTime() + EXPIRES_IN);
        return Jwts.builder().setSubject(Long.toString(userId))
                .setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, APP_SECRET).compact();
    }

    //tokenı çözüyoruz. Token içinden kull. id'sini çekiyoruz.
    Long getUserIdFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(APP_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }
    /**
     * front endden bize bir istek geldiğinde bize bir token ile gelir.
     * Bu token doğru mu bizim oluşturduğumuz token mı kontrol etmemiz gerek.
     * Bu methodu kullanıyoruz.
     */
    boolean validateToken(String token){
        try{
            // app_secret ile parse edebiliyorsak bizim uyg. olusturdugu tokendır.
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token);
            return !isTokenExpired(token);
        }catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException |
                IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * token expire olmuş mu kontrol ediyoruz. ve üstteki methotta kullanıyoruz.
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(token)
                .getBody().getExpiration();
        return expiration.before(new Date());
    }


}
