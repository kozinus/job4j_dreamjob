package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import net.jcip.annotations.*;

@Repository
@ThreadSafe
public class MemoryCandidateRepository implements CandidateRepository {

    private final AtomicInteger nextId = new AtomicInteger(1);

    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Ivan", "Experience is 1 year",
                LocalDateTime.of(2022, 9, 23, 14, 22), 2, 0));
        save(new Candidate(0, "Kirill", "Experience is 1.5 year",
                LocalDateTime.of(2022, 4, 1, 15, 33), 3, 0));
        save(new Candidate(0, "Fedor", "Experience is 2 years",
                LocalDateTime.of(2021, 10, 9, 11, 22), 3, 0));
        save(new Candidate(0, "Maksim", "Experience is 3 years",
                LocalDateTime.of(2020, 5, 26, 19, 5), 1, 0));
        save(new Candidate(0, "Andrej", "Experience is 5 years",
                LocalDateTime.of(2018, 9, 23, 14, 22), 1, 0));
        save(new Candidate(0, "Anton", "Experience is 5.5 years",
                LocalDateTime.of(2018, 7, 4, 14, 22), 1, 0));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId.getAndIncrement());
        candidates.put(candidate.getId(), candidate);
        return candidate;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) -> new Candidate(oldCandidate.getId(), candidate.getName(),
                candidate.getDescription(), oldCandidate.getCreationDate(),
                candidate.getCityId(), candidate.getFileId())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}
