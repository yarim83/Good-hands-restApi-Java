package pl.coderslab.charity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table
public class Donation extends AbstractEntity {

    private Integer quantity;

    private String street;

    private String city;

    private String zipCode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;

    @JsonFormat(pattern = "HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime pickUpTime;

    private String pickUpComment;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    private List<Category> category = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "institution_id")
    private Institution institution;
}
