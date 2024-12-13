package tutor.service.impl;

import org.springframework.kafka.annotation.KafkaListener;
import reactor.core.publisher.Mono;

public interface KafkaServiceImplementation {
    Mono<String> sendTutor(String tutorId);

    @KafkaListener(topics = KafkaServiceImpl.TOPIC_TUTOR, containerFactory = "tutorKafkaListenerContainerFactory")
    Mono<String> getTutorId();
}
