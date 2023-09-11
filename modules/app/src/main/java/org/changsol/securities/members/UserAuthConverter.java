package org.changsol.securities.members;

import java.util.Map;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;

public class UserAuthConverter extends DefaultUserAuthenticationConverter {

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        try {
            return super.extractAuthentication(map);
        } catch (UnauthorizedUserException ex) {
            ex.printStackTrace();
            return new UsernamePasswordAuthenticationToken("anonymousUser", "N/A", null);
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
            return null;
        }
    }
}
