package pl.coderslab.charity.converter;

import org.springframework.stereotype.Component;
import pl.coderslab.charity.dto.InstitutionDto;
import pl.coderslab.charity.model.Institution;

@Component
public class InstitutionConverter {

    public InstitutionDto toDto(Institution institution){
        InstitutionDto institutionDto = new InstitutionDto();

        institutionDto.setId(institution.getId());
        institutionDto.setName(institution.getName());
        institutionDto.setDescription(institution.getDescription());

        return institutionDto;
    }

    public Institution fromDto(InstitutionDto institutionDto){
        return null;
    }
}
