<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java" import="java.util.*,com.sendyago.count.controller.SystemCountService"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
    content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<title>健康监测系统</title>
<%@ include file="/pages/common/common_query.jsp"%>

<script language="Javascript" src="<%=path%>/highcharts/highstock.js"></script>
<script language="Javascript" src="<%=path%>/highcharts/style-grid-light.js"></script>

</head>
<% 
String menu_id = request.getParameter("shiro_menu_id");
String role_id = request.getParameter("shiro_role_id");
%>

<body>
    
    <!-- content -->

            <div class="page-header">
                <ol class="breadcrumb">
                    <li><span class="glyphicon glyphicon-home" aria-hidden="true"></span>
                        首页</li>
                    <li>查询与统计</li>
                    <li>统计值查询</li>
                </ol>
            </div>
            <div class="container-fluid">
                <div class="row">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <form action="" name="form1" id="form1" method="post" class="form-horizontal" >
                                <input id="divFlag" name="divFlag" type="hidden" value="${divFlag}">                      
                                <input type="hidden" id="limit" name="limit" value="10" />
                                <input type="hidden" id="path" name="path" value="<%=path%>" />
                                <input type="hidden" id="mapSensor" name="mapSensor" value="${mapSensor}" />                      
                                <input type="hidden" id="dataList" name="dataList" value="${dataList}" />      
                                <input type="hidden" id="sensor_name" name="sensor_name" value="${sensor_name}" />
                                 <input type="hidden" id="menu_id" name="menu_id" value="${param.shiro_menu_id}" />
                                 <input type="hidden" id="role_id" name="role_id" value="${param.shiro_role_id}" />
                                <input type="hidden" id="user_id" name="user_id" value="${param.shiro_user_id}" />
                                <fieldset>
                                    <legend>数据统计查询</legend>
                                    <div class="form-group">
                                <label for="normal-field" class="col-sm-3 control-label">监测项目</label>
                                <div class="col-sm-2">
                                    <select id="checkType" name="checkType" class="form-control"
                                        onchange="getPart()">


                                    </select>

                                </div>
                                <div class="col-sm-2">
                                    <select class="form-control" id="partType" name="partType"
                                        onchange="getSection()">
                                      <option  disabled="disabled" >种类名称</option>
                                        </select>
                                        <input type="hidden" id="partUnit" value="" />
                                </div>
                                <div class="col-sm-2">
                                    <select class="form-control" id="sectionList"
                                        name="sectionList" onchange="getSectionSensor('1')">
                                             <option value="" disabled >截面</option> 
                                        </select>
                                </div>
                                <div class="col-sm-2">
                                    <input type="hidden" id="s_sensor" name="s_sensor" value=""
                                        class="form-control" /> <input type="text" id="s_sensor_name" name="s_sensor_name" value=""   placeholder="传感器编号" class="form-control" onClick="getSectionSensor('1')" />

                                    <div id="wrap">
                                        <!-- <section id="ajax">
                                            <a id="id1" href="" class="framer" data-framer-type="ajax">
                                            </a>
                                        </section> -->
                                          <section id="iframe">
                                                     <a id="id1" href="" class="framer" data-framer-width="700" data-framer-height="440" data-framer-type="iframe"></a>
                                                </section>
                                    </div>

                                </div>
                            </div>
                            
                              <div class="form-group">
                                        <label for="hint-field" class="col-sm-3 control-label">
                                                                                                            数据范围 </label>
                                        <div class="col-sm-6">
                                            <div class="input-group">
                                                <span class="input-group-addon">从</span> <input type="text"   name="b_data" id="b_data"   class="form-control"> 
                                                <span class="input-group-addon">到</span> <input type="text"   name="e_data" id="e_data" class="form-control"> 
                            
                                            </div>
                                        </div>
                                    </div>
                                    
                                      
                                    <div class="form-group">
                                    
                                     <label for="hint-field" class="col-sm-3 control-label">统计类型</label>
                                        <div class="col-sm-1">        
                                         <input name="countType" type="radio" value="1" />平均值
                                        </div>
                                         <div class="col-sm-1">                             
                                         <input name="countType" type="radio" value="2" />最大值 
                                        </div>
                                        <div class="col-sm-1">
                                         <input name="countType" type="radio" value="3" />最小值
                                        </div>
     
                                         <div class="col-sm-3" >
                                            <select class="form-control"   id="sdw" name="sdw"  onchange="getSelect()">
                                             <option value=""  >查询周期</option> 
                                            <option value="1">日</option>
                                            <option value="2">周</option>
                                            <option value="3">月</option>
                                            <option value="4">年</option>
                                            </select>
                                    
                                    </div>
                                    </div>
                            <div class="form-group" id="bh1" style="display: none;">
                                                                      
                                        <label for="hint-field" class="col-sm-3 control-label"> 日期范围</label>
                                        <div class="col-sm-5">
                                            <div class="input-group">
                                               <!--   <span class=""></span> <input type="text"   name="sdw1" id="sdw1" placeholder=日期 onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" readonly="readonly"  class="form-control"> -->
                                                <span class="input-group-addon">从</span> <input type="text"   name="sdw2" id="sdw2" placeholder="" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})" readonly="readonly"  class="form-control"> 
                                                <span class="input-group-addon">到</span> <input type="text"   name="sdw3" id="sdw3" placeholder="" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})"  readonly="readonly"  class="form-control">                          
                                            </div>
                                        </div>
                                      
                                    </div> 
                                <div class="form-group" id="bh2" style="display: none;">                                                                     
                                 <label for="hint-field" class="col-sm-3 control-label"> 日期</label>
                                     <div class="col-sm-5">
                                    <div class="input-group">
                                  <span class=""></span> <input type="text"   name="sdw4" id="sdw4" placeholder=日期 onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM',alwaysUseStartDate:true})" readonly="readonly"  class="form-control"> 
                                  <span class="input-group-addon">从</span> <select  class="form-control" name="sdw5" id="sdw5">
                                    <option value="1">第一周</option>
                                    <option value="2">第二周</option>
                                    <option value="3">第三周</option>
                                    <option value="4">第四周</option>
                                    </select>
                               <span class="input-group-addon">到</span> <select class="form-control" name="sdw6" id="sdw6">
                                    <option value="1">第一周</option>
                                    <option value="2">第二周</option>
                                    <option value="3">第三周</option>
                                    <option value="4">第四周</option>
                                </select>
                            </div>
                        </div>
                        </div>

                        
                           <div class="form-group" id="bh3" style="display: none;">
                            <label for="hint-field" class="col-sm-3 control-label"> 时间范围：从 </label>
                              <div class="col-sm-4">
                                       <div class="input-group">
                                                <span class="input-group-addon">从</span> <input type="text"   name="sdw7" id="sdw7" placeholder="开始时间" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM',alwaysUseStartDate:true})" readonly="readonly"  class="form-control"> 
                                                <span class="input-group-addon">到</span> <input type="text"   name="sdw8" id="sdw8" placeholder="结束时间" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM',alwaysUseStartDate:true})" class="form-control">
                            
                                            </div>
                            </div>
                        </div>
                        
                        
                        
                           <div class="form-group" id="bh4" style="display: none;">
                            <label for="hint-field" class="col-sm-3 control-label"> 时间范围 </label>
                              <div class="col-sm-4">
                                       <div class="input-group">
                                                <span class="input-group-addon">从</span> <input type="text"   name="sdw9" id="sdw9" placeholder="开始时间" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy',alwaysUseStartDate:true})" readonly="readonly"  class="form-control"> 
                                                <span class="input-group-addon">到</span> <input type="text"   name="sdw10" id="sdw10" placeholder="结束时间" onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy',alwaysUseStartDate:true})" class="form-control">
                            
                                            </div>
                            </div>
                        </div>
                        
                    
                                </fieldset>
                                <div class="form-actions">
                                    <div class="row">
                                      <div  align="right" class="col-sm-offset-6 col-sm-5">
                                             <button type="button" class="btn bg-success"
                                                onclick="serachShowDiv('div1','<%=String.valueOf(SystemCountService.OperationButton(role_id,menu_id,"4"))%>')"><span class="glyphicon glyphicon-list"></span>&nbsp;列表查询 </button>
                                            <button type="button" class="btn bg-teal"
                                                onclick="serachShowDiv('div2','<%=String.valueOf(SystemCountService.OperationButton(role_id,menu_id,"5"))%>')"><span class="glyphicon glyphicon-stats"></span>&nbsp;曲线查询</button>
                                            <button type="button" class="btn bg-warning"  
                                            onclick="exportExcel('2','<%=String.valueOf(SystemCountService.OperationButton(role_id,menu_id,"6"))%>')"><span class="glyphicon glyphicon-share"></span>&nbsp;导出</button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
