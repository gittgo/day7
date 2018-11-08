package com.example.bootssm1.mapper;

import com.example.bootssm1.domain.BookInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    BookInfo getBookById(Integer bookId);

    Integer saveBook(BookInfo bookInfo);

    Integer batchBook(List<BookInfo> list);
}
