package com.example.bootssm1.domain;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BookInfo {

    private Integer bookId;
    private String bookName;
    private String bookAuthor;
    private BigDecimal bookPrice;
    private Date bookDate;

}
