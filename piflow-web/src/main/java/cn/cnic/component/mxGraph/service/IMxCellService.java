package cn.cnic.component.mxGraph.service;

import cn.cnic.component.mxGraph.model.MxCell;

public interface IMxCellService {

    public int deleteMxCellById(String id);

    public MxCell getMeCellById(String id);


}