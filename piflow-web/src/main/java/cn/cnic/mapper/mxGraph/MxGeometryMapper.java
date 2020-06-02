package cn.cnic.mapper.mxGraph;

import cn.cnic.component.mxGraph.model.MxGeometry;
import cn.cnic.provider.mxGraph.MxGeometryMapperProvider;
import org.apache.ibatis.annotations.*;

@Mapper
public interface MxGeometryMapper {

    /**
     * add MxGeometry
     *
     * @param mxGeometry
     * @return
     */
    @InsertProvider(type = MxGeometryMapperProvider.class, method = "addMxGeometry")
    public int addMxGeometry(MxGeometry mxGeometry);

    /**
     * update MxGeometry
     *
     * @param mxGeometry
     * @return
     */
    @UpdateProvider(type = MxGeometryMapperProvider.class, method = "updateMxGeometry")
    public int updateMxGeometry(MxGeometry mxGeometry);

    /**
     * Query MxGeometry based on id
     *
     * @param id
     * @return
     */
    @SelectProvider(type = MxGeometryMapperProvider.class, method = "getMxGeometryById")
    @Results({
            @Result(column = "mx_relative", property = "relative"),
            @Result(column = "mx_as", property = "as"),
            @Result(column = "mx_x", property = "x"),
            @Result(column = "mx_y", property = "y"),
            @Result(column = "mx_width", property = "width"),
            @Result(column = "mx_height", property = "height")
    })
    public MxGeometry getMxGeometryById(String id);

    /**
     * Query MxGeometry based on flowId
     *
     * @param flowId
     * @return
     */
    @SelectProvider(type = MxGeometryMapperProvider.class, method = "getMxGeometryByFlowId")
    @Results({
            @Result(column = "mx_relative", property = "relative"),
            @Result(column = "mx_as", property = "as"),
            @Result(column = "mx_x", property = "x"),
            @Result(column = "mx_y", property = "y"),
            @Result(column = "mx_width", property = "width"),
            @Result(column = "mx_height", property = "height")
    })
    public MxGeometry getMxGeometryByFlowId(String flowId);


    @UpdateProvider(type = MxGeometryMapperProvider.class, method = "updateEnableFlagById")
    public int updateEnableFlagById(String id);

}