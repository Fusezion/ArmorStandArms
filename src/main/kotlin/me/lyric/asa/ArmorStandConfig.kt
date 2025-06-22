package me.lyric.asa

import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.InputStreamReader

class ArmorStandConfig(
	private val plugin: ArmorStandPlugin
) {

	private val configFile = File(plugin.dataFolder, "config.yml").also { if (!it.exists()) plugin.saveResource("config.yml", false) }
	internal val config = YamlConfiguration.loadConfiguration(configFile)

	init {
		matchConfig()
	}

	private fun matchConfig() {
		try {
			var hasUpdated = false

			val inputStream = plugin.getResource("config.yml") ?: throw IllegalArgumentException("config.yml does not exist in the resources")
			val inputStreamReader = InputStreamReader(inputStream)
			val internalConfig = YamlConfiguration.loadConfiguration(inputStreamReader)

			for (key: String in config.getKeys(true)) {
				if (!(internalConfig.contains(key))) {
					config.set(key, null)
					hasUpdated = true
				}
			}

			for (key: String in internalConfig.getKeys(true)) {
				if (!(config.contains(key))) {
					config.set(key, internalConfig.get(key))
					hasUpdated = true
				}
			}

			if (hasUpdated) config.save(configFile)
		} catch (exception: Exception) {
			exception.printStackTrace()
		}
	}

	var debug: Boolean
		get() = config.getBoolean("debug-mode", false)
		set(value) { config.set("debug-mode", value) }
	var armsEnabled: Boolean
		get() = config.getBoolean("allow-armor-stand-arms", true)
		set(value) { config.set("allow-armor-stand-arms", value) }

	fun saveConfig() {
		config.save(configFile)
	}

}