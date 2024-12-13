package tutor.service;

import tutor.entity.TutorEntity;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import reactor.core.publisher.Mono;

public interface KafkaService<K, V> {
    Mono<String> sendTutor(String tutorId);
    Mono<String> getTutorId();
}
