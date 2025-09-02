package org.acheron.authserver.service;

import com.acheron.userserver.UserRequest;
import com.acheron.userserver.UserServiceGrpcGrpc;
import com.google.protobuf.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.acheron.authserver.dto.UserCreateDto;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.stereotype.Service;

@Service
public class UserGrpcClient {


     public void saveUser(UserCreateDto user, String accessToken) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 9091)
                .usePlaintext()
                .build();

        UserServiceGrpcGrpc.UserServiceGrpcBlockingStub stub =
                UserServiceGrpcGrpc.newBlockingStub(channel);

        // Створюємо metadata з Bearer token
        Metadata headers = new Metadata();
        Metadata.Key<String> authKey = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);
        headers.put(authKey, "Bearer " + accessToken);

        // Додаємо interceptor до stub
        stub = stub.withInterceptors(MetadataUtils.newAttachHeadersInterceptor(headers));

        try {
            UserRequest request = UserRequest.newBuilder()
                    .setUsername(user.getUsername())
                    .setPassword(user.getPassword())
                    .setEmail(user.getEmail())
                    .setDisplayName("asd")
                    .setImage("asd")
                    .setIsEmailVerified(user.isEmailVerified())
                    .setRole(user.getRole())
                    .setAuthMethod(user.getAuthMethod())
                    .build();

            Empty response = stub.saveUser(request);

            System.out.println("User saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.shutdown();
        }
    }
}
