package com.example.zzh.test;

import com.example.zzh.ZzhApplication;
import com.example.zzh.mapper.ChannelMapper;
import com.example.zzh.mapper.GiftMapper;
import com.example.zzh.mapper.MealMapper;
import com.example.zzh.mapper.ProductMapper;
import com.example.zzh.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: zhao zhihong
 * @Date: 2020/3/17
 */
@SpringBootTest(classes = ZzhApplication.class)
public class FinalTest extends AbstractTestNGSpringContextTests {

    /**
     * 最后写一个完整的流程测试：商品和sku的新增、查询、更改、删除以及相关联的逻辑
     * 流程：① 新建商品（包含sku和赠品等）
     */

    private RestTemplate restTemplate;

    private String qaPreUrl;

    private Product initProduct;



    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private GiftMapper giftMapper;
    @Autowired
    private ChannelMapper channelMapper;
    @Autowired
    private MealMapper mealMapper;


    @Parameters({"qaPreUrl"})
    @BeforeClass
    public void init(String qaPreUrl) {
        this.restTemplate = new RestTemplate();
        this.qaPreUrl = qaPreUrl;
    }

    /**
     * 踩坑！！！！
     * 下面这个方法抛异常了不会展示在控制台，依赖此方法的方法会被skip掉
     */
    @DataProvider(name = "product")
    public Object[][] product() {
        Product productParam = new Product();
        //商品信息
        productParam.setProductName("testNG测试商品" + System.currentTimeMillis());
        productParam.setProductStatus(1);
        productParam.setImageCover("封面图url");
        productParam.setImageContent("内容url");
        productParam.setImageEvaluation("评价url");
        productParam.setImageIntroduction("介绍url");
        productParam.setRemark("备注");
        productParam.setDescription("描述");
        //sku信息，模拟该商品有两个sku
        List<Sku> skuList = new ArrayList<>();
        Sku sku1 = initSku();
        skuList.add(sku1);
        Sku sku2 = initSku();
        skuList.add(sku2);
        productParam.setProductSKUList(skuList);
        //班级信息
        String url = qaPreUrl + "management/class/list?current=1&pageSize=10";
        ParameterizedTypeReference<Result<ResultList<Clazz>>> reference =
                new ParameterizedTypeReference<Result<ResultList<Clazz>>>() {
                };
        ResponseEntity<Result<ResultList<Clazz>>> entity =
                restTemplate.exchange(url, HttpMethod.GET, null, reference);
        List<Clazz> entries = entity.getBody().getData().getEntries();
        if (!CollectionUtils.isEmpty(entries)) {
            //模拟关联两个班级
            //最后的ClassIdList有两种情况，第一种是只有一个班级，第二种有两个班级，取决于entries集合的大小
            List<Integer> classIds = new ArrayList<>();
            for (Clazz entry : entries) {
                if (classIds.size() == 2) {
                    break;
                }
                classIds.add(entry.getClassId());
            }
            productParam.setClassIdList(classIds);
        }
        //把商品信息赋值给product属性成为全局变量(查询时还有用)
        initProduct = productParam;

        return new Object[][]{
                {productParam}
        };
    }

    /**
     * 初始化sku信息
     *
     * @return
     */
    private Sku initSku() {
        Random r = new Random();
        Sku sku = new Sku();
        sku.setSkuType(r.nextInt(2));
        sku.setValidityPeriod(r.nextInt(365) + 1);
        sku.setOriginalPrice(new BigDecimal(100));
        sku.setPresentPrice(new BigDecimal(100));
        sku.setRetailPrice(new BigDecimal(90));
        sku.setSkuDesc("testNG测试商品sku");
        sku.setOpenSale(new Date());
        sku.setPeriodNum(20);
        //获取赠品信息
        String url = qaPreUrl + "management/gift/list?current=1&pageSize=10";
        ParameterizedTypeReference<Result<ResultList<Gift>>> reference =
                new ParameterizedTypeReference<Result<ResultList<Gift>>>() {
                };
        ResponseEntity<Result<ResultList<Gift>>> entity =
                restTemplate.exchange(url, HttpMethod.GET, null, reference);
        List<Gift> entries = entity.getBody().getData().getEntries();
        if (!CollectionUtils.isEmpty(entries)) {
            //模拟只关联一个赠品
//            List<Integer> giftIds = Arrays.asList(entries.get(0).getGiftId());
            Gift gift = entries.get(0);
            Integer giftId = gift.getGiftId();
            List<Integer> giftIds = Collections.singletonList(giftId);
            sku.setGiftIdList(giftIds);
        }
        //不模拟configId的数据了，方式和giftId类似


        return sku;
    }

    /**
     * 新增商品
     *
     * @param product
     */
    @Test(dataProvider = "product")
    public void addProduct(Product product) {
        String url = qaPreUrl + "management/product";
        HttpEntity<Product> entity = new HttpEntity<>(product);
        Result result = restTemplate.postForObject(url, entity, Result.class);
        Assert.assertTrue(result.isSuccess());

    }

