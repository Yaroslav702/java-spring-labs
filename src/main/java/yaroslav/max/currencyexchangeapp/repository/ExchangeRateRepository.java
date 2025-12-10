package yaroslav.max.currencyexchangeapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import yaroslav.max.currencyexchangeapp.entity.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    Page<ExchangeRate> findBySourceCurrency_Code(String code, Pageable pageable);

    @Query("SELECT e FROM ExchangeRate e WHERE e.targetCurrency.code = :code")
    List<ExchangeRate> findByTargetCurrencyCode(@Param("code") String code);

    List<ExchangeRate> findByDateNamed(@Param("date") LocalDate date);
}