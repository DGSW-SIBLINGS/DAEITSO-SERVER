package dgsw.hackathon.DaeItSo.global.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app.dodam")
public class DodamProperties {
    private String dauth;
    private String openDodam;
}
