package com.sendyago.util.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.sendyago.util.jdbc.OracleJDBC;

public class PartBase {
/*public static int GetPart (String partId) {

    int partSecond=1;    
    if(partId.equals("1")){
        partSecond=10;
    }
    else if(partId.equals("2")){
        partSecond=5;
    }
    else if(partId.equals("3")){
        partSecond=5;
    }
    else if(partId.equals("4")){
        partSecond=5;
    }
  
    return partSecond;
 }
*/
    public static int GetPart (String partId) {
        int partSecond=1;
        OracleJDBC oracleJDBC=new OracleJDBC();
        
        LinkedHashMap<String, Object> map=new LinkedHashMap<String, Object>();
        map.put("query_all", 1);
        map.put("in_page", 1);
        map.put("in_limit", 1);       
        List<HashMap<String, Object>> list= oracleJDBC.procedures4Query(map,"COUNT_SENSOR_PART_LIST");
       for(int i=0;i<list.size();i++){ 
          if(list.get(i).get("PART_ID").toString().equals(partId) ){          
             partSecond= Integer.parseInt(list.get(i).get("PART_SECOND").toString());
         
          }
       }
       return partSecond;

     }


}
