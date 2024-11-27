package com.xazhao.cache;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.xazhao.cache.core.RedisCache;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

/**
 * @Description Created on 2024/11/27.
 * @Author Zhao.An
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisCacheTest {

    @Resource
    private RedisCache redisCache;


    /**
     * 存储基本数据
     */
    @Test
    public void redisSetTest() {

        JSON users = JSONUtil.parse(USER_AN);
        JSON usersZhao = JSONUtil.parse(USER_ZHAO);

        redisCache.setCache("age", 26);

        redisCache.setCache("Name", "Zhao.An");

        redisCache.setCache("Users", users);

        redisCache.setCache("HashUser", "HashUS_A", users);
        redisCache.setCache("HashUser", "HashUS_B", usersZhao);

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("Map_A", "Map_A");
        hashMap.put("Map_B", "Map_B");
        redisCache.setCache("hashMap", hashMap);
        // 多级目录
        redisCache.setCache("A:B:C:hashMap", hashMap);

        HashSet<JSON> usersSet = new HashSet<>();
        usersSet.add(users);
        usersSet.add(usersZhao);
        redisCache.setCache("usersSet", usersSet);

        HashSet<String> strSet = new HashSet<>();
        strSet.add("Set_A");
        strSet.add("Set_B");
        redisCache.setCache("strSet", strSet);

        HashSet<Integer> intSet = new HashSet<>();
        intSet.add(101);
        intSet.add(102);
        redisCache.setCache("intSet", intSet);

        List<JSON> usersList = new ArrayList<>();
        usersList.add(users);
        usersList.add(usersZhao);
        redisCache.setCache("usersList", usersList);

        List<String> strList = new ArrayList<>();
        strList.add("List_A");
        strList.add("List_B");
        redisCache.setCache("strList", strList);

        List<Integer> intList = new ArrayList<>();
        intList.add(909);
        intList.add(908);
        redisCache.setCache("intList", intList);

        redisCache.setCache("ageExpire", 26, 2L);

        redisCache.setCache("NameExpire", "Zhao.An", 5L);

        redisCache.setCache("UsersExpire", users, 3L);

        redisCache.setCache("HashUserExpire", "HashUS_C", users, 15L);
        redisCache.setCache("HashUserExpire", "HashUS_D", usersZhao, 25L);

        HashSet<JSON> usersSetExpire = new HashSet<>();
        usersSetExpire.add(users);
        usersSetExpire.add(usersZhao);
        redisCache.setCache("usersSetExpire", usersSetExpire, 3L);

        List<JSON> usersListExpire = new ArrayList<>();
        usersListExpire.add(users);
        usersListExpire.add(usersZhao);
        redisCache.setCache("usersListExpire", usersListExpire, 8L);

        Map<String, Object> hashMapExpire = new HashMap<>();
        hashMapExpire.put("Map_A", "Map_A");
        hashMapExpire.put("Map_B", "Map_B");
        redisCache.setCache("hashMapExpire", hashMapExpire, 12L);

        // ZSet
        redisCache.setCache("ZSetName", "Zhao.An", 4.253D);
        redisCache.setCache("ZSetName", "Zhao.Jia", 3.52D);
        redisCache.setCache("ZSetName", "Zhao", 90.243D);

        redisCache.setCache("ZSetAge", "28", 23.243D);

        redisCache.setCache("ZSetNameExpire", "users", 56.243D, 30L);

        Set<Object> zSetName = redisCache.rangeZSet("ZSetName", 2, 3);
        log.info(zSetName.toString());
        Set<Object> zSetName1 = redisCache.rangeZSet("ZSetName");
        log.info(zSetName1.toString());

        Set<Object> reverseRangeZSet = redisCache.reverseRangeZSet("ZSetName", 2, 3);
        log.info(reverseRangeZSet.toString());
        Set<Object> reverseRangeZSet1 = redisCache.reverseRangeZSet("ZSetName");
        log.info(reverseRangeZSet1.toString());

        Long zSetName2 = redisCache.getZSetSize("ZSetName");
        log.info(zSetName2.toString());

        log.info(redisCache.getZSetCard("ZSetName").toString());
        log.info(redisCache.getZSetScore("ZSetName", "Zhao").toString());
        log.info(redisCache.getCountZSet("ZSetName", 3.23, 91.12).toString());

        Double zSetName3 = redisCache.incrementScoreZSet("ZSetName", "Zhao.Jia", 5.12);
        log.info("增加后：" + zSetName3);

        log.info(redisCache.rankZSet("ZSetName", "Zhao").toString());
        log.info(redisCache.reverseRankZSet("ZSetName", "Zhao.Jia").toString());
    }

    private static final String USER_AN = """
            {
                "id": 1,
                "nick_name": "Zhao.An",
                "nick_code": "W100001",
                "password_hash": "12345678",
                "salt": null,
                "telephone": "17xxxxxx23",
                "email": "Zhao.An@126.com",
                "profile_photo_url": null,
                "certification": 1,
                "real_name": "Zhao.An",
                "id_card_no": "6103xxxxxxx",
                "user_role": null,
                "place_org": null,
                "place_dept": null,
                "duty_block": null,
                "state": "ACTIVE",
                "deleted": 0,
                "last_login_time": "2024/08/12 14:28:36",
                "lock_version": null,
                "gmt_create": "2024/8/12 14:28:39",
                "gmt_modified": "2024/08/12 14:28:42",
                "temp": null
            }
            """;

    private static final String USER_ZHAO = """
            {
                "id": 2,
                "nick_name": "Zhao.An",
                "nick_code": "W100002",
                "password_hash": "12345678",
                "salt": null,
                "telephone": "176932409823",
                "email": "Zhao.An@126.com",
                "profile_photo_url": null,
                "certification": 1,
                "real_name": "Zhao.An",
                "id_card_no": "610324xxxxx",
                "user_role": null,
                "place_org": null,
                "place_dept": null,
                "duty_block": null,
                "state": "ACTIVE",
                "deleted": 0,
                "last_login_time": "2024/08/12 14:28:36",
                "lock_version": null,
                "gmt_create": "2024/08/12 14:28:39",
                "gmt_modified": "2024/08/12 14:28:42",
                "temp": null
            }
            """;
}
