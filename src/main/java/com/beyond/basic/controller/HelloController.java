package com.beyond.basic.controller;

import com.beyond.basic.domain.Hello;
import com.beyond.basic.domain.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/hello") // 클래스 차원에 url 매핑시에 RequestMapping 사용
// @RestController // Controller + 각 메서드 마다 @ResponseBody
// 해당 클래스가 컨트롤러(사용자의 요청을 처리하고 응답하는 편의기능)임을 명시
public class HelloController {

    // case1. 사용자가 서버에게 화면 요청(get)
    // case2. @ResponseBody 가 붙을 경우 단순 String 데이터 return(get)

    @GetMapping("/")
    // getmapping을 통해 get 요청을 처리하고 url 패턴을 명시
    // @ResponseBody
    // responsebody를 사용하면 화면이 아닌 데이터를 return
    // 만약 여기서 responsebody가 없고 return이 스트링이면 스프링은 templates 폴더 밑에 helloworld.html 화면을 찾아 리턴한다.
    // ( 화면을 리턴하고 싶으면 responsebody를 빼고 데이터를 리턴하고 싶으면 responsebody를 붙이면 된다. )
    // public String helloWorld(){
        // 아래와 같이 Controller에서도 HttpServletRequest를 주입받아 사용가능
        public String helloWorld(HttpServletRequest request){
            System.out.println(request.getSession());
            System.out.println(request.getHeader("cookie"));
        return "helloworld";
    }

    // case3. 사용자가 json 데이터 요청(get)
    // data를 리턴하되, json 형식으로 return
    // method 명은 helloJson, url 패턴은 /hello/json
    @GetMapping("/json")
    // 메서드 차원에서도 RequestMapping 사용 가능
    // @RequestMapping(value = "/json", method = RequestMethod.GET)
    @ResponseBody
    // responsebody를 사용하면서 객체를 return시 자동으로 직렬화 된다.
    public Hello helloJson() throws JsonProcessingException {
        Hello hello = new Hello();
        hello.setName("hi");
        hello.setEmail("hi@naver.com");
//        ObjectMapper objectMapper = new ObjectMapper();
//        String value = objectMapper.writeValueAsString(hello);
//        return value;

        return hello;
    }

    // case4. 사용자가 json 데이터를 요청하되, parameter 형식으로 특정 객체 요청(get)
    // get 요청 중에 특정 데이터를 요청
    @GetMapping("/param1")
    @ResponseBody
    // parameter 형식 : ?name=hongildong&email=hongildong@naver.com
    // localhost:8080/hello/model-param1?name=hongildong / @RequestParam 어노테이션을 이용해서 name 키값의 값을 inputName에 담는다.
    public Hello Param1( @RequestParam(value = "name")String inputName){
        Hello hello  = new Hello();
        hello.setName(inputName);
        hello.setEmail("hongildong@naver.com");
        System.out.println(hello);
        return hello;
    }

    // url 패턴 : model-param2, 메서드명 : modelParam2
    // parameter2개 : name, email => hello 객체 생성 후 리턴
    // 요청방식 : ?name=xxx&email=xxx@naver.com
    @GetMapping("/param2")
    @ResponseBody
    public Hello Param2(@RequestParam(value = "name") String inputName,
                             @RequestParam(value = "email") String inputEmail){
        Hello hello = new Hello();
        hello.setName(inputName);
        hello.setEmail(inputEmail);
        return hello;
    }

    // case5. parameter 형식으로 요청하되, 서버에서 데이터바인딩 하는 형식(get)
    @GetMapping("/param3")
    @ResponseBody
    // parameter 가 많을 경우 객체로 대체가 가능하다. 객체에 각 변수에 맞게 알아서 매핑/바인딩 된다.(데이터바인딩)
    // ?name=xxx&email=xxx@naver.com&password=xxx
    // 데이터바인딩 조건 : 기본생성자, setter
    public Hello Param3(Hello hello){
        return hello;
    }

    // case6.서버에서 화면에 데이터를 넣어 사용자에게 return(model 객체 사용)(get)
    // @RestController : Controller + ResponseBody(데이터) 데이터 리턴이 아니라 화면 리턴을 해야함으로 쓰면 안됨
    @GetMapping("/model-param")
    public String modelParam(@RequestParam(value = "name")String inputName, Model model){
        // model 객체에 name 이라는 키 값에 value를 세팅하면 해당 key:value는 화면으로 전달
        model.addAttribute("name", inputName);
        return "helloworld";
    }

    // case7.pathvariable 방식을 통해 사용자로부터 값을 받아 화면 리턴(get)
    // localhost:8080/hello/model-path/hongildong
    // localhost:8080/author/1 또는 author?id=1
    // pathvariable 방식은 url을 통해 자원의 구조를 명확하게 표현함으로, 좀 더 restful 한 형식이다.
    @GetMapping("/model-path/{inputName}")
    public String modelPath(@PathVariable String inputName, Model model){
        model.addAttribute("name", inputName);
        return "helloworld";
    }

    @GetMapping("/form-view")
    // 사용자에게 name, email, password를 입력할 수 있는 화면을 주는 메서드 정의
    // 메서드명 : formView, 화면명 : form-view
    public String formView(){
        return "form-view";
    }

