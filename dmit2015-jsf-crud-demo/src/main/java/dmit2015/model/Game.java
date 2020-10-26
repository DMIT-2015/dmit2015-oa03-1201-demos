package dmit2015.model;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class Game {

    private UUID gameId;

    @NotBlank(message = "Enter a title for the game.")
    private String title;

    private String platform;    // PS4, Xbox One, Nintendo, PC Game

    @DecimalMin(value = "0.00", message = "Enter a purchase price of zero or more.")
    private BigDecimal purchasePrice;

    @PastOrPresent(message = "Enter a purchase date that is today or in the past")
    private LocalDate purchaseDate;
}