    /**
     * 查询商品
     */
    @Test(dependsOnMethods = {"addProduct"})
    public void getProduct() {
        //获取新增方法新增的商品ID
        String productName = initProduct.getProductName();
        Integer productId = productMapper.getProduct(productName);

        String url = qaPreUrl + "management/product/" + productId;
        ParameterizedTypeReference<Result<Product>> reference =
                new ParameterizedTypeReference<Result<Product>>() {
                };
        ResponseEntity<Result<Product>> entity =
                restTemplate.exchange(url, HttpMethod.GET, null, reference);
        Result<Product> result = entity.getBody();
        System.out.println(result);
        //断言接口请求成功
        Assert.assertTrue(result.isSuccess());
        Product productData = result.getData();

        List<Clazz> classList = productData.getClassList();
        List<Integer> classIdList = initProduct.getClassIdList();
        //断言商品详情中的class信息和新增时的一致
        Assert.assertTrue(classIdList.size() == classList.size());
        List<Integer> tempClassIds = new ArrayList<>();
        for (Clazz clazz : classList) {
            tempClassIds.add(clazz.getClassId());
        }
        Assert.assertTrue(classIdList.containsAll(tempClassIds));
        //断言商品详情中的sku信息与新增时一致
        List<Sku> productSKUList = initProduct.getProductSKUList();
        List<Sku> skuList = productData.getProductSKUList();

        Assert.assertTrue(productSKUList.size() == skuList.size());
        //断言sku中的赠品信息与新增时一致
        for (Sku sku : skuList) {
            Assert.assertTrue(sku.getGiftIdList().equals(productSKUList.get(0).getGiftIdList()));
        }

        //赋值给initProduct,方便后续编辑使用
        initProduct = productData;
    }

    private Integer classId;

    private Integer skuId;

    /**
     * 编辑商品
     * 方法中的classList和skuList在新增的时候设置了都是两个，所以这里直接删一个留一个，
     * 如果涉及到留或删多个，可以自行控制属性的类型。
     */
    @Test(dependsOnMethods = "getProduct")
    public void editProduct() {

        List<Clazz> classList = initProduct.getClassList();
        initProduct.setClassIdList(Collections.singletonList(classList.get(0).getClassId()));
        //记录被删的数据，方便之后做验证
        this.classId = classList.get(1).getClassId();
        List<Sku> skuList = initProduct.getProductSKUList();
        initProduct.setProductSKUList(Collections.singletonList(skuList.get(0)));
        this.skuId = skuList.get(1).getSkuId();

        String url = qaPreUrl + "management/product";
        HttpEntity<Product> entity = new HttpEntity<>(initProduct);
        ResponseEntity<Result> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Result.class);
        Assert.assertTrue(result.getBody().isSuccess());

    }

    /**
     * 校验商品
     */
    @Test(dependsOnMethods = "editProduct")
    public void checkProduct() {
        String url = qaPreUrl + "management/product/" + initProduct.getProductId();
        ParameterizedTypeReference<Result<Product>> reference =
                new ParameterizedTypeReference<Result<Product>>() {
                };
        ResponseEntity<Result<Product>> entity =
                restTemplate.exchange(url, HttpMethod.GET, null, reference);
        Result<Product> result = entity.getBody();
        System.out.println(result);
        //断言接口请求成功
        Assert.assertTrue(result.isSuccess());
        Product productData = result.getData();
        //断言sku的信息是否是编辑时保留的sku
        List<Sku> skuList = productData.getProductSKUList();
        Assert.assertTrue(skuList.size() == 1);
        Assert.assertTrue(skuList.get(0).getSkuId() != skuId);
        //断言class信息是否是编辑时保留的class
        List<Clazz> classList = productData.getClassList();
        Assert.assertTrue(classList.size() == 1);
        Assert.assertTrue(classList.get(0).getClassId() != classId);
    }

    /**
     * 编辑后要验证相关的表的删除逻辑
     * 删除sku：①要同时删除渠道配置的sku
     * ②要同时删除sku关联的套餐
     * ③要同时删除代理配置的该sku的套餐
     */
    @Test(dependsOnMethods = {"checkProduct"})
    public void checkOther() {
        //sql中查的是被删除的sku在channelSku表的所有未删除的记录
        List<Integer> channleSkus = channelMapper.checkChannleSku(skuId);
        //集合长度为0，说明逻辑①验证通过
        Assert.assertTrue(channleSkus.size() == 0);

        //sql中查的是被删除的sku在productMeal表的所有未删除的记录
        List<Integer> skuMeals = mealMapper.getSkuMeals(skuId);
        //集合长度为0，说明逻辑②验证通过
        Assert.assertTrue(skuMeals.size() == 0);

        //sql中查的是被删除的sku的meal在agentMeal表的所有未删除的记录
        List<Integer> agentMeals = mealMapper.getAgentMeals(skuId);
        //集合长度为0，说明逻辑③验证通过
        Assert.assertTrue(skuMeals.size() == 0);

    }

}
