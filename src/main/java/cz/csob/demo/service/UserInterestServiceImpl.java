package cz.csob.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.csob.demo.config.RestTemplateConfig;
import cz.csob.demo.model.User;
import cz.csob.demo.model.UserDto;
import cz.csob.demo.model.UserResponse;
import cz.csob.demo.util.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserInterestServiceImpl implements UserInterestService {

    @Autowired
    private RestTemplateConfig restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${rest.user.url}")
    private String url;

    @Override
    public Map<String, List<UserDto>> getInterests(List<String> interests) {

        ResponseEntity<String> response = callUserInterestsApi();
        Map<String, List<UserDto>> interestsMap = new HashMap<>();

        if (response.getBody() != null) {
            try {
                UserResponse userResponse = objectMapper.readValue(response.getBody(), UserResponse.class);
                mapSourceDataToResponseMap(interests, interestsMap, userResponse);

            } catch (Exception e) {
                throw new RuntimeException("JSON parsing failed.", e);
            }
        }

        return interestsMap;
    }

    private ResponseEntity<String> callUserInterestsApi() {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                String.class
        );
    }

    private void mapSourceDataToResponseMap(
            List<String> interests,
            Map<String, List<UserDto>> interestsMap,
            UserResponse userResponse) {

        StreamUtils.forEachNullable(userResponse.getUsers(), user ->
                StreamUtils.streamOfNullable(user.getInterests())
                        .filter(interests::contains)
                        .forEach(key -> interestsMap.computeIfAbsent(key, u -> new ArrayList<>())
                                .add(mapUserToUserDto(user))));
    }

    private UserDto mapUserToUserDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .name(String.join(" ", user.getFirstName(), user.getLastName()))
                .build();
    }
}
