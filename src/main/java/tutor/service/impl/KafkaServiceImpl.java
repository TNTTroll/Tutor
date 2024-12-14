package tutor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaServiceImplementation {
    public static final String TOPIC_TUTOR = "tutor";

    private final KafkaTemplate<String, String> tutorKafkaTemplate;
    private final AtomicLong offset = new AtomicLong(0);
    
    @Override
    public Mono<TutorEntity> sendTutor(TutorEntity tutor) {
        tutorKafkaTemplate.send(TOPIC_TUTOR, tutor.getId());
        return Mono.just(tutor);
    }

    @KafkaListener(topics = TOPIC_TUTOR, containerFactory = "tutorKafkaListenerContainerFactory")
    @Override
    public Mono<String> getTutorId() {
        String tutorId = tutorKafkaTemplate.receive(TOPIC_TUTOR, 0, offset.getAndIncrement()).value();
        return Mono.just(tutorId);
    }
}
