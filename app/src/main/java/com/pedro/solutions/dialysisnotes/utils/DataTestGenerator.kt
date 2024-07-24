package com.pedro.solutions.dialysisnotes.utils

import com.pedro.solutions.dialysisnotes.data.dialysis.Dialysis
import kotlin.random.Random

object DataTestGenerator {
    fun generateSmallDialysisList(): List<Dialysis> {
        return generateDialysisList(100)
    }

    fun generateMediumDialysisList(): List<Dialysis> {
        return generateDialysisList(1000)
    }

    fun generateLargeDialysisList(): List<Dialysis> {
        return generateDialysisList(10000)
    }

    private fun generateDialysisList(sizeOfList: Int): List<Dialysis> {
        val random = Random(System.currentTimeMillis()).nextLong()

        val mutableList = mutableListOf<Dialysis>()
        for (i in 1..sizeOfList) {
            val randomId = Random(System.currentTimeMillis()).nextInt()
            val randomInitialUf = Random(System.currentTimeMillis()).nextInt()
            val randomFinalUf = Random(System.currentTimeMillis()).nextInt()
            val randomNotes = "This is just an example $i".repeat(random.mod(100))


            mutableList.add(
                Dialysis(
                    System.currentTimeMillis(),
                    System.currentTimeMillis(),
                    randomInitialUf,
                    randomFinalUf,
                    randomNotes, null
                )
            )
        }
        return mutableList.toList()
    }
}