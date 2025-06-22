package me.lyric.asa

import java.io.File
import java.util.Properties

class ArmorStandProperties(
	private val plugin: ArmorStandPlugin,
	private val fileName: String
) {

	private val properties = Properties()
	private val file = File(plugin.dataFolder, fileName).also { if (!it.exists()) plugin.saveResource("messages.properties", false) }

	init {
		properties.load(file.inputStream())
	}

	fun get(value: String) : String? = properties.getProperty(value)
	fun getOrDefault(value: String, default: String) : String = properties.getProperty(value, default)


}