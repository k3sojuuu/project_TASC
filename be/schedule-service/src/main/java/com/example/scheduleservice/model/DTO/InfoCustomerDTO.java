package com.example.scheduleservice.model.DTO;

import com.example.scheduleservice.model.InfoCustomer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoCustomerDTO {
    private Long userId;
    private List<InfoCustomer> listInfo;
}
