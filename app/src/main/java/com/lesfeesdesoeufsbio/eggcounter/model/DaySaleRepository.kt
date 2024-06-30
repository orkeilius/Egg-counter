package com.lesfeesdesoeufsbio.eggcounter.model

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.lesfeesdesoeufsbio.eggcounter.utils.TimeHelper
import kotlinx.datetime.LocalDate
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import java.io.File

class DaySaleReposytory private constructor(private val fileDir: File) {
    companion object {

        private var instance: DaySaleReposytory? = null
        private val json = Json { encodeDefaults = true }

        fun getInstance(context: Context): DaySaleReposytory {
            if (this.instance == null) {
                this.instance = DaySaleReposytory(context.getDir("sale",MODE_PRIVATE))
            }
            return this.instance!!
        }
    }


      fun save(data : DaySale){
        val fileName = generateFileName(data.date)
        val file = File(fileDir, fileName)

        if (file.exists() && file.isFile) {
            file.delete()
        }
        file.writeText(json.encodeToString(data))
    }

    fun getAllDay(): ArrayList<DaySale>{
        val files = fileDir.listFiles()?.filter { it.isFile }
        val out = arrayListOf<DaySale>()

        files?.forEach{ file ->
            out.add(json.decodeFromString<DaySale>(file.readText()))
        }
        out.sortByDescending { it.date.toEpochDays() }
        return out
    }

    fun getDay(date : LocalDate= TimeHelper.getCurrentLocalDateTime().date) : DaySale{


        val file = File(fileDir,generateFileName(date))

        if(!file.exists()){
            return DaySale(date)
        }
        return Json.decodeFromString<DaySale>(file.readText())
    }




    private fun generateFileName(date : LocalDate): String{
        return "DaySale-$date"
    }
}
