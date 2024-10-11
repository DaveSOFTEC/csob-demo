package cz.csob.demo.controller;

import cz.csob.demo.model.UserDto;
import cz.csob.demo.service.UserInterestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@AllArgsConstructor
public class DemoController {

    private UserInterestService userInterestService;

    @GetMapping(value = "/interests-of-users", produces = {"application/json"})
    public ResponseEntity<Map<String, List<UserDto>>> getUserInterests(@RequestParam List<String> interests) {

        Map<String, List<UserDto>> userInterests = userInterestService.getInterests(interests);
        return ResponseEntity.ok().body(userInterests);
    }
}
