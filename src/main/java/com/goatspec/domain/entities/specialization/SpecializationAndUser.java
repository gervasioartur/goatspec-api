package com.goatspec.domain.entities.specialization;

import com.goatspec.domain.entities.authentication.UserInfo;

public record SpecializationAndUser(UserInfo userInfo, Specialization specialization, String specializationStatus) {

}
