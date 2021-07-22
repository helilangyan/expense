//package bht.expense.gateway.swagger;
//
//import bht.expense.gateway.swagger.ExpenseSwaggerResourceProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Profile;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import springfox.documentation.swagger.web.*;
//
//import java.util.List;
//
//
///**
// * @author 姚轶文
// * @date 2021/4/8 14:20
// */
//@RestController
//@RequestMapping("/swagger-resources")
//@Profile({"dev","test"})
//public class SwaggerResourceController {
//
//    private ExpenseSwaggerResourceProvider swaggerResourceProvider;
//
//    @Autowired
//    public SwaggerResourceController(ExpenseSwaggerResourceProvider swaggerResourceProvider) {
//        this.swaggerResourceProvider = swaggerResourceProvider;
//    }
//
//    @RequestMapping(value = "/configuration/security")
//    public ResponseEntity<SecurityConfiguration> securityConfiguration() {
//        return new ResponseEntity<>(SecurityConfigurationBuilder.builder().build(), HttpStatus.OK);
//    }
//
//    @RequestMapping(value = "/configuration/ui")
//    public ResponseEntity<UiConfiguration> uiConfiguration() {
//        return new ResponseEntity<>(UiConfigurationBuilder.builder().build(), HttpStatus.OK);
//    }
//
//    @RequestMapping
//    public ResponseEntity<List<SwaggerResource>> swaggerResources() {
//        return new ResponseEntity<>(swaggerResourceProvider.get(), HttpStatus.OK);
//    }
//
//
//}
