package fr.ensibs.bakery.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import fr.ensibs.bakery.model.User;
import fr.ensibs.bakery.model.UserDAO;

import java.sql.SQLException;
import java.util.Date;

import static fr.ensibs.bakery.impl.Constants.JWT_ISSUER;
import static fr.ensibs.bakery.impl.Constants.JWT_SECRET;

/**
 * Class handling authentication.
 */
public final class Auth {

    /**
     * Private constructor to enforce noninstanciability.
     */
    private Auth() {
        throw new AssertionError();
    }

    /**
     * Sign a new JWT.
     * @param name the name of the user owning the token
     * @return the signed JWT
     */
    public static String sign(String name, boolean isAdmin) {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
        // the token expires one hour after the issue
        long expiresAt = System.currentTimeMillis() + 3600 * 1000;
        String token = JWT.create()
                .withIssuer(JWT_ISSUER)
                .withSubject(name)
                .withClaim("admin", isAdmin)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(expiresAt))
                .sign(algorithm);
        return token;
    }

    /**
     * Verify a given token.
     * @param token the token to verify
     * @param isAdmin true if the user must be an admin
     * @return the name of the user owning the token if it is valid, null otherwise
     */
    public static String verify(String token, boolean isAdmin) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(JWT_ISSUER)
                    .withClaim("admin", isAdmin)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
