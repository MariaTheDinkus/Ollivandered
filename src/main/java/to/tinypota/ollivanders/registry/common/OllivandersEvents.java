package to.tinypota.ollivanders.registry.common;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import to.tinypota.ollivanders.common.block.FlooFireBlock;

public class OllivandersEvents {
	public static void init() {
		AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
			var state = world.getBlockState(pos);
			if (player.isCreative() && state.getBlock() instanceof FlooFireBlock) {
				if (!world.isClient()) {
					if (state.get(Properties.LIT)) {
						world.syncWorldEvent(null, 1009, pos, 0);
						world.setBlockState(pos, state.with(Properties.LIT, false).with(FlooFireBlock.ACTIVE, false));
						return ActionResult.FAIL;
					} else {
						return ActionResult.PASS;
					}
				}
			}
			return ActionResult.PASS;
		});
	}
}
