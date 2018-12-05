package com.nature.third.impl;

import com.nature.base.util.DateUtils;
import com.nature.base.util.HttpUtils;
import com.nature.base.util.LoggerUtil;
import com.nature.common.Eunm.FlowState;
import com.nature.common.constant.SysParamsCache;
import com.nature.component.workFlow.model.Flow;
import com.nature.component.workFlow.model.FlowInfoDb;
import com.nature.mapper.FlowInfoDbMapper;
import com.nature.third.inf.IGetFlowInfo;
import com.nature.third.inf.IGetFlowProgress;
import com.nature.third.vo.flow.ThirdProgressVo;
import com.nature.third.vo.flowInfo.ThirdFlowInfoStopsVo;
import com.nature.third.vo.flowInfo.ThirdFlowInfoVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class GetFlowInfoImpl implements IGetFlowInfo {

    Logger logger = LoggerUtil.getLogger();

    @Resource
    private FlowInfoDbMapper flowInfoDbMapper;

    @Autowired
    private IGetFlowProgress iGetFlowProgress;


    /**
     * 发送 post请求
     */
    @SuppressWarnings("rawtypes")
    @Override
    public ThirdFlowInfoVo getFlowInfo(String appid) {
        ThirdFlowInfoVo jb = null;
        Map<String, String> map = new HashMap<String, String>();
        map.put("appID", appid);
        String doGet = HttpUtils.doGet(SysParamsCache.FLOW_INFO_URL(), map);
        if (StringUtils.isNotBlank(doGet) && !doGet.contains("Exception")) {
            // 同样先将json字符串转换为json对象，再将json对象转换为java对象，如下所示。
            JSONObject obj = JSONObject.fromObject(doGet).getJSONObject("flow");// 将json字符串转换为json对象
            // 当FlowInfo中有List时需要
            Map<String, Class> classMap = new HashMap<String, Class>();
            // key为FlowInfo中的List的name,value为list的泛型的class
            classMap.put("stops", ThirdFlowInfoStopsVo.class);
            // 将json对象转换为java对象
            jb = (ThirdFlowInfoVo) JSONObject.toBean(obj, ThirdFlowInfoVo.class, classMap);
        }
        return jb;
    }

    /**
     * 通过appId调接口返回flowInfo并保存数据库
     */
    @Override
    public FlowInfoDb AddFlowInfo(String appId, Flow flow) {
        ThirdFlowInfoVo startFlow2 = getFlowInfo(appId);
        ThirdProgressVo progress = iGetFlowProgress.getFlowProgress(appId);
        logger.info("测试返回信息：" + startFlow2);
        if (null != startFlow2 && null != startFlow2.getId()) {
            FlowInfoDb flowInfoDb = flowInfoDbMapper.flowInfoDb(startFlow2.getId());
            //如果数据库不为空则更新接口返回来的数据
            if (null != flowInfoDb) {
                FlowInfoDb up = new FlowInfoDb();
                up.setId(flowInfoDb.getId());
                up.setName(startFlow2.getName());
                up.setState(startFlow2.getState());
                up.setEndTime(DateUtils.strCstToDate(startFlow2.getEndTime()));
                up.setStartTime(DateUtils.strCstToDate(startFlow2.getStartTime()));
                if (null == progress.getProgress() || "NaN".equals(progress.getProgress())) {
                    up.setProgress("0");
                } else {
                    up.setProgress(progress.getProgress());
                }
                up.setLastUpdateUser("-1");
                up.setLastUpdateDttm(new Date());
                int updateFlowInfo = flowInfoDbMapper.updateFlowInfo(up);
                if (updateFlowInfo > 0) {
                    return up;
                }
            } else {
                //接口返回不为空的话,数据库保存
                FlowInfoDb add = new FlowInfoDb();
                add.setId(startFlow2.getId());
                add.setName(startFlow2.getName());
                add.setState(progress.getState());
                add.setEndTime(DateUtils.strCstToDate(startFlow2.getEndTime()));
                add.setStartTime(DateUtils.strCstToDate(startFlow2.getStartTime()));
                add.setProgress(progress.getProgress());
                add.setCrtDttm(new Date());
                add.setCrtUser("wdd");
                add.setVersion(0L);
                add.setEnableFlag(true);
                add.setLastUpdateUser("-1");
                add.setLastUpdateDttm(new Date());
                add.setFlow(flow);
                int addFlowInfo = flowInfoDbMapper.addFlowInfo(add);
                if (addFlowInfo > 0) {
                    return add;
                }
            }
        }
        FlowInfoDb kong = new FlowInfoDb();
        //flowInfo接口返回为空的情况
        kong.setId(appId);
        kong.setCrtDttm(new Date());
        kong.setCrtUser("wdd");
        kong.setVersion(0L);
        kong.setEnableFlag(true);
        kong.setLastUpdateUser("-1");
        kong.setProgress("0");
        kong.setState(FlowState.STARTED.toString());
        kong.setLastUpdateDttm(new Date());
        kong.setFlow(flow);
        flowInfoDbMapper.addFlowInfo(kong);
        return kong;
    }

}
