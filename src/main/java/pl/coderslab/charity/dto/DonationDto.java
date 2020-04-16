package pl.coderslab.charity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class DonationDto {

    private Long id;

    private Integer quantity;

    private String street;

    private String city;

    private String zipCode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;

    @JsonFormat(pattern = "HH:mm")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime pickUpTime;

    private String pickUpComment;

    private List<Long> categoryIds = new ArrayList<>();

    private Long institutionId;
}
