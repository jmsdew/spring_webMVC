package com.ohgiraffers.handlermethod;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@Controller
@RequestMapping("/first/*")
@SessionAttributes("id")
public class FirstController {

    /*
    * 핸들러 메소드에 파라미터로 특정 몇 가지 타입을 선언하게 되면 핸들러 메소드 호출 시 인자로 값을 전달해준다.
    *
    * 컨트롤러 핸들러 메소드의 반환 값을 void 로 설정하면 요청 주소가 view 의 이름이 된다.
    * => /first/regist 요청이 들어오면 /first/regist 뷰로 응답한다.
    * 위의 first 는 template 하위의 디렉토리를 의미한다.
    * */

    @GetMapping("regist")
    public void regist(){}

    /*
    * 1. WebRequest 로 요청 파라미터 전달 받기
    *    파라미터 선언부에 WebRequest 타입을 선언하면 해당 메소드 호출 시 인자로 값을 전달해 준다.
    *    핸들러 메소드 매개변수로 HttpServletRequest , HttpServletResponse 도 사용 가능하다.
    *    상위 타입인 ServletRequest , ServletResponse 도 사용 가능하다.
    *    WebRequest 는 HttpServletRequest 의 요청 정보를 거의 대부분 그대로 가지고 있는 API 로 Servlet 에 종속적이지 않다.
    *    HttpServletRequest 는 Servlet API의 일부이고
    *    WebRequest 는 Spring 의 일부이기 때문에 Spring 기반의 프로젝트에서 다 자주 사용 된다.
    * */
    @PostMapping("regist")
    public String registMenu(Model model, WebRequest request){
        /* WebRequest 객체의 getParameter 등의 메소드를 통해 클라이언트로 부터 전달 된 파라미터를 가져올 수 있다.*/
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));

        /* 클라이언트로부터 전달 받은 값을 통해 응답할 화면의 메세지를 생성한다.*/
        String message = name + "을(를) 신규 메뉴 목록의 " + categoryCode + " 번 카테고리에 " + price + " 원 으로 등록 하셨습니다.";
        System.out.println(message);

        model.addAttribute("message", message);

        return "first/messagePrinter";  // 넘기면 dispatherServlet 에서 html을 붙혀 매핑 시킴
    }

    @GetMapping("modify")
    public void modify(){}

    /* 2. @RequestParam로 요청 파라미터 전달 받기
     *    요청 파라미터를 매핑하여 호출 시 값을 넣어주는 어노테이션으로 매개 변수 앞에 작성한다.
     *    form의 name 속성값과 매개변수의 이름이 다른 경우 @RequestParam("name")을 설정하면 된다.
     *    또한 어노테이션은 생략 가능하지만 명시적으로 작성하는 것이 의미 파악에 쉽다.
     *
     *    전달하는 form의 name속성이 일치하는 것이 없는 경우 400에러가 발생하는데 이는 required 속성의 기본 값이 true이기 때문이다.
     *    required 속성을 false로 하게 되면 해당 name값이 존재하지 않아도 null로 처리하며 에러가 발생하지 않는다.
     *
     *    값이 넘어오지 않게 되면 "" 빈 문자열이 넘어오게 되는데, 이 때 parsing 관련 에러가 발생할 수 있다.
     *    값이 넘어오지 않는 경우 defaultValue를 이용하게 되면 기본값으로 사용할 수 있다.
     * */


    @PostMapping("modify")
    public String modifyMenuPrice(Model model, @RequestParam(required = false) String modifyName,  
                                  // 변수명만 맞춰줘도 동작 하지만(꼬이는 경우도 있음)
                                  // 옵션들을 넣기 위해 @RequestParam 을 써줘야 함. required = 중복여부 , name = 이름 , defalte 등등 옵션
                                  @RequestParam(defaultValue = "0") int modifyPrice){
        String message = modifyName + " 메뉴 가격을 " + modifyPrice + "원으로 변경 하였습니다.";
        System.out.println(message);

        model.addAttribute("message",message);
        return "first/messagePrinter";

    }

    /*
    * 파라미터가 여러 개인 경우 맵으로 한 번에 처리할 수도 있다.
    * 이 때 맵의 키는 form 의 name 속성값이 된다.
    * */
    @PostMapping("modifyAll")
    public String modifyMenu(Model model, @RequestParam Map<String, String> parameters){     //  값이 전달될 때 전부 text 로 받아옴 (String)
        String modifyMenu = parameters.get("modifyName2");
        int modifyPrice = Integer.parseInt(parameters.get("modifyPrice2"));

        String message = "메뉴의 이름을 " + modifyMenu + " (으)로, 가격을 " + modifyPrice +" 원 으로 변경하였습니다.";
        System.out.println(message);

        model.addAttribute("message", message);
        return "first/messagePrinter";

    }


    @GetMapping("search")
    public void search(){}

    /*
    * 3. @Modelattribute 를 이용하는 방법
    * DTO 같은 모델을 커맨드 객체로 전달받는다.
    * 테스트 하기 위해서는 DTO 를 만든다.
    *
    * @ModelAttribute 의 경우 커맨드 객체를 생성하여 매개변수로 전달해 준 뒤 해당 인스턴스를 model 에 담는다.
    * 화면에서 출력 해보면 모델에 담겨진 값을 확인할 수 있다.
    *
    * 경우에 따라 폼에서 입력한 값을 다음 화면으로 바로 전달해야 하는 경우가 발생한느데 이 때 유용하게 사용할 수 있다.
    * @ModelAttribute("모델에담을key")를 지정할 수 있으며, 지정하지 않으면 타입의 앞글자를 소문자로 한 네이밍 규칙을 따른다.
    *  menuDTO로 담긴다.
    * */

    @PostMapping("search")
    public String searchMenu(@ModelAttribute("menu") MenuDTO menu){
        System.out.println(menu);
        return "first/searchResult";
    }

    @GetMapping("login")
    public void login(){}

    /* 4-1 session 이용하기
    * HttpSession 을 매개변수로 선언하면 핸들러 메소드 호출 시 세션 객체를 넣어서 호출한다.
    * */
    @PostMapping("login1")
    public String sessionTest1(HttpSession session, @RequestParam String id){
        session.setAttribute("id",id);
        return "first/loginResult";
    }

    @GetMapping("logout1")
    public String logoutTest1(HttpSession session){
        session.invalidate();
        return "first/loginResult";
    }

    /*
    * 4-2 @SessionAttribute 를 이용하여 Session에 값 담기
    * 클래스 레벨에 @SessionAttribute 어노테이션을 이용하여 세션에 값을 담을 key를 설정해 두면
    * model 영역에 해당 key로 값이 추가되는 경우 Session 에 자동 등록 한다.
    * */
    @PostMapping("login2")
    public String sessionTest2(Model model, @RequestParam String id){
        // Session의 id 와 일치하여 해당 속성은 session에 저장되어 관리한다.
        model.addAttribute("id", id);
        return "first/loginResult";
    }

    // sessionAttribute 로 등록된 값은 session 의 상태를 관리하는 sessionstatus 의 setcomplete() 메소드를 호출해야 사용이 만료된다.
    @GetMapping("logout2")
    public String logoutTest2(SessionStatus sessionStatus){
        // 현재 컨트롤러 세션에 저장된 모든 정보를 제거한다. 개별 제거는 불가능
        sessionStatus.setComplete();
        return "first/loginResult";
    }

    @GetMapping("body")
    public void body(){}
    /*  많이 쓰게 될 어노테이션
    * 5. @RequestBody 를 이용하는 방법
    * 해당 어노테이션은 http 본문 자체를 읽는 부분을 모델로 변환시켜 주는 어노테이션이다.
    *
    * 출력해보면 쿼리스트링 형태의 문자열이 전송된다.
    * json 으로 전달하는 경우 jackson 의 컨버터로 자동 파싱하여 사용할 수 있다.
    *
    * 주로 restApi 작성 시 많이 사용되며, 일반적인 form 전송을 할 때에는 거의 사용하지 않는다.
    *
    * 추가적으로 헤더에 대한 정보도
    * @RequestHeader 어노테이션을 이용하여 가져올 수 있다.
    * @CookieValue 를 이용해여 쿠키 정보도 쉽게 불러올 수 있다.
    * */

    @PostMapping("body")
    public void bodyTest(@RequestBody String body, @RequestHeader("content-type") String contentType, // 꺼내고자 하는 header의 key를 넣는다,.
                         @CookieValue(value = "JSESSIONID", required = false)String sessionId) throws UnsupportedEncodingException {
        System.out.println(contentType);
        System.out.println(sessionId);
        System.out.println(body);

        System.out.println(URLDecoder.decode(body,"UTF-8"));
    }


}
