package tutor.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaServiceImplementation {
    public static final String TOPIC_TUTOR = "tutor";

    private final KafkaTemplate<String, String> tutorKafkaTemplate;

    @Override
    public Mono<String> sendTutor(String tutorId) {
        tutorKafkaTemplate.send(TOPIC_TUTOR, tutorId);
        return Mono.just(tutorId);
    }

    @KafkaListener(topics = TOPIC_TUTOR, containerFactory = "tutorKafkaListenerContainerFactory")
    @Override
    public Mono<String> getTutorId() {
        String tutorId = tutorKafkaTemplate.receive(TOPIC_TUTOR, 1, 1).value();
        return Mono.just(tutorId);
    }
}
