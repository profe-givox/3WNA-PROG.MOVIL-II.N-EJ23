package net.ivanvega.proyectodivisacontentprividera.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity (tableName = "Moneda")
public data class Moneda (

    @PrimaryKey(autoGenerate = true)
                    @ColumnInfo(name = "_ID")
                    var _ID: Int  ,
                   @ColumnInfo(name = "codeMoneda")
                   var codeMoneda: String ,
                   @ColumnInfo(name = "nombreMoneda")
                    var nombreMoneda: String,
                   @ColumnInfo(name = "pais")
                   var pais : String
                   ){
    @Ignore
    constructor() :
            this(0,"","","")
}

