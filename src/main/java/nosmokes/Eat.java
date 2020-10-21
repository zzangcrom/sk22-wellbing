package nosmokes;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Eat_table")
public class Eat {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long number;
    private String status;

    @PostPersist
    public void onPostPersist(){
        GoodEaten goodEaten = new GoodEaten();
        BeanUtils.copyProperties(this, goodEaten);
        goodEaten.publishAfterCommit();

    }

    @PrePersist
    public void onPrePersist(){
        BadEaten badEaten = new BadEaten();
        BeanUtils.copyProperties(this, badEaten);
        badEaten.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        nosmokes.external.Deduct deduct = new nosmokes.external.Deduct();
        // mappings goes here
        deduct.setPoint(this.getNumber());
        deduct.setPayId(this.getId());
        WellbingApplication.applicationContext.getBean(nosmokes.external.DeductService.class)
                .pay(deduct);


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
