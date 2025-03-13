package com.example.simplerxapp.models

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.nio.file.attribute.FileTime
import java.time.LocalDateTime
import java.time.ZoneId

data class FileDetails(
    val name: String,
    val path: String,
    val size: Long,
    val extension: String,
    val createdOn: LocalDateTime,
    val modifiedOn: LocalDateTime,
    val lastAccessedOn: LocalDateTime
) {

    override fun toString(): String {
        return "" +
                "Path: $path\n" +
                "Filename: $name.$extension\n" +
                "Creation Time: ${createdOn}\n" +
                "Modified Time: ${modifiedOn}\n" +
                "Last Accessed Time: ${lastAccessedOn}\n" +
                "Size: ${bytesToKB(size)} KB\n"
    }

    fun bytesToKB(bytes: Long): Double {
        return bytes / 1024.0
    }

    fun bytesToMB(bytes: Long): Double {
        return bytes / (1024.0 * 1024.0)
    }

    companion object {
        fun ofFile(file: File): FileDetails {
            return if (file.isFile) {
                val path = Paths.get(file.path)
                val attr = Files.readAttributes(path, BasicFileAttributes::class.java)
                FileDetails(
                    name = file.name,
                    path = file.path,
                    extension = file.extension,
                    size = attr.size(),
                    createdOn = fileTimeToLocalDateTime(attr.creationTime()),
                    modifiedOn = fileTimeToLocalDateTime(attr.lastModifiedTime()),
                    lastAccessedOn = fileTimeToLocalDateTime(attr.lastModifiedTime())
                )
            } else {
                FileDetails(
                    name = file.name,
                    path = file.path,
                    size = 0,
                    extension = "",
                    createdOn = LocalDateTime.now(),
                    modifiedOn = LocalDateTime.now(),
                    lastAccessedOn = LocalDateTime.now()
                )
            }
        }

        fun fileTimeToLocalDateTime(fileTime: FileTime): LocalDateTime {
            return fileTime.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        }
    }
}
