package yaroslav.max.currencyexchangeapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import yaroslav.max.currencyexchangeapp.entity.Currency;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByCode(String code);
    Page<Currency> findByCodeContainingIgnoreCase(String code, Pageable pageable);
}
