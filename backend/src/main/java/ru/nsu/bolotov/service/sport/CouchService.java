package ru.nsu.bolotov.service.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.sport.CouchRepository;
import ru.nsu.bolotov.model.dto.sport.CouchCreationDto;
import ru.nsu.bolotov.model.dto.sport.CouchInfoDto;
import ru.nsu.bolotov.model.entity.sport.Couch;
import ru.nsu.bolotov.model.exception.sport.CouchNotFoundException;
import ru.nsu.bolotov.model.mapper.CouchMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouchService {
    private final CouchRepository couchRepository;
    private final CouchMapper couchMapper;

    @Transactional
    public long createCouch(CouchCreationDto couchDto) {
        log.info("Received event for couch with full name: {} {}", couchDto.getFirstName(), couchDto.getLastName());
        Couch couch = couchMapper.map(couchDto);
        Couch savedCouch = couchRepository.save(couch);
        return savedCouch.getCouchId();
    }

    public CouchInfoDto getCouchInfo(long couchId) {
        Couch couch = couchRepository.findCouchByCouchId(couchId)
                .orElseThrow(() -> new CouchNotFoundException("Тренер не найден"));
        return couchMapper.map(couch);
    }
}
