package dgsw.hackathon.DaeItSo.global.config.webclient.parser;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HeaderParser {
    private String type;
    private String value;
}
