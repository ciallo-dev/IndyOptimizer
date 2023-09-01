package com.alphaautoleak.indyOptimizer

import java.io.File
import kotlin.system.exitProcess

object Bootstrap
{

    private const val VERSION = "0.1"

    @JvmStatic
    fun main(args: Array<String>)
    {
        /* no way */
        println(" _____          _       _____       _   _           _              \n" +
                "|_   _|        | |     |  _  |     | | (_)         (_)             \n" +
                "  | | _ __   __| |_   _| | | |_ __ | |_ _ _ __ ___  _ _______ _ __ \n" +
                "  | || '_ \\ / _` | | | | | | | '_ \\| __| | '_ ` _ \\| |_  / _ \\ '__|\n" +
                " _| || | | | (_| | |_| \\ \\_/ / |_) | |_| | | | | | | |/ /  __/ |   \n" +
                " \\___/_| |_|\\__,_|\\__, |\\___/| .__/ \\__|_|_| |_| |_|_/___\\___|_|   \n" +
                "                   __/ |     | |                                   \n" +
                "                  |___/      |_|                                   ");

        if (args.size != 2)
        {
            println("Usage : java -jar xx.jar <input> <output>")
            exitProcess(-1)
        }

        val input = File(args[0])
        val output = File(args[1])

        /* process start*/
        val interpreter = ClazzOptimization(input,output)
        interpreter.loadJarFile()
        interpreter.optimizeClasses()
        /* process end */
        interpreter.exportJarFile()
    }




}


