package com.van.services.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {
    String title;
    @Builder.Default
    Double value = 0.0;
    @Builder.Default
    Double totalPrice = 0.0;
}
