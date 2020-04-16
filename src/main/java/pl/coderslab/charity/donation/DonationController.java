package pl.coderslab.charity.donation;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.converter.DonationConverter;
import pl.coderslab.charity.dto.DonationDto;
import pl.coderslab.charity.model.Donation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log4j
@RequestMapping("/api/donation")
public class DonationController {

    private final DonationRepository donationRepository;
    private final DonationConverter donationConverter;

    private HttpHeaders httpHeaders;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<Donation> getAll() {
        try {
            return donationRepository.findAll();
        } catch (NullPointerException e) {
            log.error(e);
            return new ArrayList<>();
        }
    }

    @GetMapping("/count/")
    public Object getNumberOfDonations() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("donationBags", donationRepository.findAll().size());
            jsonObject.put("bagsDonated", getSumOfBags());
            return jsonObject.toString();
        } catch (NullPointerException e) {
            log.error(e);
            return null;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDto> getById(@PathVariable Long id) {
        try {
            Optional<Donation> donationOptional = donationRepository.findById(id);
            if (!donationOptional.isPresent()) {
                httpHeaders.add("errors", "can't find entity");
                log.error("can't find entity with ID:" + id);
                return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
            } else {
                DonationDto donationDto = donationOptional.map(donationConverter::toDto).orElse(null);
                return new ResponseEntity<>(donationDto, HttpStatus.OK);
            }
        } catch (NullPointerException ex) {
            return new ResponseEntity<>(httpHeaders, HttpStatus.SERVICE_UNAVAILABLE);
        }

    }

    @PostMapping("/")
    public ResponseEntity<DonationDto> add(@RequestBody DonationDto donationDto) {
        if (donationDto == null) {
            httpHeaders.add("errors", "can't add entity");
            log.error("can't add entity" + null);
            return new ResponseEntity<>(httpHeaders, HttpStatus.BAD_REQUEST);
        } else {
            new Donation();
            Donation donation;
            System.out.println(donationDto.toString());
            donation = donationConverter.fromDto(donationDto);
            donationRepository.save(donation);
            return new ResponseEntity<>(donationDto, HttpStatus.CREATED);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donation> update(@RequestBody Donation donation) {
        if (!donationRepository.findById(donation.getId()).isPresent()) {
            httpHeaders.add("errors", "can't add entity");
            log.error("can't add entity" + donation);
            return new ResponseEntity<Donation>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
        donationRepository.save(donation);
        return new ResponseEntity<>(donation, HttpStatus.CREATED);
    }

    private Integer getSumOfBags() {
        Integer totalBagsDonated = 0;
        List<Donation> all = donationRepository.findAll();
        for (Donation donation : all) {
            totalBagsDonated = totalBagsDonated + donation.getQuantity();
        }
        return totalBagsDonated;
    }
}

