package pl.coderslab.charity.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@Table
public class Category extends AbstractEntity{

    @NonNull
    @NotEmpty
    private String name;

}
