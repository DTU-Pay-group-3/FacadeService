package utils;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
public class Payment {

     String paymentId;
     String merchantID;
     String customerToken;
     String description;
     BigDecimal amount;

}