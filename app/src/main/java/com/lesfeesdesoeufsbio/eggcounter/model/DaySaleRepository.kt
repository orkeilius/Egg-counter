package com.lesfeesdesoeufsbio.eggcounter.model

import android.content.Context
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.File

class DaySaleReposytory private constructor(val fileDir: File) {
    companion object {

        private var instance: DaySaleReposytory? = null

        fun getInstance(context: Context): DaySaleReposytory {
            if (this.instance == null) {
                this.instance = DaySaleReposytory(context.filesDir)
            }
            return this.instance!!
        }
    }


      fun save(data : DaySale){
        val fileName = "DaySale-" + data.date.toString()
        val file = File(fileDir, fileName)

        if (file.exists() && file.isFile) {
            file.delete()
        }
        file.writeText(Json.encodeToString(data))
    }
}
