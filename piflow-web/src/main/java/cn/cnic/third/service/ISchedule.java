package cn.cnic.third.service;

import cn.cnic.component.schedule.entity.Schedule;

public interface ISchedule {

    public String scheduleStart(Schedule schedule);

    public String scheduleStop(String scheduleId);

    public String scheduleInfo(String scheduleId);
}