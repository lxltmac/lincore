package com.lxl.lincore.common.aliyun.video.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxl.lincore.common.aliyun.video.domain.dto.UploadVideoInfo;
import com.lxl.common.helper.ServiceConfigHelper;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author linxilii
 * @Description: 获取视频点播地址
 * @Date: 2018/9/14 11:03
 */
@Service
public class SignatureService {

    //账号AK信息请填写(必选)
    private static String access_key_id;
    //账号AK信息请填写(必选)
    private static String access_key_secret;
    //STS临时授权方式访问时该参数为必选，使用主账号AK和RAM子账号AK不需要填写
    private static String security_token = "";

    //以下参数不需要修改
    private final static String VOD_DOMAIN = "http://vod.cn-shanghai.aliyuncs.com";
    private final static String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private final static String HTTP_METHOD = "GET";
    private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private final static String UTF_8 = "utf-8";
    private final static String Formats = "mp3,mp4";
    private final static Logger LOG = Logger.getLogger(SignatureService.class.getName());

    static {
        access_key_id = ServiceConfigHelper.getServiceConfigValue("aliyun.video.accessId");
        access_key_secret = ServiceConfigHelper.getServiceConfigValue("aliyun.video.accessKey");
    }

    public JSONObject getUploadVideoUrl(UploadVideoInfo uploadVideoInfo) throws IOException {
        //生成私有参数，不同API需要修改
        Map<String, String> privateParams = generatePrivateParamters(uploadVideoInfo);
        //生成公共参数，不需要修改
        Map<String, String> publicParams = generatePublicParamters();
        //生成OpenAPI地址，不需要修改
        String URL = generateOpenAPIURL(publicParams, privateParams);
        //发送HTTP GET 请求
        String response = httpGet(URL);
        JSONObject jsonObject = JSON.parseObject(response);

        return jsonObject;
    }

    /**
     * 生成视频点播OpenAPI私有参数
     * 不同API需要修改此方法中的参数
     *
     * @return
     */
    private static Map<String, String> generatePrivateParamters(UploadVideoInfo uploadVideoInfo) {
        // 接口私有参数列表, 不同API请替换相应参数
        Map<String, String> privateParams = new HashMap<>();
        // 视频ID
        privateParams.put("VideoId", uploadVideoInfo.getVideoId());
        // API名称
        privateParams.put("Action", uploadVideoInfo.getAction());
        //视频流格式
        privateParams.put("Formats", Formats);
        //播放地址过期时间
        privateParams.put("AuthTimeout","1800000000");
//        //视频流类型
//        privateParams.put("StreamType","video");
//        //视频流清晰度 FD（流畅）,LD（标清）,SD（高清）,HD（超清）,OD（原画）,2K（2K）,4K（4K）
//        privateParams.put("Definition","OD");
//        //返回数据类型 Single（每种清晰度和格式只返回一路最新转码完成的流）,Multiple（每种清晰度和格式返回所有转码完成的流）
//        privateParams.put("ResultType","Single");
//        //输出地址类型 oss（回源地址）cdn（加速地址）
//        privateParams.put("OutputType","oss");
        return privateParams;
    }

    /**
     * 生成视频点播OpenAPI公共参数
     * 不需要修改
     *
     * @return
     */
    private static Map<String, String> generatePublicParamters() {
        Map<String, String> publicParams = new HashMap<>();
        publicParams.put("Format", "JSON");
        publicParams.put("Version", "2017-03-21");
        publicParams.put("AccessKeyId", access_key_id);
        publicParams.put("SignatureMethod", "HMAC-SHA1");
        publicParams.put("Timestamp", generateTimestamp());
        publicParams.put("SignatureVersion", "1.0");
        publicParams.put("SignatureNonce", generateRandom());
        if (security_token != null && security_token.length() > 0) {
            publicParams.put("SecurityToken", security_token);
        }
        return publicParams;
    }

    /**
     * 生成OpenAPI地址
     * @param privateParams
     * @return
     * @throws Exception
     */
    private static String generateOpenAPIURL(Map<String, String> publicParams, Map<String, String> privateParams) {
        return generateURL(VOD_DOMAIN, HTTP_METHOD, publicParams, privateParams);
    }

