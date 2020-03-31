package com.jdkhome.blzo.ex.utils.coder;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
@Component
public class FastjsonEncoder implements Encoder {
    private SerializeConfig config = null;

    public FastjsonEncoder() {
        this(null);
    }

    public FastjsonEncoder(SerializeConfig config) {
        if (null != config) {
            this.config = config;
        } else {
            this.config = SerializeConfig.getGlobalInstance();
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see feign.codec.Encoder#encode(java.lang.Object, java.lang.reflect.Type, feign.RequestTemplate)
     */
    @Override
    public void encode(Object obj, Type type, RequestTemplate template) throws EncodeException {
        template.body(JSON.toJSONString(obj,config));
    }

}