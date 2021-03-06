package com.dbteam.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Document
public class Payment {

    @Transient
    public static final String SEQUENCE_NAME = "payments_sequence";

    @Id
    Long paymentId;
    Long groupChatId;
    LocalDateTime date;
    String payer;
    Double amount;
    String recipient;
    Boolean isConfirmed;
}