    // post요청(사용자 입장에서 서버에 데이터를 주는 상황)
    // case1. url인코딩 방식(text만 전송)
    // 형식 : key1=value1&key2=value2&key3=value3
    @PostMapping("/form-post1") // getmapping과 같은 url 패턴 사용도 가능
    @ResponseBody
    public String formPost1(
            @RequestParam(value = "name") String inputName,
            @RequestParam(value = "email") String inputEmail,
            @RequestParam(value = "password") String inputPassword ){

        // 사용자로부터 받아온 내용 출력
        System.out.println(inputName);
        System.out.println(inputEmail);
        System.out.println(inputPassword);
        return "ok";
    }

    @PostMapping("/form-post2")
    @ResponseBody
    public String formPost2(@ModelAttribute Hello hello){ // ModelAttribute는 생략 가능
        // 사용자로부터 받아온 내용 출력
        System.out.println(hello);
        return "ok";
    }

    // case2. multipart/form-data 방식(text와 파일) 전송
    // url명 : form-file-post, 메서드명 : formFilePost, 화면명 : form-file-view
    // form 태그 name, email, password, file
    @GetMapping("/form-file-post")
    public String formfilePost(){
        return "form-file-view";
    }

    @PostMapping("/form-file-post")
    @ResponseBody
    public String formFileHandle(Hello hello,
                                 @RequestParam(value = "photo")MultipartFile photo){

        System.out.println(hello);
        System.out.println(photo.getOriginalFilename()); // 파일명만 콘솔에 찍어보기
        return "ok";
    }

    // case3. js를 활용한 form 데이터 전송(text)
    @GetMapping("/axios-form-view")
    public String axiosFormView(){
        // name, email, password 를 전송
        return "axios-form-view";
    }

    @PostMapping("/axios-form-view")
    @ResponseBody
    // axios를 통해 넘어오는 형식이 key1=value1&key2=value2 등 url 인코딩 방식
    public String axiosFormPost(Hello hello){
        System.out.println(hello);
        return "ok";
    }

    // case4. js를 활용한 form 데이터 전송(+file)
    @GetMapping("/axios-form-file-view")
    public String axiosFormFileView(){
        return "axios-form-file-view";
    }
    @PostMapping("/axios-form-file-view")
    @ResponseBody
    public String axiosFormFileViewPost(Hello hello,
                                        @RequestParam(value = "file") MultipartFile file){
        System.out.println(hello);
        System.out.println(file.getOriginalFilename());
        return "ok";
    }

    // case5. js를 활용한 json 데이터 전송
    // url 패턴 : axios-json-view,  화면명 : axios-json-view, get요청메서드 : 동일,
    // post요청메서드 : axiosJsonPost
    @GetMapping("/axios-json-view")
    public String axiosJsonView(){
        return "axios-json-view";
    }

    @PostMapping("/axios-json-view")
    @ResponseBody
    // json으로 전송한 데이터를 받을때에는 @RequestBody 어노테이션을 사용 - 이게 표준!!
    public String axiosJsonPost(@RequestBody Hello hello){ // json 데이터를 parsing 해서 hello 객체를 만들 때 / json 데이터를 처리할 때 @RequestBody 어노테이션 사용
        System.out.println(hello);
        return "ok";
    }

    // case6. js를 활용한 json 데이터 전송(+file)
    @GetMapping("/axios-json-file-view")
    public String axiosJsonFileView(){
        return "axios-json-file-view";
    }

    @PostMapping("/axios-json-file-view")
    @ResponseBody
    // RequestPart는 파일과 Json을 처리할 때 주로 사용하는 어노테이션
    public String axiosJsonFilePost(

//            @RequestParam(value = "hello") String hello,
//            @RequestParam(value = "file") MultipartFile file

            // formData를 통해 json, file(멀티미디어)을 처리할 때 RequestPart 라는 어노테이션을 많이 사용
            @RequestPart("hello") Hello hello,
            @RequestPart("file") MultipartFile file

    ) throws JsonProcessingException {

        System.out.println(hello);
        // String 으로 받은 뒤 수동으로 객체로 변환
        // ObjectMapper objectMapper = new ObjectMapper();
        // Hello h1 = objectMapper.readValue(hello, Hello.class);
        // System.out.println(h1.getName());
        System.out.println(file.getOriginalFilename());
        return "ok";
    }

    // case7. js를 활용한 json 데이터 전송(+여러 file)
    @GetMapping("/axios-json-multi-file-view")
    public String axiosJsonMultiFileView(){
        return "axios-json-multi-file-view";
    }

    @PostMapping("/axios-json-multi-file-view")
    @ResponseBody
    public String axiosJsonMultiFilePost(
            @RequestPart("hello") Hello hello,
            @RequestPart("files") List<MultipartFile> files

    ) throws JsonProcessingException {

        System.out.println(hello);
        for(MultipartFile file : files){
            System.out.println(file.getOriginalFilename());
        }
        return "ok";
    }

    // case8. 중첩된 JSON 데이터 처리
    // Student 객체
    // {name:"hongildong", email:"hong@naver.com", scores:[{math:60}, {music:70}, {english:100}]}
    @GetMapping("/axios-nested-json-view")
    public String axiosNestedJsonView(){
        return "axios-nested-json-view";
    }
    @PostMapping("/axios-nested-json-view")
    @ResponseBody
    public String axiosNestedJsonPost(@RequestBody Student student){
        System.out.println(student);
        return "ok";
    }

    // 빌더 패턴 실습
    public void helloBuilderTest(){
        Hello hello = Hello.builder().build();
    }

}
