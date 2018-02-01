package com.lcg.test

import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * TODO

 * @author lei.chuguang Email:475825657@qq.com
 * *
 * @version 1.0
 * *
 * @since 2017/6/27 15:42
 */
object 交通卡 {
    @JvmStatic fun main(args: Array<String>) {
        card(16, 670, 670 + 120)
    }

    @JvmOverloads fun card(day: Int, aI: Int = 0, bI: Int = 0) {
        var a: Int
        var b: Int
        val c = 50
        val d = 60
        val z = Integer.MAX_VALUE shr 31 - day
        var min = Integer.MAX_VALUE
        var max = 0
        var cs = 0
        var mins = ""
        try {
            val writer = FileWriter(File("d:\\$day.txt"))
            for (i in 0..z - 1) {//总组合数
                val m = i
                a = aI
                b = bI
                var aCardString = ""
                for (j in 0..day - 1) {//天数、次幂
                    for (k in 0..1) {//来回两次计算
                        val a1: Float
                        val b1: Float
                        if (a < 100 || a > 400) {
                            a1 = 1f
                        } else if (a < 150) {
                            a1 = 0.8f
                        } else {
                            a1 = 0.5f
                        }
                        if (b < 100 || b > 400) {
                            b1 = 1f
                        } else if (b < 150) {
                            b1 = 0.8f
                        } else {
                            b1 = 0.5f
                        }
                        if (m shr j and 1 == 1) {
                            a += (c * a1).toInt()
                            b += (d * b1).toInt()
                            if (k == 0) {
                                aCardString += "公"
                                a += 0
                                b += 2//
                            }
                        } else {
                            a += (d * a1).toInt()
                            b += (c * b1).toInt()
                            if (k == 0) {
                                aCardString += "婆"
                                a += 2
                                b += 0
                            }
                        }
                    }
                }
                val v = a + b
                if (v < min) {
                    min = v
                    mins = aCardString
                }
                if (i == 0) {
                    cs = v
                }
                if (v > max)
                    max = v
                if (v == 5012) {
                    writer.write(aCardString + "\n")
                }
            }
            writer.flush()
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        println("cs=$cs max=$max min=$min A卡组合=$mins")
    }
}
