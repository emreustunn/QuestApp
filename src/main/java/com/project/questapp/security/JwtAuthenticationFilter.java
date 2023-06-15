package com.project.questapp.security;

import com.project.questapp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailServiceImpl;
    /**
     * bu bir filter classı
     * bir request geldiğinde front endden back end tarafına
     * spring bir çok filtreleme yapar.
     * biz buraya ekstra bir aşama daha ekliyoruz.
     * bizim için jwt filtresi ekleyeceğiz.
     * bir request geldiğinde bu request authorize olmus mu? diye soracagız.
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            //request headirinda gelen tokenı çıkartmak...
            String jwtToken = extractJwtFromRequest(request);
            if(StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)){
                Long id = jwtTokenProvider.getUserIdFromJwt(jwtToken);
                UserDetails userDetails = userDetailServiceImpl.loadUserById(id);
                if (userDetails != null) {
                    //spring sec kendi auth içindeki class
                    UsernamePasswordAuthenticationToken auth=
                            new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                    //detay ekliyoruz
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    /**
                     * security ile alakalı şeyleri tutan local storage tarzı bi yapı var. aşağıdaki gibi
                     * local thread objesi yaratıyo ve bu user için gerekli şeyleri içerisinde tutar.
                     * bizde onun içerisine auth objemizi veriyoruz.
                     */
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch (Exception e) {
        }
        //ben gerekli şeyleri contextime ekledim sen devam et diyoruz.
        filterChain.doFilter(request,response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if(StringUtils.hasText(bearer) && bearer.startsWith("Bearer "))
            return bearer.substring("Bearer".length()+1);
        return null;
    }
}
