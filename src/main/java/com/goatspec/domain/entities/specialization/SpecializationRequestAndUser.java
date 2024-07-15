package com.goatspec.domain.entities.specialization;

import com.goatspec.domain.entities.authentication.UserInfo;

public record SpecializationRequestAndUser(UserInfo userInfo, SpecializationRequest specializationRequest, String specializationStatus) {

}
