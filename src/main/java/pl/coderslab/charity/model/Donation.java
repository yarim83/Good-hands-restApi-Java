package pl.coderslab.charity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

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

    @JsonFormat(pattern = "HH:MM:ss")
    private LocalTime pickUpTime;

    private String pickUpComment;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "institution_id")
    private Institution institution;
}
