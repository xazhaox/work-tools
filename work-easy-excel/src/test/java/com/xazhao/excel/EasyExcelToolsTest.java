package com.xazhao.excel;

import cn.hutool.core.bean.BeanUtil;
import com.xazhao.core.config.MybatisProperties;
import com.xazhao.excel.entity.EasyExcelTools;
import com.xazhao.excel.mapper.EasyExcelToolsMapper;
import com.xazhao.excel.service.EasyExcelToolsService;
import com.xazhao.excel.service.MyBatisExcelService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class EasyExcelToolsTest {

    @Resource
    private EasyExcelToolsService easyExcelToolsService;

    @Resource
    private EasyExcelToolsMapper easyExcelToolsMapper;

    @Resource
    private Environment environment;

    @Resource
    private MybatisProperties mybatisProperties;

    @Resource
    private MyBatisExcelService batisExcelService;

    @Test
    public void millionExcelExportTest() {

        easyExcelToolsService.millionExcelExport(null);
    }

    @Test
    public void batchQuerySplitSheetWriteTest() {

        try {
            easyExcelToolsService.concurrentQuerySplitSheetWrite(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void concurrentQueryTest() {

        LinkedList<EasyExcelTools> easyExcelTools = easyExcelToolsService.concurrentQuery();
        System.out.println();
    }

    @Test
    public void getExcelCountTest() {

        easyExcelToolsMapper.grossAmountQuery();
    }

    @Test
    public void getExcalMapTest() {

        easyExcelToolsService.pageMapQuery();
    }

    @Test
    public void completeQueryTest() {

        easyExcelToolsService.completeQuery();
    }

    @Test
    public void readPropertiesTest() {

        log.info(BeanUtil.beanToMap(mybatisProperties).toString());

        String property = environment.getProperty("spring.application.name");
        log.info(property);

        String[] activeProfiles = environment.getActiveProfiles();
        log.info(Arrays.toString(activeProfiles));

        // 2147483647
        log.warn(String.valueOf(Integer.MAX_VALUE));
    }

    @Test
    public void pageQuery() {

        batisExcelService.pageQuery();
    }

    public static final String JSON = """
            [
              {
                "pageSize": 10,
                "pageNo": -1,
                "dataTotal": null,
                "countFlag": true,
                "channelId": 45039642,
                "channelName": "西安市区西华门营业厅",
                "channelLevelCd": null,
                "channelTypeCd": "110000",
                "channelSubtypeCd": "110100",
                "statusCd": "1000",
                "parentChnId": null,
                "channelNbr": "6101021020662",
                "commonRegionId": 290015,
                "provCode": "15020062",
                "channelSpecId": null,
                "capacity": null,
                "startDt": null,
                "endDt": null,
                "openTime": null,
                "closeTime": null,
                "description": null,
                "version": null,
                "ccCodeMkt": null,
                "ccCodePart": "45039642",
                "ccCodeOper": "15020062",
                "operFlag": 1,
                "statusDate": "2024-05-31 10:34:22",
                "ccProvince": "陕西",
                "ccCity": "西安",
                "ccCounty": "新城区",
                "ccTown": null,
                "ccAddr": "西新街28号",
                "isIphone": 1,
                "latnId": 290,
                "zoneId": 20191802,
                "ccNumberOper": "290000263",
                "jtUniNumber": null,
                "lordNumber": null,
                "ifJt": 6,
                "orgId": 29001,
                "channelClass": "10",
                "channelThirdType": "110101",
                "action": "update",
                "channelCreateTime": 1400122419000,
                "ecsCode": null,
                "cbsCode": null,
                "longitude": null,
                "latitude": null,
                "locX": 108.95509753507,
                "locY": 34.27028385133,
                "gpsLocX": null,
                "gpsLocY": null,
                "chnTypeCd": "110102",
                "fiveGridId": 20758027,
                "latnName": "西安市",
                "commonRegionTownId": null,
                "channelSubtypeCds": null,
                "channelTypeCds": null,
                "specializedTeamsId": 100080,
                "specializedTeamsName": "自营厅",
                "specializedTeamsNames": null,
                "loginStaffId": null,
                "loginSysUserCode": null,
                "sysRoleIds": null,
                "saleRelId": null,
                "isGeneral": null,
                "currentPage": -1
              },
              {
                "pageSize": 10,
                "pageNo": -1,
                "dataTotal": null,
                "countFlag": true,
                "channelId": 45039642,
                "channelName": "西安市区西华门营业厅",
                "channelLevelCd": null,
                "channelTypeCd": "110000",
                "channelSubtypeCd": "110100",
                "statusCd": "1000",
                "parentChnId": null,
                "channelNbr": "6101021020662",
                "commonRegionId": 290015,
                "provCode": "15020062",
                "channelSpecId": null,
                "capacity": null,
                "startDt": null,
                "endDt": null,
                "openTime": null,
                "closeTime": null,
                "description": null,
                "version": null,
                "ccCodeMkt": null,
                "ccCodePart": "45039642",
                "ccCodeOper": "15020062",
                "operFlag": 1,
                "statusDate": "2024-05-31 10:34:22",
                "ccProvince": "陕西",
                "ccCity": "西安",
                "ccCounty": "新城区",
                "ccTown": null,
                "ccAddr": "西新街28号",
                "isIphone": 1,
                "latnId": 290,
                "zoneId": 20191802,
                "ccNumberOper": "290000263",
                "jtUniNumber": null,
                "lordNumber": null,
                "ifJt": 6,
                "orgId": 29001,
                "channelClass": "10",
                "channelThirdType": "110101",
                "action": "update",
                "channelCreateTime": 1400122419000,
                "ecsCode": null,
                "cbsCode": null,
                "longitude": null,
                "latitude": null,
                "locX": 108.95509753507,
                "locY": 34.27028385133,
                "gpsLocX": null,
                "gpsLocY": null,
                "chnTypeCd": "110102",
                "fiveGridId": 20758027,
                "latnName": "西安市",
                "commonRegionTownId": null,
                "channelSubtypeCds": null,
                "channelTypeCds": null,
                "specializedTeamsId": 100080,
                "specializedTeamsName": "自营厅",
                "specializedTeamsNames": null,
                "loginStaffId": null,
                "loginSysUserCode": null,
                "sysRoleIds": null,
                "saleRelId": null,
                "isGeneral": null,
                "currentPage": -1
              },
              {
                "pageSize": 10,
                "pageNo": -1,
                "dataTotal": null,
                "countFlag": true,
                "channelId": 45039642,
                "channelName": "西安市区西华门营业厅",
                "channelLevelCd": null,
                "channelTypeCd": "110000",
                "channelSubtypeCd": "110100",
                "statusCd": "1000",
                "parentChnId": null,
                "channelNbr": "6101021020662",
                "commonRegionId": 290015,
                "provCode": "15020062",
                "channelSpecId": null,
                "capacity": null,
                "startDt": null,
                "endDt": null,
                "openTime": null,
                "closeTime": null,
                "description": null,
                "version": null,
                "ccCodeMkt": null,
                "ccCodePart": "45039642",
                "ccCodeOper": "15020062",
                "operFlag": 1,
                "statusDate": "2024-05-31 10:34:22",
                "ccProvince": "陕西",
                "ccCity": "西安",
                "ccCounty": "新城区",
                "ccTown": null,
                "ccAddr": "西新街28号",
                "isIphone": 1,
                "latnId": 290,
                "zoneId": 20191802,
                "ccNumberOper": "290000263",
                "jtUniNumber": null,
                "lordNumber": null,
                "ifJt": 6,
                "orgId": 29001,
                "channelClass": "10",
                "channelThirdType": "110101",
                "action": "update",
                "channelCreateTime": 1400122419000,
                "ecsCode": null,
                "cbsCode": null,
                "longitude": null,
                "latitude": null,
                "locX": 108.95509753507,
                "locY": 34.27028385133,
                "gpsLocX": null,
                "gpsLocY": null,
                "chnTypeCd": "110102",
                "fiveGridId": 20758027,
                "latnName": "西安市",
                "commonRegionTownId": null,
                "channelSubtypeCds": null,
                "channelTypeCds": null,
                "specializedTeamsId": 100080,
                "specializedTeamsName": "自营厅",
                "specializedTeamsNames": null,
                "loginStaffId": null,
                "loginSysUserCode": null,
                "sysRoleIds": null,
                "saleRelId": null,
                "isGeneral": null,
                "currentPage": -1
              },
              {
                "pageSize": 10,
                "pageNo": -1,
                "dataTotal": null,
                "countFlag": true,
                "channelId": 45039642,
                "channelName": "西安市区西华门营业厅",
                "channelLevelCd": null,
                "channelTypeCd": "110000",
                "channelSubtypeCd": "110100",
                "statusCd": "1000",
                "parentChnId": null,
                "channelNbr": "6101021020662",
                "commonRegionId": 290015,
                "provCode": "15020062",
                "channelSpecId": null,
                "capacity": null,
                "startDt": null,
                "endDt": null,
                "openTime": null,
                "closeTime": null,
                "description": null,
                "version": null,
                "ccCodeMkt": null,
                "ccCodePart": "45039642",
                "ccCodeOper": "15020062",
                "operFlag": 1,
                "statusDate": "2024-05-31 10:34:22",
                "ccProvince": "陕西",
                "ccCity": "西安",
                "ccCounty": "新城区",
                "ccTown": null,
                "ccAddr": "西新街28号",
                "isIphone": 1,
                "latnId": 290,
                "zoneId": 20191802,
                "ccNumberOper": "290000263",
                "jtUniNumber": null,
                "lordNumber": null,
                "ifJt": 6,
                "orgId": 29001,
                "channelClass": "10",
                "channelThirdType": "110101",
                "action": "update",
                "channelCreateTime": 1400122419000,
                "ecsCode": null,
                "cbsCode": null,
                "longitude": null,
                "latitude": null,
                "locX": 108.95509753507,
                "locY": 34.27028385133,
                "gpsLocX": null,
                "gpsLocY": null,
                "chnTypeCd": "110102",
                "fiveGridId": 20758027,
                "latnName": "西安市",
                "commonRegionTownId": null,
                "channelSubtypeCds": null,
                "channelTypeCds": null,
                "specializedTeamsId": 100080,
                "specializedTeamsName": "自营厅",
                "specializedTeamsNames": null,
                "loginStaffId": null,
                "loginSysUserCode": null,
                "sysRoleIds": null,
                "saleRelId": null,
                "isGeneral": null,
                "currentPage": -1
              },
              {
                "pageSize": 10,
                "pageNo": -1,
                "dataTotal": null,
                "countFlag": true,
                "channelId": 45039642,
                "channelName": "西安市区西华门营业厅",
                "channelLevelCd": null,
                "channelTypeCd": "110000",
                "channelSubtypeCd": "110100",
                "statusCd": "1000",
                "parentChnId": null,
                "channelNbr": "6101021020662",
                "commonRegionId": 290015,
                "provCode": "15020062",
                "channelSpecId": null,
                "capacity": null,
                "startDt": null,
                "endDt": null,
                "openTime": null,
                "closeTime": null,
                "description": null,
                "version": null,
                "ccCodeMkt": null,
                "ccCodePart": "45039642",
                "ccCodeOper": "15020062",
                "operFlag": 1,
                "statusDate": "2024-05-31 10:34:22",
                "ccProvince": "陕西",
                "ccCity": "西安",
                "ccCounty": "新城区",
                "ccTown": null,
                "ccAddr": "西新街28号",
                "isIphone": 1,
                "latnId": 290,
                "zoneId": 20191802,
                "ccNumberOper": "290000263",
                "jtUniNumber": null,
                "lordNumber": null,
                "ifJt": 6,
                "orgId": 29001,
                "channelClass": "10",
                "channelThirdType": "110101",
                "action": "update",
                "channelCreateTime": 1400122419000,
                "ecsCode": null,
                "cbsCode": null,
                "longitude": null,
                "latitude": null,
                "locX": 108.95509753507,
                "locY": 34.27028385133,
                "gpsLocX": null,
                "gpsLocY": null,
                "chnTypeCd": "110102",
                "fiveGridId": 20758027,
                "latnName": "西安市",
                "commonRegionTownId": null,
                "channelSubtypeCds": null,
                "channelTypeCds": null,
                "specializedTeamsId": 100080,
                "specializedTeamsName": "自营厅",
                "specializedTeamsNames": null,
                "loginStaffId": null,
                "loginSysUserCode": null,
                "sysRoleIds": null,
                "saleRelId": null,
                "isGeneral": null,
                "currentPage": -1
              }
            ]
            """;
}
