package com.example.grpc.controller

import com.example.grpc.service.GrpcClientService
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/grpc-client")
class GrpcClientTestController(
    private val grpcClientService: GrpcClientService
) {

    @GetMapping("/user/{userId}")
    fun testGetUser(@PathVariable userId: Long): String = runBlocking {
        val user = grpcClientService.getUser(userId)
        "gRPC Client 호출 성공 - User: name=${user.name}, age=${user.age}"
    }

    @GetMapping("/goods/{goodsName}")
    fun testGetGoods(@PathVariable goodsName: String): String = runBlocking {
        val goods = grpcClientService.getGoods(goodsName)
        "gRPC Client 호출 성공 - Goods: name=${goods.name}, price=${goods.price}"
    }
}
