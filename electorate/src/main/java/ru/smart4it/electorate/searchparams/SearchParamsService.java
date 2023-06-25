package ru.smart4it.electorate.searchparams;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchParamsService {

    private final SearchParamRepository repository;

    public List<SearchParamDto> getParameters() {
        return repository.findAll().stream()
                .map(param -> new SearchParamDto(
                        param.getId(),
                        param.getText(),
                        param.isDisabled()
                ))
                .collect(Collectors.toList());
    }

    public void save(SearchParam parameter) {
        repository.save(parameter);
    }

    @Transactional
    public void update(UUID id) {
        repository.findById(id).ifPresent(param -> {
            param.setDisabled(true);
            repository.save(param);
        });
    }
}
