package com.example.grpc.controller

import com.example.grpc.proto.Goods
import com.example.grpc.proto.GoodsRequest
import com.example.grpc.proto.GoodsServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService
import org.slf4j.LoggerFactory

@GrpcService
class GoodsController : GoodsServiceGrpcKt.GoodsServiceCoroutineImplBase() {

    private val logger = LoggerFactory.getLogger(GoodsController::class.java)

    override suspend fun getGoods(request: GoodsRequest): Goods {
        logger.info("Received GetGoods request for goods_name: ${request.goodsName}")

        // 실제로는 DB에서 조회하겠지만, 여기서는 더미 데이터 반환
        return Goods.newBuilder()
            .setName(request.goodsName)
            .setPrice(10000)
            .build()
    }
}