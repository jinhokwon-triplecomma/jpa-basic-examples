package com.example.demo.user.controller;

import com.example.demo.user.dto.request.CreateUserRequest;
import com.example.demo.user.dto.response.CreateUserResponse;
import com.example.demo.user.dto.response.GetUserResponse;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(path = "")
    ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest request) {
        return ResponseEntity.ok(
                CreateUserResponse.of(
                        userService.create(
                                request.getName(),
                                request.getPass(),
                                request.getRoles()
                        )
                )
        );
    }

    @GetMapping("/{id}")
    ResponseEntity<GetUserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(
                GetUserResponse.of(userService.getUser(id)));
    }

//
//    // page=3&size=10&sort=id,DESC&sort=name,DESC .. 와 같은 방식으로 여러 컬럼을 기준으로 정렬이 가능함.
//    @GetMapping("/user/list")
//    ResponseEntity getUserList(@RequestParam(required = false) String name,
//                               @RequestParam(required = false) String email,
//                               @SortDefault.SortDefaults({
//                                       @SortDefault(sort = "id", direction = Sort.Direction.DESC),
//                                       @SortDefault(sort = "name", direction = Sort.Direction.ASC)
//                               })
//                               @PageableDefault(page = 0, size = 100) Pageable pageable) {
//        return ResponseEntity.ok(toUserVOList(userService.getUserList(name, email, pageable)));
//    }
//
//    @GetMapping("/user/list2")
//    ResponseEntity getUserList2(@RequestParam(required = false) String name,
//                                @RequestParam(required = false) String email,
//                                @RequestParam(required = false) String accessCode,
//                                @SortDefault.SortDefaults({
//                                        @SortDefault(sort = "id", direction = Sort.Direction.DESC),
//                                        @SortDefault(sort = "name", direction = Sort.Direction.ASC)
//                                })
//                                @PageableDefault(page = 0, size = 100) Pageable pageable) {
//        return ResponseEntity.ok(toUserListVO(userService.getUserList2(name, email, accessCode, pageable)));
//    }
//
//
//    @GetMapping("/user/list/pageable")
//    ResponseEntity getPageableUserList(@SortDefault.SortDefaults({
//            @SortDefault(sort = "id", direction = Sort.Direction.DESC),
//            @SortDefault(sort = "name", direction = Sort.Direction.ASC)
//    })
//                                       @PageableDefault(page = 0, size = 100) Pageable pageable) {
//        return ResponseEntity.ok(toUserListVO(userService.getPageAll(pageable)));
//    }
//
//    @GetMapping("/user/list/slice")
//    ResponseEntity getSliceUserList(@SortDefault.SortDefaults({
//            @SortDefault(sort = "id", direction = Sort.Direction.DESC),
//            @SortDefault(sort = "name", direction = Sort.Direction.ASC)
//    })
//                                    @PageableDefault(page = 0, size = 100) Pageable pageable) {
//        return ResponseEntity.ok(toUserListVO(userService.getSliceAll(pageable)));
//    }
}
