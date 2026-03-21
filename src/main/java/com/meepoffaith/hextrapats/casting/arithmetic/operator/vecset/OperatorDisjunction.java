package com.meepoffaith.hextrapats.casting.arithmetic.operator.vecset;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.Iota;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;
import com.meepoffaith.hextrapats.casting.bases.OperatorBase;
import com.meepoffaith.hextrapats.casting.iota.Vec3SetIota;
import com.meepoffaith.hextrapats.util.MultiPreds;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static com.meepoffaith.hextrapats.init.IotaTypes.VEC_SET;

public class OperatorDisjunction extends OperatorBase{
    public OperatorDisjunction(){
        super(2, MultiPreds.all(VEC_SET));
    }

    @Override
    public @NotNull Iterable<Iota> operate(HexIotaStack stack, CastingEnvironment ctx){
        Set<Vec3d> set1 = stack.getVec3Set(0).getSet();
        Set<Vec3d> set2 = stack.getVec3Set(1).getSet();
        HashSet<Vec3d> disjunction = new HashSet<>();
        for(Vec3d key : set1){
            if(!set2.contains(key)){
                disjunction.add(key);
            }else{
                set2.remove(key);
            }
        }
        disjunction.addAll(set2);
        return asActionResult(new Vec3SetIota(disjunction));
    }
}
