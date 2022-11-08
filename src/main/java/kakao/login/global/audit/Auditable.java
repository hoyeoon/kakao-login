package kakao.login.global.audit;

public interface Auditable {
    TimeEntity getTimeEntity();
    void setTimeEntity(TimeEntity timeEntity);
}