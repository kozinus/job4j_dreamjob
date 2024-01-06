package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private int nextId = 1;

    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Experience is 1 year",
                LocalDateTime.of(2022, 9, 23, 14, 22)));
        save(new Vacancy(0, "Junior Java Developer", "Experience is 1.5 year",
                LocalDateTime.of(2022, 4, 1, 15, 33)));
        save(new Vacancy(0, "Junior+ Java Developer", "Experience is 2 years",
                LocalDateTime.of(2021, 10, 9, 11, 22)));
        save(new Vacancy(0, "Middle Java Developer", "Experience is 3 years",
                LocalDateTime.of(2020, 5, 26, 19, 5)));
        save(new Vacancy(0, "Middle+ Java Developer", "Experience is 5 years",
                LocalDateTime.of(2018, 9, 23, 14, 22)));
        save(new Vacancy(0, "Senior Java Developer", "Experience is 5.5 years",
                LocalDateTime.of(2018, 7, 4, 14, 22)));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> new Vacancy(oldVacancy.getId(), vacancy.getTitle(),
                vacancy.getDescription(), vacancy.getCreationDate())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }

}