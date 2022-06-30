package org.sse.Controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.sse.mapper.*;
import org.sse.pojo.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@RestController
@SuppressWarnings("all")
//@CrossOrigin
public class Controller {
    private Gson gson = new Gson();

    @RequestMapping("/test")
    public String test(){
        return "This is a test program!";
    }


    //Factory------------------------------------------------------------------------------------------------
    @Autowired
    private FactoryMapper factoryMapper;
    @RequestMapping("/getAllFactories")
    public String getAllFactories () {
        List<Factory> factories = factoryMapper.selectList(null);
        return gson.toJson(factories);
    }

    //通过设置了条件的对象进行条件查询
    @PostMapping("/getFactoriesByQuery")
    public String getFactoriesByQuery(@RequestBody Factory factory){
        QueryWrapper<Factory> factoryQueryWrapper = new QueryWrapper<>(factory);
        List<Factory> factoryList = factoryMapper.selectList(factoryQueryWrapper);
        return gson.toJson(factoryList);
    }
    @PostMapping("/addFactory")
    public void addFactory (@RequestBody Factory factory) {
        factoryMapper.insert(factory);
    }
    @PostMapping("/deleteFactory")
    public void deleteFactory (@RequestBody Factory factory) {
        factoryMapper.deleteById((Serializable) factory);
    }
    @PostMapping("/updateFactory")
    public void updateFactory (@RequestBody Factory factory) {
        factoryMapper.updateById(factory);
    }

    @GetMapping("/countAllFactory")
    public long countAllFactories(){
        long count =  factoryMapper.selectCount(null);
        return count;
    }

    //Device-----------------------------------------------------------------------------------------------------

    @Autowired
    private DeviceMapper deviceMapper;

    @GetMapping("/getAllDevice")
    public String getAllDevice () {
        List<Device> devices = deviceMapper.selectList(null);
        return gson.toJson(devices);
    }
    //通过设置了条件的对象进行条件查询
    @PostMapping("/getDeviceByQuery")
    public String getDeviceByQuery(@RequestBody Device device){
        QueryWrapper<Device> deviceQueryWrapper = new QueryWrapper<>(device);
        List<Device> deviceList = deviceMapper.selectList(deviceQueryWrapper);
        return gson.toJson(deviceList);
    }

    @PostMapping("/addDevice")
    public void addDevice (@RequestBody Device device) {
        deviceMapper.insert(device);
    }
    @PostMapping("/deleteDevice")
    public void deleteDevice (@RequestBody Device device) {
        deviceMapper.deleteById((Serializable) device);
    }
    @PostMapping("/updateDevice")
    public void updateDevice (@RequestBody Device device) {
        deviceMapper.updateById(device);
    }

    @GetMapping("/countAllDevice")
    public long countAllDevice(){
        long count =  deviceMapper.selectCount(null);
        return count;
    }

    //对可用设备计数
    @GetMapping("/countUsableDevice")
    public long countUsableDevice(){
        QueryWrapper<Device> queryWrapper=new QueryWrapper();
        queryWrapper.eq("status",1);
        long count =  deviceMapper.selectCount(queryWrapper);
        return count;
    }

    //Product---------------------------------------------------------------------------------------------------
    @Autowired
    private ProductMapper productMapper;

    @RequestMapping("/getAllProduct")
    public String getAllProduct () {
        List<Product> products = productMapper.selectList(null);
        return gson.toJson(products);
    }
    //通过设置了条件的对象进行条件查询
    @PostMapping("/getProductBYQuery")
    public String getProductByQuery(@RequestBody Product product){
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>(product);
        List<Product> productList = productMapper.selectList(productQueryWrapper);
        return gson.toJson(productList);
    }

    @PostMapping("/addProduct")
    public void addProduct (@RequestBody Product product) {
        productMapper.insert(product);
    }
    @PostMapping("/deleteProduct")
    public void deleteProduct (@RequestBody Product product) {
        productMapper.deleteById((Serializable) product);
    }
    @PostMapping("/updateProduct")
    public void updateProduct (@RequestBody Product product) {
        productMapper.updateById(product);
    }

    //Order-----------------------------------------------------------------------------------------------------
    @Autowired
    private OrderMapper orderMapper;

    @RequestMapping("/getAllOrder")
    public String getAllOrder () {
        List<Order> orders = orderMapper.selectList(null);
        return gson.toJson(orders);
    }
    //通过设置了条件的对象进行条件查询
    @PostMapping("/getOrderByQuery")
    public String getOrderBYQuery(@RequestBody Order order){
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>(order);
        List<Order> orderList = orderMapper.selectList(orderQueryWrapper);
        return gson.toJson(orderList);
    }
    @PostMapping("/addOrder")
    public void addOrder (@RequestBody Order order) {
        orderMapper.insert(order);
    }
    @PostMapping("/deleteOrder")
    public void deleteOrder (@RequestBody Order order) {
        orderMapper.deleteById((Serializable) order);
    }
    @PostMapping("/updateOrder")
    public void updateOrder (@RequestBody Order order) {
        orderMapper.updateById(order);
    }

    @GetMapping("/countAllOrder")
    public long countAllOrder(){
        long count =  orderMapper.selectCount(null);
        return count;
    }
    //对完成的订单计数
    @RequestMapping("/countDoneOrder")
    public long countDoneOrder(){
        QueryWrapper<Order> queryWrapper=new QueryWrapper();
        queryWrapper.eq("status",1);
        long count =  orderMapper.selectCount(queryWrapper);
        return count;
    }

