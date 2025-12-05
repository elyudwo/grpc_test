package com.example.grpc.service

import com.example.grpc.proto.User
import com.example.grpc.proto.UserRequest
import com.example.grpc.proto.UserServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.LoggerFactory

@GrpcService
class UserController : UserServiceGrpcKt.UserServiceCoroutineImplBase() {

    private val logger = LoggerFactory.getLogger(UserController::class.java)

    override suspend fun getUser(request: UserRequest): User {
        logger.info("Received GetUser request for user_id: ${request.userId}")

        // 실제로는 DB에서 조회하겠지만, 여기서는 더미 데이터 반환
        return User.newBuilder()
            .setName("User_${request.userId}")
            .setAge(25)
            .build()
    }
}
