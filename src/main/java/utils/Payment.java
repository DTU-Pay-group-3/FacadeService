package utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.Value;

import java.math.BigDecimal;

@AllArgsConstructor
@Setter
@Data
@Value
public class Payment {

     String paymentId;
     String merchantID;
     String customerToken;
     String description;
     BigDecimal amount;

}