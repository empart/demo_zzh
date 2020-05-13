package com.example.zzh.test;

import com.example.zzh.model.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/12
 */
public class TestHttp {

    private RestTemplate restTemplate;

    private String preUrl;

    private String qaPreUrl;

    @Parameters({"preUrl", "qaPreUrl"})
    @BeforeClass(groups = {"qaPost", "putAndDel", "local"})
    public void init(String preUrl, String qaPreUrl) {
        this.restTemplate = new RestTemplate();
        this.preUrl = preUrl;
        this.qaPreUrl = qaPreUrl;
    }

    /**
     * get请求分为getForObject和getForEntity，它们用法一致，只是返回值类型不同。
     * getForObject会根据指定的数据类型，把接口返回的结果封装到数据类中
     * getForEntity会返回ResponseEntity，泛型是指定的数据类型，通过.getBody()获取数据
     */
    @Test(groups = {"local"})
    public void getObject1() throws URISyntaxException {
        //第一种方式：参数直接拼在url后面
        String url = preUrl + "demo/one?id=5";
        Demo demo = restTemplate.getForObject(url, Demo.class);
        System.out.println(demo);

//        URI uri = new URI(url);
//        Demo demo1 = restTemplate.getForObject(uri, Demo.class);
//        System.out.println("uri test :" + demo1);

        Assert.assertNull(demo);
    }

    @Parameters({"id"})
    @Test(groups = {"local"})
    public void getObject2(Integer id) {
        //第二种方式：url中使用占位符
        String url = preUrl + "demo/one?id={?}";
        Demo demo = restTemplate.getForObject(url, Demo.class, id);
//        String url = preUrl + "demo/one?id={id}";
//        Map<String, String> map = new HashMap<>();
//        map.put("id","5");
//        Demo demo = restTemplate.getForObject(url, Demo.class, map);

        System.out.println(demo);
        Assert.assertNotNull(demo);
    }

    @Test(groups = {"local"})
    public void getObject3() {
        //第三种方式：传递一个参数的map
        //TODO 测试不传参数看占位符内容
        String url = preUrl + "demo/one/path/{id}/{name}";
        Map<String, String> map = new HashMap<>();
        map.put("id", "5");
        map.put("name", "aa");
        Demo demo = restTemplate.getForObject(url, Demo.class, map);

        System.out.println(demo);
        Assert.assertNotNull(demo);
    }

    @Test(groups = {"local"})
    public void getEntity() {
        String url = preUrl + "demo/one?id=5";
        ResponseEntity<Demo> entity = restTemplate.getForEntity(url, Demo.class);

        Demo demo = entity.getBody();
        System.out.println("响应结果：" + demo);
        Assert.assertNotNull(demo);
        Assert.assertEquals(entity.getStatusCodeValue(), 200);
        Assert.assertTrue(entity.getStatusCode().is2xxSuccessful());
    }


    /**
     * 测试接口返回复杂的数据类该如何接收
     */
    @Test(groups = {"qaGet"})
    public void testGet() {
        //请求挑战者列表
        StringBuilder builder = new StringBuilder();
        builder.append(qaPreUrl).append("homework/student/challengers?").append("recordId=").append(4);
        ParameterizedTypeReference<Result<ResultList<Challenger>>> reference =
                new ParameterizedTypeReference<Result<ResultList<Challenger>>>() {
                };
        ResponseEntity<Result<ResultList<Challenger>>> entity =
                restTemplate.exchange(builder.toString(), HttpMethod.GET, null, reference);
        System.out.println(entity);
        Result<ResultList<Challenger>> result = entity.getBody();
        System.out.println(result);
        System.out.println("第一个挑战者是：" + result.getData().getEntries().get(0).getChallengerName());
        Assert.assertTrue(result.isSuccess());
        Assert.assertTrue(result.getData().getEntries().size() > 0);
    }

    /**
     * post请求也分为postForObject和postForEntity
     */
    @Parameters({"recordId", "challengerId", "pkScore"})
    @Test(groups = {"qaPost"})
    public void postObject(Integer recordId, Integer challengerId, Integer pkScore) {
        //保存挑战结果
        String url = qaPreUrl + "homework/student/challenge";
        Map<String, Object> map = new HashMap<>();
        map.put("recordId", recordId);
        map.put("challengerId", challengerId);
        map.put("pkScore", pkScore);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(map);
        Result result = restTemplate.postForObject(url, httpEntity, Result.class);
        System.out.println(result);
//        if(!result.isSuccess()){
//            System.out.println("错误信息"+result.getError().getMessage());
//        }
        Assert.assertTrue(result.isSuccess());
    }

