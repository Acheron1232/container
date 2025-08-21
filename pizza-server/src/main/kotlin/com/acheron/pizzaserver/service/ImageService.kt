package com.acheron.pizzaserver.service

import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.storage.BlobId
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*

@Service
class ImageService {

    private val log = LoggerFactory.getLogger(ImageService::class.java)

    private val bucketName = "container-61d6e.firebasestorage.app" // заміни на свій bucket
    private val storage: Storage = StorageOptions.newBuilder()
        .setCredentials(
            ServiceAccountCredentials.fromStream(ClassPathResource("container-61d6e-firebase-adminsdk-fbsvc-304c7c9f8d.json").inputStream
            )
        )
        .build()
        .service

    fun uploadImage(folder: String, file: MultipartFile): String {
        val fileName = "$folder/${UUID.randomUUID()}-${file.originalFilename}"
        val blobId = BlobId.of(bucketName, fileName)
        val blobInfo = BlobInfo.newBuilder(blobId)
            .setContentType(file.contentType ?: "media")
            .build()

        storage.create(blobInfo, file.bytes)
        val encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8)
        val url = "https://firebasestorage.googleapis.com/v0/b/$bucketName/o/$encodedFileName?alt=media"
        log.info("Uploaded file to Firebase: $url")
        return url
    }

    fun deleteImage(url: String): Boolean {
        val key = url.split("/").drop(4).joinToString("/") // все після bucketName
        return try {
            val blobId = BlobId.of(bucketName, key)
            storage.delete(blobId)
            log.info("Deleted file: $url")
            true
        } catch (e: Exception) {
            log.error("Failed to delete file $url: ${e.message}")
            false
        }
    }
}