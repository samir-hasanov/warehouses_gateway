package www.stock.az.dto.request.warehousesorder;

import lombok.Data;
import www.stock.az.enums.PaymentType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentCreateRequest {
    private String documentNumber;
    private LocalDateTime paymentDate;
    private String description;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal amount;
    private String purpose;
    private PaymentType paymentType;
    private String counterpartyAccount;
    private String counterpartyVoen;
    private String counterpartyName;
    private BigDecimal runningBalance;
    private Long orderId;
    private Long invoiceId;
    private String currency;
}
