package yaroslav.max.currencyexchangeapp.entity;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Currency {
    private String code;
    private String displayName;
}
