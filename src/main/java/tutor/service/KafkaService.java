package tutor.service;

import tutor.entity.TutorEntity;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import reactor.core.publisher.Mono;

public interface KafkaService<K, V> {
    Mono<TutorEntity> sendTutor(TutorEntity tutor);
    Mono<String> getTutorId();
}
