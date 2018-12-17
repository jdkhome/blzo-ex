package com.jdkhome.blzo.ex.ip2region.ip_tool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;

import java.io.*;

@Slf4j
public class IpRegionTools {
    private IpRegionTools() {
    }
    //日志

    private static class HolderClass {
        static MyDbSearcher searcher;

        public static void inputstreamtofile(InputStream ins, File file) throws Exception {
            OutputStream os = null;

            try {
                os = new FileOutputStream(file);


                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            } catch (FileNotFoundException e) {
                log.error("inputstreamtofile -> error:{}",e);
            } finally {

                if (null != os) {
                    os.close();
                }
                if (null != ins) {
                    ins.close();
                }
            }

        }

        static {
            try {
                DbConfig config = new DbConfig();

                InputStream inputStream = IpRegionTools.class.getResourceAsStream("/data/ip2region.db");
                //String dbfile = IpTools.class.getClassLoader().getResource("ip2region.db").getFile();
                File file = File.createTempFile("ipdb", ".tmp");

                inputstreamtofile(inputStream, file);

                searcher = new MyDbSearcher(config, file);
            } catch (Exception e) {
                log.error("单例初始化 -> e:", e);
            }
        }
    }

    /**
     * 更具ip获取城市名
     * 拿不到城市就返回国家
     *
     * @param ip
     * @return
     */
    public static String getCity(String ip) {

        //log.info("ip -> {}", ip);


        if (StringUtils.isEmpty(ip)) {
            return "未知";
        }

        try {
            // B树搜索（更快）
            DataBlock block2 = HolderClass.searcher.btreeSearch(ip);
            String[] result = block2.getRegion().split("\\|");

            if (result.length == 5) {
                if (StringUtils.isNotEmpty(result[3]) && !result[3].equals("0")) {
                    return result[3];
                } else if (StringUtils.isNotEmpty(result[0]) && !result[0].equals("0")) {
                    return result[0];
                }
            }

        } catch (Exception e) {
            log.error("解析ip异常 -> e:", e);
        }
        return "未知";
    }

}