    //GeneratePlan--------------------------------------------------------------------------------------------
    @Autowired
    private GeneratePlanMapper generatePlanMapper;

    @RequestMapping("/getAllGeneratePlan")
    public String getAllOGeneratePlan () {
        List<GeneratePlan> generatePlans = generatePlanMapper.selectList(null);
        return gson.toJson(generatePlans);
    }


    @PostMapping("/addGeneratePlan")
    public void addGeneratePlan (@RequestBody GeneratePlan generatePlan) {
        generatePlanMapper.insert(generatePlan);
    }
    @PostMapping("/deleteGeneratePlan")
    public void deleteGeneratePlan (@RequestBody GeneratePlan generatePlan) {
        generatePlanMapper.deleteById((Serializable) generatePlan);
    }
    @PostMapping("/updateGeneratePlan")
    public void updateGeneratePlan (@RequestBody GeneratePlan generatePlan) {
        generatePlanMapper.updateById(generatePlan);
    }

    @RequestMapping("/countAllGeneratePlan")
    public long countAllGeneratePlan(){
        long count =  generatePlanMapper.selectCount(null);
        return count;
    }
    //对完成的生产计划计数
    @RequestMapping("/countDoneGeneratePlan")
    public long countDoneGeneratePlan(){
        QueryWrapper<GeneratePlan> queryWrapper=new QueryWrapper();
        queryWrapper.eq("status",1);
        long count =  generatePlanMapper.selectCount(queryWrapper);
        return count;
    }

    //生产调度-----------------------------------------------------------------------------------------

    //根据一个变化量（产品对象形式）更新一个产品、订单、生产计划,返回更新后的生产计划
    @PostMapping("/updatePlanByProduct")
    public String updataPlan(Product product){
        //更新产品数量
        QueryWrapper<Product> queryWrapperProduct = new QueryWrapper<>();
        queryWrapperProduct.eq("id",product.getId());
        Product exsiting_product = (Product) productMapper.selectObjs(queryWrapperProduct);
        exsiting_product.setAmount(exsiting_product.getAmount()+product.getAmount());
        this.updateProduct(exsiting_product);

//        //更新订单数量
//        QueryWrapper<Order> queryWrapperOrder = new QueryWrapper<>();
//        queryWrapperOrder.eq("product_id",product.getId());
//        Order exsiting_order = (Order) orderMapper.selectObjs(queryWrapperOrder);
//        exsiting_order.setExisting_amount(exsiting_product.getAmount());
//        this.updateOrder(exsiting_order);

        //更新计划
        QueryWrapper<GeneratePlan> queryWrapperPlan = new QueryWrapper<>();
        queryWrapperPlan.eq("product_id",product.getId());
        GeneratePlan exsiting_Plan = (GeneratePlan) generatePlanMapper.selectObjs(queryWrapperPlan);
        exsiting_Plan.setExistingAmount(exsiting_product.getAmount());
        this.updateGeneratePlan(exsiting_Plan);

        //返回更新后的生产计划
        return gson.toJson(exsiting_Plan);
    }

    //Account-------------------------------------------------------------------------------------------
    private AccountMapper accountMapper;
    @PostMapping("/addAccount")
    public void addAccount (@RequestBody Account account) {
        accountMapper.insert(account);}

    @PostMapping("/updateAccount")
    public void updateAccount(@RequestBody Account account){
        accountMapper.updateById(account);
    }

    //验证用户数据，存在返回true，不存在返回false
    @PostMapping("/validateAccount")
    public boolean validateAccount(@RequestBody Account account){
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",account.getUsername()).eq("password",account.getPassword());
        Account target_account = (Account) accountMapper.selectObjs(queryWrapper);
        if(target_account == null) {
            return false;
        }else return true;
    }

    //上传头像,需要图像文件和用户对象
    @PostMapping("/uploadPic")
    public String uploadPic(@RequestParam("file")MultipartFile file,Account account){
        if(file.isEmpty()){
            return "upload failed!";
        }else {
            try {
                InputStream is = file.getInputStream();
                QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("username",account.getUsername()).eq("password",account.getPassword());
                Account target_account = (Account) accountMapper.selectObjs(queryWrapper);
                byte[] pic = new byte[(int)file.getSize()];
                is.read(pic);
                account.setPic(pic);
                this.updateAccount(target_account);
                return "上传成功";
            }catch (IOException e) {
                e.printStackTrace();
            }
            return "success";
        }
    }

    //展示图像
    @RequestMapping(value="/getPhotoById")
    public void getPhotoById(final HttpServletResponse response,Account account) throws IOException {
        byte[] data = account.getPic();
        response.setContentType("image/jpeg");
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        InputStream in = new ByteArrayInputStream(data);
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf, 0, 1024)) != -1) {
            outputSream.write(buf, 0, len);
        }
        outputSream.close();
    }


    //Report------------------------------------------------------------------------------------
    @Autowired
    private ReportMapper reportMapper;

    @RequestMapping("/getReport")
    public String getReport () {
        List<Report> reportList = reportMapper.selectList(null);
        return gson.toJson(reportList);
    }

    @PostMapping("/addReport")
    public void addReport (@RequestBody Report report) {
        reportMapper.insert(report);
    }

}
