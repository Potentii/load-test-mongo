package com.example.demo.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Document(collection = "pagamentos")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "_id" })
public class Pagamento {
    
    @Id
    @Getter
    @Setter
    private String _id;

    @Getter
    @Setter
    private String a;

    @Getter
    @Setter
    private String b;

    @Getter
    @Setter
    private String c;

    @Getter
    @Setter
    private String type;

    @Getter
    @Setter
    private BigDecimal value;

    @Getter
    @Setter
    private LocalDate date;

    @Getter
    @Setter
    private UUID uuid;

}

