package pl.coderslab.charity.institution;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.model.Institution;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log4j
@RequestMapping("/api/institution")
public class InstitutionController {

    private final InstitutionRepository institutionRepository;

    private HttpHeaders httpHeaders;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Institution> getAll() {
        try {
            return institutionRepository.findAll();
        } catch (NullPointerException e) {
            log.error(e);
            return new ArrayList<>();
        }
    }

    @GetMapping("/page/{page}/limit/{limit}")
    @ResponseStatus(HttpStatus.OK)
    public Page<Institution> getAll(@PathVariable Integer page, @PathVariable Integer limit) {
        try {
            return institutionRepository.findAll(PageRequest.of(page, limit));
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    @GetMapping("/count/")
    public int getNumberOfInstitutions() {
        try {
            return institutionRepository.findAll().size();
        } catch (NullPointerException e) {
            log.error(e);
            return 0;
        }
    }

    @GetMapping("/{id}")
    public Institution getById(@PathVariable Long id) {
        Optional<Institution> InstitutionOptional = institutionRepository.findById(id);
        return InstitutionOptional.orElse(null);
    }

    @PostMapping("/")
    public ResponseEntity<Institution> add(@RequestBody Institution Institution, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || Institution == null) {
            httpHeaders.add("errors", "can't add entity");
            log.error("can't add entity" + Institution);
            return new ResponseEntity<Institution>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
        institutionRepository.save(Institution);
        return new ResponseEntity<Institution>(Institution, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Institution> update(@RequestBody Institution Institution, BindingResult bindingResult) {
        if (!institutionRepository.findById(Institution.getId()).isPresent() ||
                bindingResult.hasErrors()) {
            httpHeaders.add("errors", "can't add entity");
            log.error("can't add entity" + Institution);
            return new ResponseEntity<Institution>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
        institutionRepository.save(Institution);
        return new ResponseEntity<Institution>(Institution, HttpStatus.CREATED);
    }
}
