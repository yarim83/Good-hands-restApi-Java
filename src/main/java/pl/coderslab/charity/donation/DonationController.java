package pl.coderslab.charity.donation;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.charity.model.Donation;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log4j
@RequestMapping("/api/donation")
public class DonationController {

    private final DonationRepository donationRepository;

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
    public Donation getById(@PathVariable Long id) {
        Optional<Donation> donationOptional = donationRepository.findById(id);
        return donationOptional.orElse(null);
    }

    @PostMapping("/")
    public ResponseEntity<Donation> add(@RequestBody Donation donation, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || donation == null) {
            httpHeaders.add("errors", "can't add entity");
            log.error("can't add entity" + donation);
            return new ResponseEntity<Donation>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
        donationRepository.save(donation);
        return new ResponseEntity<Donation>(donation, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Donation> update(@RequestBody Donation donation, BindingResult bindingResult) {
        if (!donationRepository.findById(donation.getId()).isPresent() ||
                bindingResult.hasErrors()) {
            httpHeaders.add("errors", "can't add entity");
            log.error("can't add entity" + donation);
            return new ResponseEntity<Donation>(httpHeaders, HttpStatus.BAD_REQUEST);
        }
        donationRepository.save(donation);
        return new ResponseEntity<Donation>(donation, HttpStatus.CREATED);
    }



    private Integer getSumOfBags() {
        Integer totalBagsDonated = 0;
        List<Donation> all = donationRepository.findAll();
        for(Donation donation: all){
            totalBagsDonated = totalBagsDonated + donation.getQuantity();
        }
        return totalBagsDonated;
    }


}

