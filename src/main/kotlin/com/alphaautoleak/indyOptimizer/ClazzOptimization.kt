package com.alphaautoleak.indyOptimizer

import com.alphaautoleak.indyOptimizer.utils.IOUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.InvokeDynamicInsnNode
import java.io.File
import java.nio.file.Files
import java.util.*
import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream

class ClazzOptimization(private val input: File, val output: File)
{
    /* cache */
    private var classes = HashMap<String,ClassNode>()
    private val resources: MutableMap<String, ByteArray> = HashMap()

    fun loadJarFile()
    {
        val jarFile = JarFile(input)
        val iter: Enumeration<JarEntry> = jarFile.entries()

        /* get all of class in jar file */
        while (iter.hasMoreElements())
        {
            val entry: JarEntry = iter.nextElement()

            /* get all of file input stream */
            jarFile.getInputStream(entry).use{ `in`->

                /* support directory class */
                if (entry.name.endsWith(".class") or entry.name.endsWith(".class/"))
                {
                    /* inputStream to ClassNode */
                    val reader = ClassReader(`in`)
                    val classNode = ClassNode()

                    reader.accept(classNode,0)
                    classes.put(classNode.name,classNode)
                }
                else if (!entry.isDirectory)
                {
                    resources[entry.name] = IOUtils.toByteArray(`in`)
                }
                else
                {
                    // directory do nothing
                }

            }

        }

    }


    fun optimizeClasses()
    {
        for (classNode in classes.values)
        {
            for (methodNode in classNode.methods)
            {
                /* handle invokeDynamicNode only */
                for (insnNode in methodNode.instructions.toArray()) if (insnNode is InvokeDynamicInsnNode) IndyOptimization(insnNode).optimize()
            }

        }

    }


    fun exportJarFile()
    {
        try
        {
            val out = JarOutputStream(Files.newOutputStream(output.toPath()))

            println("Writing classes....")

            /* classes output */
            for (classNode in classes.values)
            {
                val writer = ClassWriter(ClassWriter.COMPUTE_MAXS or ClassWriter.COMPUTE_FRAMES)
                classNode.accept(writer)

                /* put class file */
                out.putNextEntry(JarEntry(classNode.name + ".class"))
                out.write(writer.toByteArray())
            }

            /* write out the resources */
            for (name in resources.keys)
            {
                out.putNextEntry(JarEntry(name))
                resources[name]?.let { out.write(it) }
            }

            out.closeEntry()
            println("saved jar file successfully !")
        }
        catch (e: Throwable)
        {
            e.printStackTrace()
        }
    }


    init
    {
        /* check file exist */
        if (!input.exists()) throw RuntimeException("file not found...")
    }

}