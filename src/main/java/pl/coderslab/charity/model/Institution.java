package pl.coderslab.charity.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table
public class Institution extends AbstractEntity{

    @NotEmpty
    @NotNull
    private String name;

    @NotEmpty
    @NotNull
    private String description;
}
