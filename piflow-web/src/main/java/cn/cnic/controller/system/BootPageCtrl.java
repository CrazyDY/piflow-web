package cn.cnic.controller.system;

import cn.cnic.base.util.ReturnMapUtils;
import cn.cnic.base.util.SessionUserUtil;
import cn.cnic.component.system.service.ISysInitRecordsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/bootPage")
public class BootPageCtrl {

    @Resource
    private ISysInitRecordsService sysInitRecordsServiceImpl;

    @RequestMapping("/isInBootPage")
    @ResponseBody
    public String isInBootPage() {
        boolean inBootPage = sysInitRecordsServiceImpl.isInBootPage();
        return ReturnMapUtils.setSucceededCustomParamRtnJsonStr("isIn", inBootPage);
    }

    @RequestMapping("/initComponents")
    @ResponseBody
    public String initComponents() {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        return sysInitRecordsServiceImpl.initComponents(currentUsername);
    }

    @RequestMapping("/threadMonitoring")
    @ResponseBody
    public String threadMonitoring() {
        String currentUsername = SessionUserUtil.getCurrentUsername();
        return sysInitRecordsServiceImpl.threadMonitoring(currentUsername);
    }


}
