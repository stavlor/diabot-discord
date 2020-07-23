package com.dongtronic.diabot.platforms.discord.utils

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Role
import org.slf4j.LoggerFactory
import java.util.*

object RoleUtils {
    val logger = LoggerFactory.getLogger(RoleUtils.javaClass)!!

    fun buildRewardsMap(potentialRewards: MutableList<String>?, guild: Guild): TreeMap<Role, MutableList<Role>> {
        val rewards: TreeMap<Role, MutableList<Role>> = TreeMap()

        if (potentialRewards == null || potentialRewards.size == 0) {
            // Stop processing if no role rewards are configured
            return rewards
        }

        for (rewardString in potentialRewards) {
            val data = rewardString.split(":")
            val required = guild.getRoleById(data[0])
            val reward = guild.getRoleById(data[1])

            if (required == null || reward == null) {
                continue
            }

            if (rewards.containsKey(required)) {
                var configuredRewards = rewards[required]
                if (configuredRewards == null) {
                    configuredRewards = ArrayList()
                }

                if (!configuredRewards.contains(reward)) {
                    configuredRewards.add(reward)
                }
            } else {
                rewards[required] = ArrayList()
                rewards[required]!!.add(reward)
            }
        }

        return rewards
    }
}
