package cs.vapo.bringit.core.config;

import io.swagger.v3.oas.models.info.Contact;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;

public class OpenApiProperties implements InitializingBean {

    private String title;

    private String version;

    private String description;

    private final Contact contact;

    OpenApiProperties(final Environment env) {
        
    }
}
