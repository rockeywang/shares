package com.wangchao.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/hive")
public class HiveController {
    private static final Logger logger = LoggerFactory.getLogger(HiveController.class);

    @Autowired
    @Qualifier("hiveJdbcTemplate")
    private JdbcTemplate hiveJdbcTemplate;

    @PostMapping("/create")
    public String create() {

        StringBuffer sql = new StringBuffer("create table IF NOT EXISTS ");
        sql.append("HIVE_TEST");
        sql.append("(KEY INT, VALUE STRING)");
        sql.append("PARTITIONED BY (CTIME DATE)"); // 分区存储
        sql.append("ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' LINES TERMINATED BY '\n' "); // 定义分隔符
        sql.append("STORED AS TEXTFILE"); // 作为文本存储

        logger.info(sql.toString());
        hiveJdbcTemplate.execute(sql.toString());

        return "ok";

    }

    @PostMapping("/insert")
    public String insert() {
        hiveJdbcTemplate.execute("insert into hive_test(key, value) values(12,'Chen')");
        return "Done";
    }

    @PostMapping("/select")
    public String select() {
        String sql = "select * from student";
        List<Map<String, Object>> rows = hiveJdbcTemplate.queryForList(sql);
        Iterator<Map<String, Object>> it = rows.iterator();
        while (it.hasNext()) {
            Map<String, Object> row = it.next();
            System.out.println(String.format("%s\t%s", row.get("key"), row.get("value")));
        }
        return "Done";
    }

    @PostMapping("/delete")
    public String delete() {
        StringBuffer sql = new StringBuffer("DROP TABLE IF EXISTS ");
        sql.append("HIVE_TEST");
        logger.info(sql.toString());
        hiveJdbcTemplate.execute(sql.toString());
        return "Done";
    }
}
