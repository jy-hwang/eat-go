package kr.co.fastcampus.eatgo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue
    @Setter
    private Long id;

    @NotNull
    private Long categoryId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<MenuItem> menuItems;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Review> reviews;

    public String getInformation() {
        return name + " in " + address;
    }

    public void updateInformation(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = new ArrayList<>(menuItems);
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = new ArrayList<>(reviews);
    }


}
