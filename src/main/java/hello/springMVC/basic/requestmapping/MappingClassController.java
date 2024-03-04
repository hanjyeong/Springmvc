package hello.springMVC.basic.requestmapping;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mapping/users") //중복되는 주소 처리
public class MappingClassController {

    @GetMapping
    public String users(){
        //회원 목록 조회
        return "get users";
    }

    @PostMapping
    public String addUser(){
        //회원 등록
        return "post user";
    }

    @GetMapping("{userId}")
    public String findUser(@PathVariable String userId){
        // 회원 조회
        return "get userId = "+ userId;
    }

    @PatchMapping("{userId}")
    public String updateUser(@PathVariable String userId){
        // 회원 수정
        return "update userId = " +userId;
    }

    @DeleteMapping("{userId}")
    public String deleteUser(@PathVariable String userId){
        //회원 삭제
        return "delete userId = " + userId;
    }

}
