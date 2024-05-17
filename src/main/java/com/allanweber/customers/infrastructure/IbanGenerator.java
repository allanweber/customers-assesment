package com.allanweber.customers.infrastructure;

import com.allanweber.customers.customer.CustomerAccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class IbanGenerator {

    private static final String BANK = "ABNA";

    private final CustomerAccountRepository customerAccountRepository;

    public IbanGenerator(CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    public String generate(String country) {
        return generateIban(country);
    }

    private String generateIban(String country) {
        String iban;
        String bankAndBBAN = BANK + randomAccountNumber();
        String digits = calculateDigits(bankAndBBAN, country);
        iban = country + digits + bankAndBBAN;
        if (customerAccountRepository.findByIban(iban).isPresent()) {
            iban = generateIban(country);
        }
        return iban;
    }

    private String randomAccountNumber() {
        String chars = "0123456789";
        return new Random().ints(10, 0, chars.length())
                .mapToObj(i -> String.valueOf(chars.charAt(i)))
                .collect(Collectors.joining());
    }

    private String calculateDigits(String bankAndBBAN, String countryCode) {

        String code = bankAndBBAN + countryCode + "00";

        String numericCode = alphaToNumeric(code);

        BigInteger bigIntegerCode = new BigInteger(numericCode);

        String remainder = bigIntegerCode.mod(new BigInteger("97")).toString();

        int intCheckDigits = 98 - Integer.parseInt(remainder);

        String checkDigits = Integer.toString(intCheckDigits);

        return "00".substring(checkDigits.length()) + checkDigits;
    }

    public static String alphaToNumeric(String toConvert) {
        String converted = toConvert.toUpperCase(Locale.getDefault());
        converted = converted.replaceAll("A", "10");
        converted = converted.replaceAll("B", "11");
        converted = converted.replaceAll("C", "12");
        converted = converted.replaceAll("D", "13");
        converted = converted.replaceAll("E", "14");
        converted = converted.replaceAll("F", "15");
        converted = converted.replaceAll("G", "16");
        converted = converted.replaceAll("H", "17");
        converted = converted.replaceAll("I", "18");
        converted = converted.replaceAll("J", "19");
        converted = converted.replaceAll("K", "20");
        converted = converted.replaceAll("L", "21");
        converted = converted.replaceAll("M", "22");
        converted = converted.replaceAll("N", "23");
        converted = converted.replaceAll("O", "24");
        converted = converted.replaceAll("P", "25");
        converted = converted.replaceAll("Q", "26");
        converted = converted.replaceAll("R", "27");
        converted = converted.replaceAll("S", "28");
        converted = converted.replaceAll("T", "29");
        converted = converted.replaceAll("U", "30");
        converted = converted.replaceAll("V", "31");
        converted = converted.replaceAll("W", "32");
        converted = converted.replaceAll("X", "33");
        converted = converted.replaceAll("Y", "34");
        converted = converted.replaceAll("Z", "35");
        return converted;
    }
}
