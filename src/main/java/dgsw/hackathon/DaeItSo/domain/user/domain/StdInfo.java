package dgsw.hackathon.DaeItSo.domain.user.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class StdInfo {

    private int grade;
    private int room;
    private int number;
}
