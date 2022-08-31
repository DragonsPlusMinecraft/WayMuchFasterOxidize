package plus.dragons.wmfo;

import net.minecraft.block.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class Utils {
    public static BlockState tryDegrade(Oxidizable oxidizable,BlockState state){
        var result = oxidizable.getDegradationResult(state);
        if(result.isEmpty()) return state;
        else return result.get();
    }

    public static boolean hardensOnAnySide(BlockView world, BlockPos pos) {
        boolean bl = false;
        BlockPos.Mutable mutable = pos.mutableCopy();
        Direction[] var4 = Direction.values();
        int var5 = var4.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Direction direction = var4[var6];
            BlockState blockState = world.getBlockState(mutable);
            if (direction != Direction.DOWN || hardensIn(blockState)) {
                mutable.set(pos, direction);
                blockState = world.getBlockState(mutable);
                if (hardensIn(blockState) && !blockState.isSideSolidFullSquare(world, pos, direction.getOpposite())) {
                    bl = true;
                    break;
                }
            }
        }
        return bl;
    }

    public static boolean hardensIn(BlockState state) {
        return state.getFluidState().isIn(FluidTags.WATER);
    }
}
