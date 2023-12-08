package com.ohgiraffers.intercepter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
* 핸들러 인터셉터를 구현한다.
* default 메소드 이전에는 모두 오버라이딩 해야 하는 책임을 가지기 때문에 JHandlerInterceptorAdaptor 를 이용해
* 부담을 줄여 사용했으나, default 메소드가 인터페이스에 사용 가능하게 된 1.8 버전 이후에는 인터페이스만 구현하여
* 필요한 메소드만 오버라이딩 해서 사용할 수 있다.
* */
@Component  // 빈을 등록하지 않아도 사용가능
public class StopWatchInterceptor implements HandlerInterceptor {

    private final MenuService menuService;

    public StopWatchInterceptor(MenuService menuService) {
        this.menuService = menuService;
    }
    
    /*전처리 메소드  지정된 컨트롤러의 동작 이전에 수행할 동작 (사전 제어).*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!request.getParameter("auth").equals("admin")){
            response.sendRedirect("/");
            return false;
        }
        System.out.println("prehandler 호출함");    // 1번 호출  true 일시 핸들러 접근
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);

        // 컨트롤러를 이어서 호출한다. false인 경우 핸들러 메소드를 호출하지 않는다.
        return true;
    }

    // 후처리 메소드   // 지정된 컨트롤러의 동작 이후에 처리할 동작 (사후 제어).  실행이 완료 되었지만 아직 view 가 생성되기 전에 호출됨
    //Spring MVC의 Dispatcher Servlet이 화면을 처리하기 전에 동작.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("posthandler 호출함");
        long startTime = (long) request.getAttribute("startTime");
        request.removeAttribute("startTime");
        long endTime = System.currentTimeMillis();

        modelAndView.addObject("interval", endTime - startTime);
    }

    @Override     // handlerInterceptor 를 사용하여 요청 처리가 완료된 후에 실행하는 메소드  -- Dispatcher Servlet의 화면 처리가 완료된 이후 처리할 동작.
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("after Complate 호출함");
        menuService.method();
    }
}
