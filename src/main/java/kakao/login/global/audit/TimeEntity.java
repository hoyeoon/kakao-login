package kakao.login.global.audit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class TimeEntity {

    @Column(updatable = false)
    private OffsetDateTime createdDate;

    private OffsetDateTime updatedDate;

}
