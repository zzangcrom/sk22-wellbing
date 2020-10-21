
package nosmokes;

public class EatEarned extends AbstractEvent {

    private Long id;
    private Long checkInid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCheckInid() {
        return checkInid;
    }

    public void setCheckInid(Long checkInid) {
        this.checkInid = checkInid;
    }
}
