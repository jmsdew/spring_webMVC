package com.ohgiraffers.request;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller                         // 사용자의 요청을 받기 위한 servlet 등록 외부에서 요청을 받기 위함 
@RequestMapping("/order/*")      // 어떤 요청이 들어왔을 때 이 클래스에서 처리한다는 의미
public class ClassMappingTestController {
    
    // port : 80 = web 서버
    // port : 8080 = was 서버
    // port : 443 = https 서버

    // Get : localhost:8080/order/regist
    @GetMapping("/regist") //  order 하위의 리소스 요청 중 get 방식 regist 라는 이름 으로 들어온 요청을 처리함 
    public String registOrder(Model model){
        model.addAttribute("message", "Get 방식의 주문 등록용 핸들러 메소드 호출");
        
        return "mappingResult";       // controller 어노테이션이 달리면 리턴값이 string 일때 html 파일을 매핑해 찾아감
    }

    @RequestMapping(value = {"modify", "delete"}, method = RequestMethod.POST)
    public String modifyAndDelete(Model model){

        model.addAttribute("message", "post 방식의 주문 정보 수정과 주문 정보 삭제 공통 처리용 핸들러");
        return "mappingResult";
    }

    /*
    * 3. path Variable
    * @Pathvariable 어노테이션을 이용해 요청을 path로 부터 변수를 받아올 수 있다.
    * path variable 로 전달 되는 {변수명} 값은 반드시 매개변수명과 동일해야 한다.
    * 만약 동일하지 않으면 @PathVariable("이름") 을 설정 해 주어야 한다.
    * 이는 rest 형 웹 서비스를 설계할 때 유용하게 사용된다.
    *
    * 인텔리제이의 builder 설정을 intellij 로 했을 경우에는 @PathVariable 에 이름을 지정해주지 않으면 찾지 못한다.
    * */

    @GetMapping("/detail/{orderNo}")
    public String selectOrderDetail(Model model, @PathVariable("orderNo") int orderNo){
        model.addAttribute("message", orderNo + " 번 주문 상세 내용 조회용 핸들러 메소드 호출");
        return "mappingResult";
    }

    @RequestMapping
    public String otherRequest(Model model){
        model.addAttribute("message", "order 요청이긴 하지만 다른 기능이 준비되지 않음");
        return "mappingResult";
    }
}
