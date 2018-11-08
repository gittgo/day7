package com.example.bootssm1;

import com.example.bootssm1.domain.BookInfo;
import com.example.bootssm1.mapper.BookMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootSsm1ApplicationTests {

	@Autowired
//	@SuppressWarnings("SpringJavaAutowiringInspection")  //代码不报错处理
	private BookMapper bookMapper;

	@Test
	public void contextLoads() {
		BookInfo bookInfo = bookMapper.getBookById(1);
		System.out.println(bookInfo);
	}

	@Test
	public void save(){
		BookInfo bookInfo = bookMapper.getBookById(1);
		bookInfo.setBookName("新书");
		int row = bookMapper.saveBook(bookInfo);
		Assert.assertEquals(1,row);
	}

}
