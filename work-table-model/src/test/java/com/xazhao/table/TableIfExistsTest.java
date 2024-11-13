package com.xazhao.table;

import com.xazhao.table.service.TableIfExistsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description Created on 2024/11/11.
 * @Author Zhao.An
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class TableIfExistsTest {

    @Resource
    private TableIfExistsService tableIfExistsService;

    @Test
    public void tableIfExistsTest() {

        tableIfExistsService.queryTableIfExists();
    }
}
