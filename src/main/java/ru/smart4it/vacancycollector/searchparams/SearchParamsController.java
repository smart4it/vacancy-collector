package ru.smart4it.vacancycollector.searchparams;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vacancies")
public class SearchParamsController {

    private final SearchParamsService searchParamsService;

    @CrossOrigin
    @GetMapping("/search-parameters")
    public List<SearchParamDto> vacanciesSearchParamList() {
        return searchParamsService.getParameters();
    }

    @CrossOrigin
    @PostMapping("/search-parameters")
    public void save(@RequestBody SearchParam parameter) {
        searchParamsService.save(parameter);
    }

    @CrossOrigin
    @PatchMapping("/search-parameters")
    public void update(@RequestParam UUID id) {
        searchParamsService.update(id);
    }
}
