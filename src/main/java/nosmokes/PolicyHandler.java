package nosmokes;

import nosmokes.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    EatRepository eatRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverEatEarned_UpdateEat(@Payload EatEarned eatEarned){

        if(eatEarned.isMe()){

            Optional<Eat> eatOptional = eatRepository.findById(eatEarned.getCheckInid());
            Eat eat = eatOptional.get();
            eat.setStatus("EARNED");

            eatRepository.save(eat);
        }
    }

}
