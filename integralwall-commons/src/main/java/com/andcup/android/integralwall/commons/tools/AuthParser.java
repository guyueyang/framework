package com.andcup.android.integralwall.commons.tools;

import com.andcup.android.app.integralwall.datalayer.model.WeChatEntry;
import com.andcup.android.app.integralwall.datalayer.model.WeiboEntry;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Amos on 2015/9/18.
 */
public class AuthParser {

    public static WeiboEntry parseFromTwitter(Map<String, Object> map){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonfromMap = null;
            jsonfromMap = mapper.writeValueAsString(map);
            System.out.println(jsonfromMap);
            WeiboEntry entry =  mapper.readValue(jsonfromMap, WeiboEntry.class);
            return entry;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static WeChatEntry parseFromWeChat(Map<String, Object> map){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonfromMap = null;
            jsonfromMap = mapper.writeValueAsString(map);
            System.out.println(jsonfromMap);
            WeChatEntry entry =  mapper.readValue(jsonfromMap, WeChatEntry.class);
            return entry;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
