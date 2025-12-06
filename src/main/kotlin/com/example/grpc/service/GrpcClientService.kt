package com.example.grpc.service

import com.example.grpc.proto.UserRequest
import com.example.grpc.proto.UserServiceGrpcKt
import com.example.grpc.proto.GoodsRequest
import com.example.grpc.proto.GoodsServiceGrpcKt
import com.example.grpc.proto.User
import com.example.grpc.proto.Goods
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service

@Service
class GrpcClientService {

    @GrpcClient("user-service")
    private lateinit var userStub: UserServiceGrpcKt.UserServiceCoroutineStub

    @GrpcClient("goods-service")
    private lateinit var goodsStub: GoodsServiceGrpcKt.GoodsServiceCoroutineStub

    suspend fun getUser(userId: Long): User {
        val request = UserRequest.newBuilder()
            .setUserId(userId)
            .build()

        val response = userStub.getUser(request)
        println("gRPC Client - User 조회 성공: name=${response.name}, age=${response.age}")
        return response
    }

    suspend fun getGoods(goodsName: String): Goods {
        val request = GoodsRequest.newBuilder()
            .setGoodsName(goodsName)
            .build()

        val response = goodsStub.getGoods(request)
        println("gRPC Client - Goods 조회 성공: name=${response.name}, price=${response.price}")
        return response
    }
}
