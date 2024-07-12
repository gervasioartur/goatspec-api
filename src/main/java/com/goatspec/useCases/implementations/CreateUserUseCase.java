package com.goatspec.useCases.implementations;

import com.goatspec.domain.entities.Role;
import com.goatspec.domain.entities.User;
import com.goatspec.domain.entities.UserAccount;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.UnexpectedException;
import com.goatspec.gateways.IAuthentication;
import com.goatspec.gateways.IPasswordEncoderGateway;
import com.goatspec.gateways.IRoleGateway;
import com.goatspec.gateways.IUserGateway;
import com.goatspec.useCases.contracts.ICreateUserService;

public class CreateUserUseCase implements ICreateUserService {
    private final IUserGateway userGateway;
    private final IRoleGateway roleGateway;
    private final IPasswordEncoderGateway passwordEncoderGateway;
    private final IAuthentication authentication;

    public CreateUserUseCase(IUserGateway userGateway, IRoleGateway roleGateway, IPasswordEncoderGateway passwordEncoderGateway, IAuthentication authentication) {
        this.userGateway = userGateway;
        this.roleGateway = roleGateway;
        this.passwordEncoderGateway = passwordEncoderGateway;
        this.authentication = authentication;
    }

    @Override
    public UserAccount create(User userDomainObject) {

        User userResult = this.userGateway.findUserByCpf(userDomainObject.cpf());
        if (userResult != null)
            throw new BusinessException("The CPF is already in use. Please try to sing in with credentials.");

        userResult = this.userGateway.findUserByEmail(userDomainObject.email());
        if (userResult != null) throw new BusinessException("The email is already in use. Please try another email.");

        userResult = this.userGateway.findUserByRegistration(userDomainObject.registration());
        if (userResult != null)
            throw new BusinessException("The registration is already in use. Please try another registration.");

        Role roleResult = this.roleGateway.findRoleByName(userDomainObject.role());
        if (roleResult == null)
            throw new UnexpectedException("Something went wrong while saving the information. Please concat the administrator.");

        String encodedPassword = this.passwordEncoderGateway.encode(userDomainObject.password());
        userResult = new User(userDomainObject.cpf(), userDomainObject.email(), userDomainObject.registration(),
                userDomainObject.name(), userDomainObject.dateOfBirth(), userDomainObject.gender(), userDomainObject.role(), encodedPassword);

        userResult = this.userGateway.create(userResult);

        String accessToken = this.authentication.authenticate(userResult.cpf(), userDomainObject.password());
        return new UserAccount(accessToken);
    }
}
