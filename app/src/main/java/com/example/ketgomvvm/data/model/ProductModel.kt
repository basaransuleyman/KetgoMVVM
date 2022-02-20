package com.example.ketgomvvm.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "products_table")
data class ProductModel(

    @ColumnInfo(name = "productName")
    val productName: String? = null,

    @ColumnInfo(name = "productPrice")
    val productPrice: Int? = null,

    @ColumnInfo(name = "productDescription")
    val productDescription: String? = null,

    @ColumnInfo(name = "productStatus")
    val productStatus: String? = null,

    @ColumnInfo(name = "sellingLocation")
    val sellingLocation: String? = null,

    @ColumnInfo(name = "productImage")
    val productImage: String? = null,

    @ColumnInfo(name = "createdDate")
    val createdDate: String? = null,

    @ColumnInfo(name = "upCount")
    var upCount: Int? = 0,

    @ColumnInfo(name = "isSold")
    var isSold: Boolean? = false,
    @PrimaryKey(autoGenerate = true) val id: Int? = 0

) : Parcelable