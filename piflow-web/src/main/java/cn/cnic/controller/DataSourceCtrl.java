package cn.cnic.controller;

import cn.cnic.base.util.LoggerUtil;
import cn.cnic.base.util.SessionUserUtil;
import cn.cnic.component.dataSource.service.IDataSource;
import cn.cnic.component.dataSource.vo.DataSourceVo;
import cn.cnic.component.flow.service.IStopsService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/datasource")
public class DataSourceCtrl {
    /**
     * Introduce the log, note that all are under the "org.slf4j" package
     */
    Logger logger = LoggerUtil.getLogger();

    @Resource
    private IDataSource dataSourceImpl;

    @Resource
    private IStopsService stopsServiceImpl;

    @RequestMapping("/getDatasourceList")
    @ResponseBody
    public String getDatasourceList() {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return dataSourceImpl.getDataSourceVoList(currentUsername, isAdmin);
    }

    @RequestMapping("/getDataSourceListPagination")
    @ResponseBody
    public String getDataSourceListPagination(Integer page, Integer limit, String param) {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return dataSourceImpl.getDataSourceVoListPage(currentUsername, isAdmin, page, limit, param);
    }

    @RequestMapping("/getDatasourceById")
    @ResponseBody
    public String getDatasourceById(String id) {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return dataSourceImpl.getDataSourceVoById(currentUsername, isAdmin, id);
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public String saveOrUpdate(DataSourceVo dataSourceVo) {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return dataSourceImpl.saveOrUpdate(currentUsername, isAdmin, dataSourceVo);
    }

    @RequestMapping("/getDataSourceInputData")
    @ResponseBody
    public String getDataSourceInputPageData(String dataSourceId) {
        String username = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return dataSourceImpl.getDataSourceInputPageData(username, isAdmin, dataSourceId);
    }

    @RequestMapping("/deleteDataSource")
    @ResponseBody
    public String deleteDataSource(String dataSourceId) {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        boolean isAdmin = SessionUserUtil.isAdmin();
        return dataSourceImpl.deleteDataSourceById(currentUsername, isAdmin, dataSourceId);
    }

    @RequestMapping("/fillDatasource")
    @ResponseBody
    public String fillDatasource(String dataSourceId, String stopId) {
        String username = SessionUserUtil.getCurrentUsername();
        return stopsServiceImpl.fillDatasource(username, dataSourceId, stopId);
    }


}
