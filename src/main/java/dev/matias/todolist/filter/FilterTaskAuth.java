package dev.matias.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dev.matias.todolist.users.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();
        if (!servletPath.startsWith("/tasks/")) {
            filterChain.doFilter(request, response);
            return;
        }
        // get user and password

        var authorization = request.getHeader("Authorization");

        // "Basic" => type of authorization
        var authEncoded = authorization.substring("Basic".length()).trim();

        // Decode, but in bytes
        byte[] authBytes = Base64.getDecoder().decode(authEncoded);

        // Transform from bytes to String (returns "username:password")
        String authDecoded = new String(authBytes);

        // split the string and put into an array (["username", "password"])
        String[] credentials = authDecoded.split(":");

        String usernameUncoded = credentials[0];
        String passwordUncoded = credentials[1];

        // validate user

        var user = this.iUserRepository.findByUsername(usernameUncoded);

        if (user == null) {
            response.sendError(401, "Unauthorized");
            return;
        }

        // validate password

        var passwordVerification = BCrypt.verifyer().verify(passwordUncoded.toCharArray(), user.getPassword());

        if (!passwordVerification.verified) {
            response.sendError(401, "unauthorized");
            return;
        }

        request.setAttribute("idUser", user.getId());
        filterChain.doFilter(request, response);
    }

}