    /**
     * @param domain        请求地址
     * @param httpMethod    HTTP请求方式GET，POST等
     * @param publicParams  公共参数
     * @param privateParams 接口的私有参数
     * @return 最后的url
     */
    private static String generateURL(String domain, String httpMethod, Map<String, String> publicParams, Map<String, String> privateParams) {
        List<String> allEncodeParams = getAllParams(publicParams, privateParams);
        String cqsString = getCQS(allEncodeParams);
        out("CanonicalizedQueryString = " + cqsString);
        String stringToSign = httpMethod + "&" + percentEncode("/") + "&" + percentEncode(cqsString);
        out("StringtoSign = " + stringToSign);
        String signature = hmacSHA1Signature(access_key_secret, stringToSign);
        out("Signature = " + signature);
        return domain + "?" + cqsString + "&" + percentEncode("Signature") + "=" + percentEncode(signature);
    }

    private static List<String> getAllParams(Map<String, String> publicParams, Map<String, String> privateParams) {
        List<String> encodeParams = new ArrayList<String>();
        if (publicParams != null) {
            for (String key : publicParams.keySet()) {
                String value = publicParams.get(key);
                //将参数和值都urlEncode一下。
                String encodeKey = percentEncode(key);
                String encodeVal = percentEncode(value);
                encodeParams.add(encodeKey + "=" + encodeVal);
            }
        }
        if (privateParams != null) {
            for (String key : privateParams.keySet()) {
                String value = privateParams.get(key);
                //将参数和值都urlEncode一下。
                String encodeKey = percentEncode(key);
                String encodeVal = percentEncode(value);
                encodeParams.add(encodeKey + "=" + encodeVal);
            }
        }
        return encodeParams;
    }

    /**
     * 参数urlEncode
     *
     * @param value
     * @return
     */
    private static String percentEncode(String value) {
        try {
            String urlEncodeOrignStr = URLEncoder.encode(value, "UTF-8");
            String plusReplaced = urlEncodeOrignStr.replace("+", "%20");
            String starReplaced = plusReplaced.replace("*", "%2A");
            String waveReplaced = starReplaced.replace("%7E", "~");
            return waveReplaced;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取CQS 的字符串
     *
     * @param allParams
     * @return
     */
    private static String getCQS(List<String> allParams) {
        ParamsComparator paramsComparator = new ParamsComparator();
        Collections.sort(allParams, paramsComparator);
        String cqString = "";
        for (int i = 0; i < allParams.size(); i++) {
            cqString += allParams.get(i);
            if (i != allParams.size() - 1) {
                cqString += "&";
            }
        }

        return cqString;
    }

    private static class ParamsComparator implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            return lhs.compareTo(rhs);
        }
    }

    private static String hmacSHA1Signature(String accessKeySecret, String stringtoSign) {
        try {
            String key = accessKeySecret + "&";
            try {
                SecretKeySpec signKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
                Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
                mac.init(signKey);
                byte[] rawHmac = mac.doFinal(stringtoSign.getBytes());
                //按照Base64 编码规则把上面的 HMAC 值编码成字符串，即得到签名值（Signature）
                return new String(new BASE64Encoder().encode(rawHmac));
            } catch (Exception e) {
                throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
            }
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 生成随机数
     *
     * @return
     */
    private static String generateRandom() {
        String signatureNonce = UUID.randomUUID().toString();
        return signatureNonce;
    }

    /**
     * 生成当前UTC时间戳
     *
     * @return
     */
    public static String generateTimestamp() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    private static String httpGet(String url) throws IOException {
        /*
         * Read and covert a inputStream to a String.
         * Referred this:
         * http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
         */
        out("URL = " +  url);
        @SuppressWarnings("resource")
        Scanner s = new Scanner(new URL(url).openStream(), UTF_8).useDelimiter("\\A");
        try {
            String resposne = s.hasNext() ? s.next() : "true";
            out("Response = " + resposne);
            return resposne;
        } finally {
            s.close();
        }
    }

    private static void out(String newLine) {
        LOG.log(Level.INFO, newLine);
    }
}
