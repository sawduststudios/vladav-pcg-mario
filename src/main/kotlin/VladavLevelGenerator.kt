package levelGenerators.sample

import engine.core.MarioLevelGenerator
import engine.core.MarioLevelModel
import engine.core.MarioTimer
import kotlin.random.Random

class VladavLevelGenerator: MarioLevelGenerator {
    private val hardPiecesCount = 3
    private val midPiecesCount = 3

    class LevelPiece(val rows: Array<String>) {
        val height: Int = rows.size
        val width: Int

        init {
            require(rows.isNotEmpty()) { "LevelPiece must have at least one row." }
            val rowLength = rows[0].length
            require(rows.all { it.length == rowLength }) { "All rows in LevelPiece must have the same length." }
            this.width = rowLength
        }

        fun placeInModel(model: MarioLevelModel, startX: Int) {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    val block = rows[y][x]
                    if (block != MarioLevelModel.EMPTY) {
                        model.setBlock(startX + x, y, block)
                    }
                }
            }
        }
    }

    // -------------------------------
    // ------- PREMADE LEVELS --------
    // -------------------------------
    val easyPieces = arrayOf(
            LevelPiece(arrayOf(
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----o----o----o-",
                    "----S----S----S-",
                    "XXXXXXXXXXXXXXXX"
            )),
            LevelPiece(arrayOf(
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----t-----t-----",
                    "----T-----T-----",
                    "----------------",
                    "----------------",
                    "----------------",
                    "XXXXXXXXXXXXXXXX"
            )),
            LevelPiece(arrayOf(
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----S----S----S-",
                    "----------------",
                    "----@----@----@-",
                    "----------------",
                    "----------------",
                    "----------------",
                    "XXXXXXXXXXXXXXXX"
            ))
    )

    val midPieces = arrayOf(
            LevelPiece(arrayOf(
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "------%--------%",
                    "------%--------%",
                    "------%--------%",
                    "------S--------S",
                    "------S--------S",
                    "------S--------S",
                    "------g--------g",
                    "----------------",
                    "XXXXXXXXXXXXXXXX"
            )),
            LevelPiece(arrayOf(
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "-------t-------t",
                    "-------T-------T",
                    "----------------",
                    "----SSS----SSS--",
                    "----------------",
                    "----o-o----o-o--",
                    "----------------",
                    "----g--------g--",
                    "XXXXXXXXXXXXXXXX"
            )),
            LevelPiece(arrayOf(
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "-----#----------",
                    "-----#----------",
                    "-----#----------",
                    "-----@----------",
                    "----------------",
                    "-----C----------",
                    "----------------",
                    "----g-----------",
                    "----------------",
                    "XXXXXXXXXXXXXXXX"
            ))
    )

    val hardPieces = arrayOf(
            LevelPiece(arrayOf(
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "------%----%----",
                    "------%----%----",
                    "------%----%----",
                    "------%----%----",
                    "------S----S----",
                    "------S----S----",
                    "------S----S----",
                    "------g----g----",
                    "------k----k----",
                    "------g----g----",
                    "----------------",
                    "XXXXXXXXXXXXXXXX"
            )),
            LevelPiece(arrayOf(
                    "----------------",
                    "----------------",
                    "----------------",
                    "-------%--------",
                    "-------%--------",
                    "-------%--------",
                    "-------%--------",
                    "----------------",
                    "------t---------",
                    "------T---------",
                    "------@---------",
                    "------@---------",
                    "------@---------",
                    "------@---------",
                    "------g---------",
                    "XXXXXXXXXXXXXXXX"
            )),
            LevelPiece(arrayOf(
                    "----------------",
                    "----------------",
                    "----------------",
                    "----------------",
                    "-----###--------",
                    "-----#@#--------",
                    "-----#@#--------",
                    "-----#@#--------",
                    "-----#@#--------",
                    "-----#@#--------",
                    "-----#@#--------",
                    "-----#@#--------",
                    "-----#@#--------",
                    "-----#@#--------",
                    "-----#@#--------",
                    "XXXXXXXXXXXXXXXX"
            ))
    )


    fun generateFinisPiece(remainingWidth: Int): LevelPiece {
        require(remainingWidth >= 3) {"The end is too small!"}
        // Create strings
        val empty = "-".repeat(remainingWidth)
        val ground = "X".repeat(remainingWidth)
        val finish = "-".repeat(remainingWidth - 3) + "-F-"
        val block = "-".repeat(remainingWidth - 3) + "-#-"

        // make a level piece where two lowest rows are ground,
        // then there is block, then final, the rest is empty
        val rows = Array(16) { empty }
        rows[14] = ground
        rows[15] = ground
        rows[13] = block
        rows[12] = finish

        return LevelPiece(rows)
    }


    override fun getGeneratedLevel(model: MarioLevelModel, timer: MarioTimer): String {
        val rng = Random.Default
        model.clearMap()

        val pieceSequence = mutableListOf<LevelPiece>()
        var totalWidth = 0

        var spawnedHard = 0
        var spawnedMid = 0
        var spawnedEasy = 0

        // choose hardPiecesCount hard LevelPieces
        repeat(hardPiecesCount) {
            val piece = hardPieces.random(rng)
            pieceSequence.add(piece)
            spawnedHard++
            totalWidth += piece.width
            println("++HARD: " + piece.width)
            println("TOTAL: " + totalWidth)
        }
        // choose midPiecesCount medium LevelPieces
        repeat(midPiecesCount) {
            val piece = midPieces.random(rng)
            pieceSequence.add(piece)
            spawnedMid++
            totalWidth += piece.width
            println("++MID: " + piece.width)
            println("TOTAL: " + totalWidth)
        }
        // compute the total width of theese pieces and adjust width_left accordingly
        var widthLeft = model.width - totalWidth
        // while width_left < 10: remove random mid difficulty piece and update width_left
        while (widthLeft < 10 && pieceSequence.size > hardPiecesCount) {
            val midPiece = pieceSequence.removeAt(pieceSequence.size - 1)
            if (midPiece in midPieces) {
                widthLeft += midPiece.width
                totalWidth -= midPiece.width
                spawnedMid--
                println("--MID: " + midPiece.width)
                println("TOTAL: " + totalWidth)
            }
        }

        // report an error if widthLeft < 10
        require(widthLeft >= 10) { "Not enough space left to generate the level (widthLeft < 10)." }

        // keep on selecting random easy pieces while width_left > 22
        while (widthLeft > 12) {
            val piece = easyPieces.random(rng)
            pieceSequence.add(piece)
            spawnedEasy++
            widthLeft -= piece.width
            totalWidth += piece.width
            println("++EASY: " + piece.width)
            println("TOTAL: " + totalWidth)
        }

        while (widthLeft < 3 && pieceSequence.size > hardPiecesCount) {
            val easyPiece = pieceSequence.removeAt(pieceSequence.size - 1)
            if (easyPiece in easyPieces) {
                widthLeft += easyPiece.width
                totalWidth -= easyPiece.width
                spawnedEasy--
                println("--EASY: " + easyPiece.width)
                println("TOTAL: " + totalWidth)
            }
        }

        // set a random order of all the selected pieces combined
        pieceSequence.shuffle(rng)

        // build the pieces into the model
        var currentX = 0
        for (piece in pieceSequence) {
            piece.placeInModel(model, currentX)
            currentX += piece.width
        }

        // generate the end of the level
        val finish = generateFinisPiece(model.width - totalWidth)
        finish.placeInModel(model, currentX)
        currentX += finish.width
        totalWidth += finish.width
        println("++FINISH: " + finish.width)
        println("TOTAL: " + totalWidth)

        require(totalWidth == model.width) {"The totalWidth of the level " +  totalWidth.toString() + " is not the same as the model width"}

        // TODO: Redo the Mario start?
        model.setBlock(0, model.height /2, MarioLevelModel.MARIO_START)
        model.setBlock(0, 0, MarioLevelModel.GROUND)

        return model.map
    }

    override fun getGeneratorName(): String {
        return this.javaClass.simpleName;
    }
}