package cs.vapo.bringit.core.tools;

import cs.vapo.bringit.core.dao.model.UserForLoginDM;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUserTools {

    private CurrentUserTools() {
        
    }

    /**
     * Retrieves the current authenticated user's ID from the Security Context.
     * @return the current user's ID
     */
    public static String retrieveCurrentUserId() {
        final UserForLoginDM principal =
                (UserForLoginDM) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }
}