<!-- 
              <div  id="div1" class="panel">
                        <div  class="panel-body" >
                            <table class="table table-striped table-hover table-bordered table-condensed" id="tb">
                                
                                <thead>
                                    <tr>
                                        <th>时间</th>
                                        <th>数值</th>
                                        
                                    </tr>
                                </thead>
                                <tbody>                   
         
                                </tbody>
                            </table>
                           <div class="text-center">
                                <button class="btn btn-default btn-sm"   onclick="serachShowPage()">更多……</button>
                            </div>
                        </div>
                    </div>

                   <div id="div2" class="panel" >                       
                        <div id="pline" class="panel-body" style="width:95%"></div>
                    </div> -->
                    
                      <div id="div1">
                                                    
                          <div class="ifr_div">
                            <div class="proccess-count"  id="loading"  style="display: none;"><b>正在加载中...请稍候</b></div> 
                          </div>
                         
                          <div  class="panel panel-body">                           
                           <table class="table table-striped table-hover table-bordered table-condensed" id="tb">                              
                                <thead>
                                    <tr>
                                        <th>时间</th>
                                        <th>数值</th>                                      
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                </tbody>
                            </table>
                            
                        <!--     <div class="text-center panel-footer">                          
                                <button class="btn btn-default btn-sm"   onclick="serachShowPage()">加载更多</button>                              
                            </div>  -->                           
                      
                    </div>                    
                    </div>  
                                
                     <div id="div2"  >  
                 <div class="ifr_div">
                            <div class="proccess-count"  id="loading"  style="display: none;"><b>正在加载中...请稍候</b></div> 
                          </div> 
                     <div  class="panel panel-body">                                                                 
                     <div id="pline" class="panel-body" style="width:95%"></div>
                    </div>
                    </div>
         
                </div>
            </div>

</body>
<script type="text/javascript" src="<%=path%>/pages/count/js/queryCount.js" ></script>
</html>