    @DataProvider(name = "forPostEntity")
    public Object[][] forPostEntity() {
        //此种返回类型，要求使用此DataProvider的方法参数是ChallengeParam类型
        return new Object[][]{
                {new Challenge(4, 1846, 20)},
                {new Challenge(5, 1846, 30)}
        };
        //此种返回类型，要求使用此DataProvider的方法参数是Integer,Integer,Integer
//        return new Object[][]{
//                {4,1848,40},
//                {5,1848,50}
//        };
    }

    @Test(groups = {"qaPost"}, dataProvider = "forPostEntity")
    public void postEntity(Challenge param) {
        //保存挑战结果
        String url = qaPreUrl + "homework/student/challenge";
        Map<String, Object> map = new HashMap<>();
        map.put("recordId", param.getRecordId());
        map.put("challengerId", param.getChallengerId());
        map.put("pkScore", param.getPkScore());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(map, headers);

        ResponseEntity<Result> entity = restTemplate.postForEntity(url, httpEntity, Result.class);
        System.out.println(entity.getBody().isSuccess());
        System.out.println(entity.getStatusCodeValue());
        Assert.assertTrue(entity.getStatusCode().is2xxSuccessful());
    }

    /**
     * 为编辑赠品接口提供参数
     *
     * @return
     */
    @DataProvider(name = "editGift")
    public Object[][] gift() {
        return new Object[][]{
                {21, 1, "测试testNG222", "testNG-PUT"}
        };
    }

    /**
     * put和delete请求没有被特别给出，所以用exchange或execute方法来实现（get和post也可以用这两种方式）
     * 需要指定HttpMethod（GET POST PUT DELETE）
     * <p>
     * 注意：restTemplate请求接口只要有返回，都是200，也就是成功，因为它只管接口是否请求成功，
     * 所以使用getStatusCode().is2xxSuccessful()只能判断接口是否请求成功。
     * 我们还需要去判断接口的返回值success是否是true来判断我们的接口是否真的成功（或者是判断是否存在错误码和错误信息）
     */
    @Test(groups = {"putAndDel"}, dataProvider = "editGift")
    public void testPut(Integer giftId, Integer giftType, String giftName, String giftPicPath) {
        //编辑赠品
        String url = qaPreUrl + "management/gift";
        Map<String, Object> map = new HashMap<>();
        map.put("giftId", giftId);
        map.put("giftType", giftType);
        map.put("giftName", giftName);
        map.put("giftPicPath", giftPicPath);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(map);
        ResponseEntity<Result> entity = restTemplate.exchange(url, HttpMethod.PUT, httpEntity, Result.class);

//        ParameterizedTypeReference<Result<ResultList<Challenger>>> reference =
//                new ParameterizedTypeReference<Result<ResultList<Challenger>>>(){};
//        ResponseEntity<Result<ResultList<Challenger>>> entity =
//                restTemplate.exchange(builder.toString(), HttpMethod.GET, null, reference);

        System.out.println(entity.getBody());
        System.out.println(entity.getStatusCodeValue());
        //下面的断言只判断接口是否请求成功
        Assert.assertTrue(entity.getStatusCode().is2xxSuccessful());
        //下面的断言才会判断我们的接口走完逻辑是否成功
        Assert.assertTrue(entity.getBody().isSuccess());
    }

    /**
     * 注意 ：不加dependsOnMethods的话，由于testDel会比testPut先执行，会报错
     */
    @Parameters({"giftId"})
    @Test(groups = {"putAndDel"}, dependsOnMethods = {"testPut"})
    public void testDel(Integer giftId) {
        //删除赠品
        String url = qaPreUrl + "management/gift";
        Map<String, Object> map = new HashMap<>();
        map.put("giftId", giftId);
        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(map);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(httpEntity, Result.class);
        ResponseExtractor<ResponseEntity<Result>> extractor = restTemplate.responseEntityExtractor(Result.class);
        ResponseEntity<Result> execute = restTemplate.execute(url, HttpMethod.DELETE, requestCallback, extractor);
        System.out.println(execute.getBody());
        Assert.assertTrue(execute.getBody().isSuccess());
    }


}
