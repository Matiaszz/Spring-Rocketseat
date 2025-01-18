package dev.matias.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // get user and password

        var authorization = request.getHeader("Authorization");
        System.out.println("Authorization " + authorization);

        // "Basic" => type of authorization
        var authEncoded = authorization.substring("Basic".length()).trim();

        // Decode, but in bytes
        byte[] authBytes = Base64.getDecoder().decode(authEncoded);

        // Transform from bytes to String (returns "username:password")
        String authDecoded = new String(authBytes);

        // split the string and put into an array (["username", "password"])
        String[] credentials = authDecoded.split(":");

        String username = credentials[0];
        String password = credentials[1];

        System.out.println(username);
        System.out.println(password);
        // validate user

        // validate password

        filterChain.doFilter(request, response);
    }

}
