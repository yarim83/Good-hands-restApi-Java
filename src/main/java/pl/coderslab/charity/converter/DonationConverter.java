package pl.coderslab.charity.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import pl.coderslab.charity.category.CategoryRepository;
import pl.coderslab.charity.dto.DonationDto;
import pl.coderslab.charity.institution.InstitutionRepository;
import pl.coderslab.charity.model.Category;
import pl.coderslab.charity.model.Donation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Log4j
public class DonationConverter {

    private final CategoryRepository categoryRepository;
    private final InstitutionRepository institutionRepository;

    public DonationDto toDto(Donation donation) {
        DonationDto donationDto = new DonationDto();

        donationDto.setId(donation.getId());
        donationDto.setQuantity(donation.getQuantity());
        donationDto.setStreet(donation.getStreet());
        donationDto.setCity(donation.getCity());
        donationDto.setZipCode(donation.getZipCode());
        donationDto.setPickUpDate(donation.getPickUpDate());
        donationDto.setPickUpTime(donation.getPickUpTime());
        donationDto.setPickUpComment(donation.getPickUpComment());
//        donationDto.setCategoryIds(donation.getCategory());
        donationDto.setInstitutionId(donation.getInstitution().getId());

        return donationDto;
    }

    public Donation fromDto(DonationDto donationDto) {
        Donation donation = new Donation();

        donation.setQuantity(donationDto.getQuantity());
        donation.setStreet(donationDto.getStreet());
        donation.setCity(donationDto.getCity());
        donation.setZipCode(donationDto.getZipCode());
        donation.setPickUpDate(donationDto.getPickUpDate());
        donation.setPickUpComment(donationDto.getPickUpComment());
        for (int i = 0; i < donationDto.getCategoryIds().size(); i++) {
            List<Category> categories = new ArrayList<>();
            Long categoryId = donationDto.getCategoryIds().get(i);
            Optional<Category> categoryById = categoryRepository.findById(categoryId);
            if (categoryById.isPresent()) {
                categories.add(categoryById.get());
            } else {
                log.error("Category is'n present");
            }
            donation.setCategory(categories);
        }
        donation.setInstitution(institutionRepository.getOne(donationDto.getInstitutionId()));

        return donation;
    }

}

