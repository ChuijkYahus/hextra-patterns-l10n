package com.meepoffaith.hextrapats.casting.arithmetic.operator.vec3;

import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.casting.mishaps.MishapInvalidIota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.util.MathUtils;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.*;

public class OperatorTurnVec extends OperatorBase{
    public OperatorTurnVec(){
        super(3, IotaMultiPredicate.triple(IotaPredicate.ofType(VEC3), IotaPredicate.ofType(VEC3), IotaPredicate.ofType(DOUBLE)));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        Vec3d from = stack.getVec3(0);
        Vec3d to = stack.getVec3(1);
        double theta = stack.getDouble(2);
        double angDist = MathUtils.vecAngleDist(from, to);

        if(theta >= angDist){
            return asActionResult(new Vec3Iota(to.multiply(from.length() / to.length())));
        }else if(DoubleIota.tolerates(angDist, Math.PI)){
            //From and To are facing directly away from each other. In this case, no axis of rotation can be determined (cross product returns the 0 vector).
            throw new MishapInvalidIota(stack.get(1), 1, Text.of("vector not facing directly away from start direction"));
        }

        Vec3d fromN = from.normalize();
        Vec3d toN = to.normalize();

        Vec3d cross = fromN.crossProduct(toN).crossProduct(fromN).normalize();
        Vec3d next = fromN.multiply(Math.cos(theta)).add(cross.multiply(Math.sin(theta)));

        return asActionResult(new Vec3Iota(next.multiply(from.length())));
    }
}
