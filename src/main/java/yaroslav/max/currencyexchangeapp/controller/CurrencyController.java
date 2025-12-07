package yaroslav.max.currencyexchangeapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import yaroslav.max.currencyexchangeapp.service.CurrencyService;

@Controller
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/")
    public String showCurrenciesPage(Model model) {
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        return "currencies";
    }

    @PostMapping("/add-currency")
    public String addCurrency(@RequestParam String code,
                              @RequestParam String displayName) {
        currencyService.createCurrency(code, displayName);
        return "redirect:/";
    }
}
