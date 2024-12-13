package tutor.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tutor.dto.TutorDto;
import tutor.mapper.TutorMapper;
import tutor.service.TutorService;
import tutor.util.Util;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tutor")
@Slf4j
public class TutorControllerImpl {

    private final TutorService tutorService;
    private final TutorMapper tutorMapper;

    @PostMapping
    public Mono<String> sendTutor(@RequestParam String layerId,
                                    @RequestParam String authorId,
                                    @RequestBody String tutor) {
        return tutorService.sendTutor(layerId, authorId, tutor)
                .map(entity -> Util.SUCCESS);
    }

    @GetMapping("/to_show")
    public Mono<TutorDto> getTutor(@RequestParam String layerId,
                                       @RequestParam String userId,
                                       @RequestParam String tutorId) {
        return tutorService.getTutor(layerId, userId, tutorId)
                .map(tutorMapper::toDto);
    }

    @GetMapping("/to_post")
    public Mono<TutorDto> getTutor(@RequestParam String userId) {
        return tutorService.getTutor(userId)
                .map(tutorMapper::toDto);
    }

    @GetMapping("/all")
    public Flux<TutorDto> getTutors(@RequestParam String layerId,
                                        @RequestParam String userId) {
        return tutorService.getTutors(layerId, userId)
                .map(tutorMapper::toDto);
    }

    @DeleteMapping
    public Mono<String> deleteTutor(@RequestParam String tutorId,
                                      @RequestParam String userId,
                                      @RequestParam String layerId){
        return tutorService.deleteTutor(tutorId, userId, layerId);
    }
}
