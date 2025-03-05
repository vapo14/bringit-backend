package cs.vapo.bringit.core.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfiguration {


    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Value("${bringit.security.jwt.signing.key}")
    private String jwtSigningKey;

    @Value("${bringit.config.user.contacts.pageSize}")
    private int userContactsListPageSize;

    public int getUserContactsListPageSize() {
        return userContactsListPageSize;
    }

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }
}
