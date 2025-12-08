package yaroslav.max.currencyexchangeapp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "currencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, length = 3)
    private String code;

    private String displayName;

    public Currency(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }
}