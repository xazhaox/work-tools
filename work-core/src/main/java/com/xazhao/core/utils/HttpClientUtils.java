package com.xazhao.core.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.xazhao.core.constant.Charsets;
import com.xazhao.core.exception.ServiceErrorCode;
import com.xazhao.core.exception.ServiceException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContexts;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @Description Created on 2024/11/07.
 * @Author Zhao.An
 */

@Slf4j
public class HttpClientUtils {

    private static final String[] SUFFIX_ARR = new String[]{"zip", "rar", "7z", "xlsx", "xls", "docx", "doc", "ppt", "pptx", "pdf"};

    private static final String APPLICATION_JSON_CHARSET = "application/json; charset=utf-8";

    private static final String APPLICATION_JSON = "application/json";

    /**
     * 获取HttpClient客户端
     *
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient httpClientBuilder() {
        // 获取HttpClient
        return HttpClientBuilder.create().build();
    }

    /**
     * 获取自定义HttpClient客户端，忽略掉对服务器端证书的校验
     *
     * @return CloseableHttpClient
     */
    public static CloseableHttpClient httpClientCustom() {
        try {
            // 获取HttpClient
            return HttpClients.custom()
                    .setSSLSocketFactory(new SSLConnectionSocketFactory(SSLContexts.custom()
                            // 忽略掉对服务器端证书的校验
                            .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                            .build(), NoopHostnameVerifier.INSTANCE))
                    .build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            log.error("获取HttpClient失败");
            // 异常处理
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 发送POST请求
     *
     * @param requestBody 请求时需要携带的请求体
     * @return 请求响应体
     */
    public static Map<String, Object> httpPost(Map<String, Object> requestBody) throws IOException {

        // 获取HttpClient
        CloseableHttpClient httpClient = HttpClientUtils.httpClientBuilder();

        // 发送POST请求，入参为请求Url
        HttpPost httpPost = new HttpPost("url");

        // 请求时需要携带的请求头
        Header[] headers = {
                new BasicHeader("Content-Type", APPLICATION_JSON_CHARSET)
        };

        // 设置请求头
        httpPost.setHeaders(headers);

        // 将Map转为Json字符串，设置POST请求体
        String requestJson = JSONUtil.parse(requestBody).toJSONString(2);
        HttpEntity request = new StringEntity(requestJson, ContentType.create(APPLICATION_JSON, Charsets.UTF_8));
        httpPost.setEntity(request);

        try {

            // 发送请求
            CloseableHttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                Console.error("请求失败！");
            }

            // 获取响应结果，将响应的结果转为String字符串
            HttpEntity httpEntity = response.getEntity();
            String readerEntityStr = HttpClientUtils.httpEntityToString(httpEntity);

            // 把字节数组转换为字符串，Json格式化响应内容
            return JSONUtil.parseObj(JSONUtil.parseObj(readerEntityStr));

        } finally {

            // 关流
            httpClient.close();
        }
    }

    public static Map<String, Object> httpGet() throws IOException {

        return null;
    }

    /**
     * 解析HttpEntity内容
     *
     * @param httpEntity HttpEntity
     * @return 解析HttpEntity后的字符串内容
     * @throws IOException IOException
     */
    @SuppressWarnings(value = {"all"})
    public static String httpEntityToString(final HttpEntity httpEntity) throws IOException {

        if (null == httpEntity) throw new IllegalArgumentException("HTTP entity may not be null");

        // 获取响应内容
        InputStream inStream = httpEntity.getContent();
        if (null == inStream) return null;

        if (httpEntity.getContentLength() > Integer.MAX_VALUE)
            throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");

        // 创建字节数组输出流，用来输出读取到的内容
        try (ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inStream.read(buffer, 0, buffer.length)) != -1) {
                byteArrayOutput.write(buffer, 0, bytesRead);
            }

            // 响应内容，可转为Json类型
            return byteArrayOutput.toString();

        } finally {
            inStream.close();
        }
    }

    /**
     * 上传文件
     *
     * @param multipartFile 文件
     * @param fileSuffix    文件类型
     * @return File
     * @throws IOException IOException
     */
    private File uploadFile(MultipartFile multipartFile, String fileSuffix) throws IOException {
        String profileUrl = "work-tools/" + DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        String uploadTime = DateUtil.format(new Date(), DatePattern.PURE_TIME_PATTERN);
        String uploadFileName = uploadTime + "-" + IdUtil.fastSimpleUUID() + "." + fileSuffix;
        File reportsFile = createFile(profileUrl + File.separator + uploadFileName);
        multipartFile.transferTo(reportsFile);
        return reportsFile;
    }

    /**
     * 下载文件
     *
     * @param response 响应体
     * @param filePath 文件路径
     * @param fileName 文件名称
     */
    private static void downloadFile(HttpServletResponse response, File filePath, String fileName) {
        InputStream input = null;
        OutputStream output = null;
        try (FileInputStream fileInput = new FileInputStream(filePath)) {
            input = new BufferedInputStream(fileInput);
            setResponse(response, fileName);
            response.setHeader("Content-Length", String.valueOf(filePath.length()));
            output = new BufferedOutputStream(response.getOutputStream());
            byte[] buffer = new byte[1024 * 1024];
            int bytesRead;
            while ((bytesRead = input.read(buffer, 0, buffer.length)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            output.flush();
            input.close();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException(ServiceErrorCode.DOWNLOAD_FAILED);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (output != null) {
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 设置Header参数
     *
     * @param response 响应体
     * @param fileName 文件名称
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public static void setResponse(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.reset();
        response.setCharacterEncoding(Charsets.UTF_8);
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
    }


    /**
     * 创建文件或文件夹
     *
     * @param targetFile 文件或文件夹路径
     * @return File
     */
    private static File createFile(String targetFile) {
        File target = new File(targetFile);
        if (!target.getParentFile().exists()) {
            target.getParentFile().mkdirs();
        }
        return target;
    }

    /**
     * 判断文件是否合法
     *
     * @param suffix 文件后缀
     * @return Boolean
     */
    private static Boolean verifyFileSuffix(String suffix) {
        return Arrays.asList(SUFFIX_ARR).contains(suffix);
    }

}
