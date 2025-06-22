package me.lyric.asa

import org.bukkit.entity.ArmorStand
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntitySpawnEvent

class ArmorStandListener : Listener {

	private val config: ArmorStandConfig
		get() = ArmorStandPlugin.armorStandConfig

	fun onArmorStandSpawn(event: EntitySpawnEvent) {
		if (event.entity is ArmorStand && config.armsEnabled)
			(event.entity as ArmorStand).setArms(true)
	}

}