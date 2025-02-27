package com.example.marchapplication.Data
import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface PhotoDao {
    // Thêm ảnh mới
    @Insert
    suspend fun insertPhoto(photo: Photo)
    // Lấy ảnh dựa trên NameProduct
    @Query("SELECT * FROM photos WHERE NameProduct = :folderName")
    suspend fun getImagesByCar(folderName: String): List<Photo>
    // Lấy thông tin chi tiết của hình ảnh
    @Query("SELECT * FROM photos WHERE FilePath = :imagePath LIMIT 1")
    suspend fun getPhotoByPath(imagePath: String): Photo?
    // Lấy ảnh đại diện dựa trên NameProduct
    @Query("SELECT Image FROM photos WHERE NameProduct = :carName LIMIT 1")
    fun getAvatarForCar(carName: String): Flow<Int?>
    // Đếm tổng số lượng ảnh của từng xe trong Database
    @Query("SELECT COUNT(*) FROM photos WHERE NameProduct = :carName")
    fun getImageCountForCar(carName: String): Flow<Int>
    // Truy vấn text
    @Query("SELECT TextEN FROM photos WHERE NameProduct = :folderName LIMIT 1")
    fun getTextENForCar(folderName: String): Flow<String>
    @Query("SELECT TextJP FROM photos WHERE NameProduct = :folderName LIMIT 1")
    fun getTextJPForCar(folderName: String): Flow<String>
    // Ghi thông tin vào hình ảnh
    @Update
    suspend fun updatePhoto(photo: Photo)
    @Delete
    suspend fun deletePhoto(photo: Photo) // Xóa ảnh
}