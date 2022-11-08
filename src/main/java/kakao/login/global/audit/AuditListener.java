package kakao.login.global.audit;

import org.springframework.context.annotation.Configuration;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.OffsetDateTime;

@Configuration
public class AuditListener {
    @PrePersist
    public void setCreateDate(Auditable auditable) {
        TimeEntity timeEntity = auditable.getTimeEntity();

        if (timeEntity == null) {
            timeEntity = new TimeEntity();
            auditable.setTimeEntity(timeEntity);
        }
        timeEntity.setCreatedDate(OffsetDateTime.now());
        timeEntity.setUpdatedDate(OffsetDateTime.now());
    }

    @PreUpdate
    public void setUpdateDate(Auditable auditable){
        auditable.getTimeEntity().setUpdatedDate(OffsetDateTime.now());
    }
}