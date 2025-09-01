package com.acheron.userserver.api;

import com.acheron.userserver.UserRequest;
import com.acheron.userserver.UserServiceGrpcGrpc;
import com.acheron.userserver.dto.UserCreateDto;
import com.acheron.userserver.entity.User;
import com.acheron.userserver.service.UserService;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@GrpcService
@RequiredArgsConstructor
public class UserGrpcService extends UserServiceGrpcGrpc.UserServiceGrpcImplBase {
    private final UserService userService;
    @Override
    public void saveUser(UserRequest request, StreamObserver<Empty> responseObserver) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = User.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .email(request.getEmail())
                    .displayName(request.getDisplayName())
                    .image(request.getImage())
                    .isEmailVerified(request.getIsEmailVerified())
                    .role(User.Role.valueOf(request.getRole()))
                    .authMethod(User.AuthMethod.valueOf(request.getAuthMethod()))
                    .build();

            userService.save(new UserCreateDto(user.getUsername(), user.getPassword(), user.getEmail(), user.getDisplayName(), user.getImage(),user.getIsEmailVerified(),user.getRole().name(),user.getAuthMethod().name()));
            responseObserver.onNext(Empty.getDefaultInstance());
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}
