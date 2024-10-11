package cz.csob.demo.service;

import cz.csob.demo.model.UserDto;

import java.util.List;
import java.util.Map;

public interface UserInterestService {
    Map<String, List<UserDto>> getInterests(List<String> interests);
}
