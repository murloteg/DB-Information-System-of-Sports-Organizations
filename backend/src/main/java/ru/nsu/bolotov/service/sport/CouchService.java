package ru.nsu.bolotov.service.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.bolotov.dao.sport.CouchRepository;
import ru.nsu.bolotov.model.dto.sport.couch.CouchCreationDto;
import ru.nsu.bolotov.model.dto.sport.couch.CouchInfoDto;
import ru.nsu.bolotov.model.dto.sport.couch.CouchUpdateDto;
import ru.nsu.bolotov.model.entity.sport.Couch;
import ru.nsu.bolotov.model.exception.sport.CouchNotFoundException;
import ru.nsu.bolotov.model.mapper.CouchMapper;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public void deleteCouch(long couchId) {
        if (couchRepository.existsByCouchId(couchId)) {
            couchRepository.deleteByCouchId(couchId);
        } else {
            throw new CouchNotFoundException("Тренер не найден");
        }
    }

    @Transactional
    public void updateCouch(CouchUpdateDto couchUpdateDto) {
        if (couchRepository.existsByCouchId(couchUpdateDto.getCouchId())) {
            couchRepository.updateCouchByCouchId(
                    couchUpdateDto.getFirstName(),
                    couchUpdateDto.getLastName(),
                    couchUpdateDto.getAge(),
                    couchUpdateDto.getSex(),
                    couchUpdateDto.getCouchId()
            );
        } else {
            throw new CouchNotFoundException("Тренер не найден");
        }
    }

    public List<CouchInfoDto> getCouches(Pageable pageable) {
        Page<Couch> couches = couchRepository.findAll(pageable);
        List<CouchInfoDto> couchInfoDtos = new ArrayList<>();
        for (Couch couch : couches) {
            couchInfoDtos.add(couchMapper.map(couch));
        }
        return couchInfoDtos;
    }
}
