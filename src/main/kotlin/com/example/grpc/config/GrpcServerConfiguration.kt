package com.example.grpc.config

import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import io.grpc.protobuf.services.HealthStatusManager
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("!test")
class GrpcServerConfiguration {

    private val logger = LoggerFactory.getLogger(GrpcServerConfiguration::class.java)
    private lateinit var server: Server
    private val healthStatusManager = HealthStatusManager()

    @PostConstruct
    fun startGrpcServer() {
        val port = 9090

        server = ServerBuilder.forPort(port)
            .addService(healthStatusManager.healthService)
            .addService(ProtoReflectionService.newInstance())
            .build()
            .start()

        logger.info("gRPC Server started on port $port")
        logger.info("Health check service is available at port $port")

        // Set overall server health to SERVING
        healthStatusManager.setStatus("", io.grpc.health.v1.HealthCheckResponse.ServingStatus.SERVING)

        Runtime.getRuntime().addShutdownHook(Thread {
            logger.info("Shutting down gRPC server")
            stopGrpcServer()
        })

        // Keep the server alive
        Thread {
            try {
                server.awaitTermination()
            } catch (e: InterruptedException) {
                logger.error("gRPC server interrupted", e)
            }
        }.start()
    }

    @PreDestroy
    fun stopGrpcServer() {
        if (::server.isInitialized) {
            server.shutdown()
            logger.info("gRPC Server stopped")
        }
    }
}
