import adapters.UniversalMarioGame
import engine.core.MarioLevelGenerator
import engine.core.MarioLevelModel
import engine.core.MarioTimer
import levelGenerators.sample.GapLevelGenerator
import levelGenerators.sample.VladavLevelGenerator

fun getLevel(): String {
    //val generator: MarioLevelGenerator = GapLevelGenerator()
    val generator: MarioLevelGenerator = VladavLevelGenerator()
    println("Generator created...")
    val level = generator.getGeneratedLevel(
        MarioLevelModel(150, 16),
        MarioTimer(5 * 60 * 60 * 1000)
    )
    println("GENERATED LEVEL:")
    println(level)

    return level


    /*
    // You can use this to get a level from saved file
    val loaded_level = PlayLevel.getLevel("levels/original/lvl-5.txt")
    println("LOADED LEVEL:")
    println(loaded_level)
    return loaded_level
    */
}

fun main() {
    val game = UniversalMarioGame()
    val level = getLevel()

    // OLD Baumgarten agent
//    val agent = agents.robinBaumgarten.Agent()
    // NEW Sosvald agent (better as validator)
    val agent = mff.agents.astar.Agent()
    // HUMAN control (S = jump, A = run/shoot)
//    val agent = agents.human.Agent()

    val results = game.runGame(
            agent,
            level,
            200,
            0,
            true
    )

    //PlayLevel.printResults(results)
}