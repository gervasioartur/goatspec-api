package com.goatspec.application.useCases.implementations.user;

import com.goatspec.application.gateways.authentication.IAuthenticationGateway;
import com.goatspec.application.gateways.encrypt.IPasswordEncoderGateway;
import com.goatspec.application.gateways.role.IRoleGateway;
import com.goatspec.application.gateways.user.IUserGateway;
import com.goatspec.application.useCases.contracts.user.ICreateUserUseCase;
import com.goatspec.domain.entities.role.Role;
import com.goatspec.domain.entities.user.User;
import com.goatspec.domain.entities.user.UserAccount;
import com.goatspec.domain.exceptions.BusinessException;
import com.goatspec.domain.exceptions.UnexpectedException;

public class CreateUserUseCase implements ICreateUserUseCase {
    private final IUserGateway userGateway;
    private final IRoleGateway roleGateway;
    private final IPasswordEncoderGateway passwordEncoderGateway;
    private final IAuthenticationGateway authentication;

    public CreateUserUseCase(IUserGateway userGateway, IRoleGateway roleGateway, IPasswordEncoderGateway passwordEncoderGateway, IAuthenticationGateway authentication) {
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
