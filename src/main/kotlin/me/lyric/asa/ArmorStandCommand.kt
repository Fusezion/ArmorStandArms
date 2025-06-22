@file:Suppress("UnstableApiUsage")

package me.lyric.asa

import com.mojang.brigadier.Command
import com.mojang.brigadier.RedirectModifier
import com.mojang.brigadier.SingleRedirectModifier
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import io.papermc.paper.command.brigadier.Commands
import me.lyric.asa.ArmorStandPlugin.Companion.instance
import net.kyori.adventure.text.minimessage.MiniMessage

class ArmorStandCommand {

	val config: ArmorStandConfig
		get() = ArmorStandPlugin.armorStandConfig

	val mm: MiniMessage
		get() = AdventureUtils.MINI_MESSAGE

	val properties: ArmorStandProperties
		get() = ArmorStandPlugin.armorStandProperties

	private val infoArgument = Commands.literal("info")
		.requires { (it.executor ?: it.sender).hasPermission("armorstand.command.info") }
		.executes {
			val source = it.source
			val executor = source.executor ?: source.sender
			executor.sendMessage(
				mm.deserialize(
					properties.getOrDefault(
						"command.version",
						"property command.version was not set"
					)
				)
			)
			return@executes Command.SINGLE_SUCCESS
		}
		.build()

	private val toggleArgument = Commands.literal("toggle")
		.requires { (it.executor ?: it.sender).hasPermission("armorstand.command.toggle") }
		.executes {
			val source = it.source
			val executor = source.executor ?: source.sender
			config.armsEnabled = !(config.armsEnabled)
			if (config.armsEnabled) {
				executor.sendMessage(
					mm.deserialize(
						properties.getOrDefault(
							"command.toggle.on",
							"Property command.toggle.on was not set"
						)
					)
				)
			} else {
				executor.sendMessage(
					mm.deserialize(
						properties.getOrDefault(
							"command.toggle.off",
							"Property command.toggle.off was not set"
						)
					)
				)
			}
			return@executes Command.SINGLE_SUCCESS
		}
		.build()

	private val reloadArgument = Commands.literal("reload")
		.requires { (it.executor ?: it.sender).hasPermission("armorstand.command.reload") }
		.executes {
			val source = it.source
			val executor = source.executor ?: source.sender
			ArmorStandPlugin.armorStandConfig = ArmorStandConfig(instance)
			ArmorStandPlugin.armorStandProperties = ArmorStandProperties(instance, "messages.properties")
			executor.sendMessage {
				mm.deserialize(
					properties.getOrDefault(
						"command.reloaded",
						"property command.reloaded was not set"
					)
				)
			}
			return@executes Command.SINGLE_SUCCESS
		}
		.build()

	fun register(): LiteralCommandNode<CommandSourceStack> {
		return Commands.literal("armorstand")
			.then(toggleArgument)
			.then(reloadArgument)
			.then(infoArgument)
			.build()
	}

}