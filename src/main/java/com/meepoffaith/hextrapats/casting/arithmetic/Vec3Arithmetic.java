package com.meepoffaith.hextrapats.casting.arithmetic;

import at.petrak.hexcasting.api.casting.arithmetic.Arithmetic;
import at.petrak.hexcasting.api.casting.arithmetic.engine.InvalidOperatorException;
import at.petrak.hexcasting.api.casting.arithmetic.operator.Operator;
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorBinary;
import at.petrak.hexcasting.api.casting.arithmetic.operator.OperatorUnary;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaMultiPredicate;
import at.petrak.hexcasting.api.casting.arithmetic.predicates.IotaPredicate;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.casting.math.HexPattern;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.vec3.OperatorApproachVec;
import com.meepoffaith.hextrapats.casting.arithmetic.operator.vec3.OperatorTurnVec;
import com.meepoffaith.hextrapats.util.MathUtils;
import com.meepoffaith.hextrapats.util.MultiPreds;
import com.meepoffaith.hextrapats.util.generics.Func1to1;
import com.meepoffaith.hextrapats.util.generics.Func2to1;
import net.minecraft.util.math.Vec3d;

import java.util.List;

import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.DOUBLE;
import static at.petrak.hexcasting.common.lib.hex.HexIotaTypes.VEC3;
import static com.meepoffaith.hextrapats.init.Arithmetics.*;

public class Vec3Arithmetic implements Arithmetic{
    private static final List<HexPattern> OPS = List.of(
        ROT_ABOUT_X,
        ROT_ABOUT_Y,
        ROT_ABOUT_Z,
        CONSTRUCT_ABOUT_X,
        CONSTRUCT_ABOUT_Y,
        CONSTRUCT_ABOUT_Z,
        NORMALIZE,
        INVERT,
        INCREMENT,
        DECREMENT,
        APPROACH,
        ANGLE_DIST,
        ANGLE_APPROACH
    );

    @Override
    public String arithName(){
        return "hextrapats_vec3_math";
    }

    @Override
    public Iterable<HexPattern> opTypes(){
        return OPS;
    }

    @Override
    public Operator getOperator(HexPattern pattern){
        //Vec3d already has rotate functions, but for some reason they take in a float for the angle.
        if(pattern.sigsEqual(ROT_ABOUT_X)){
            return makeVecNumToVec((v, x) -> { //Already clockwise
                double c = Math.cos(x);
                double s = Math.sin(x);
                return new Vec3d(v.x, c*v.y + s*v.z, c*v.z - s*v.y);
            });
        }else if(pattern.sigsEqual(ROT_ABOUT_Y)){
            return makeVecNumToVec((v, x) -> {
                x = -x; //Negate to make it a clockwise rotation
                double c = Math.cos(x);
                double s = Math.sin(x);
                return new Vec3d(c*v.x + s*v.z, v.y, c*v.z - s * v.x);
            });
        }else if(pattern.sigsEqual(ROT_ABOUT_Z)){
            return makeVecNumToVec((v, x) -> { //Already clockwise
                double c = Math.cos(x);
                double s = Math.sin(x);
                return new Vec3d(c*v.x + s*v.y, c*v.y - s*v.x, v.z);
            });
        }else if(pattern.sigsEqual(CONSTRUCT_ABOUT_X)){
            return makeNumToVec(a -> new Vec3d(0, Math.sin(a), Math.cos(a))); //+Z is 0 rad
        }else if(pattern.sigsEqual(CONSTRUCT_ABOUT_Y)){
            return makeNumToVec(a -> new Vec3d(-Math.sin(a), 0, Math.cos(a))); //+Z is 0 rad. Matches player yaw in F3.
        }else if(pattern.sigsEqual(CONSTRUCT_ABOUT_Z)){
            return makeNumToVec(a -> new Vec3d(-Math.cos(a), Math.sin(a), 0)); //-X is 0 rad
        }else if(pattern.sigsEqual(NORMALIZE)){
            return makeVecToVec(Vec3d::normalize);
        }else if(pattern.sigsEqual(INVERT)){
            return makeVecToVec(v -> v.multiply(-1));
        }else if(pattern.sigsEqual(INCREMENT)){
            return makeVecToVec(v -> {
                double len = v.length();
                return DoubleIota.tolerates(len, 0) ? v : v.multiply((len + 1) / len);
            });
        }else if(pattern.sigsEqual(DECREMENT)){
            return makeVecToVec(v -> {
                double len = v.length();
                return  DoubleIota.tolerates(len, 0) ? v : v.multiply((len - 1) / len);
            });
        }else if(pattern.sigsEqual(APPROACH)){
            return new OperatorApproachVec();
        }else if(pattern.sigsEqual(ANGLE_DIST)){
            return makeVecVectoNum(MathUtils::vecAngleDist);
        }else if(pattern.sigsEqual(ANGLE_APPROACH)){
            return new OperatorTurnVec();
        }else{
            throw new InvalidOperatorException(pattern + " is not a valid operator in Vec3 Arithmetic " + this);
        }
    }

    private OperatorBinary makeVecNumToVec(Func2to1<Vec3d, Double, Vec3d> op){
        return new OperatorBinary(MultiPreds.pair(VEC3, DOUBLE),
            (i, j) -> new Vec3Iota(op.apply(Operator.downcast(i, VEC3).getVec3(), Operator.downcast(j, DOUBLE).getDouble()))
        );
    }

    private OperatorUnary makeNumToVec(Func1to1<Double, Vec3d> op){
        return new OperatorUnary(MultiPreds.all(DOUBLE),
            i -> new Vec3Iota(op.apply(Operator.downcast(i, DOUBLE).getDouble()))
        );
    }

    private OperatorUnary makeVecToVec(Func1to1<Vec3d, Vec3d> op){
        return new OperatorUnary(MultiPreds.all(VEC3),
            i -> new Vec3Iota(op.apply(Operator.downcast(i, VEC3).getVec3()))
        );
    }

    private OperatorBinary makeVecVectoNum(Func2to1<Vec3d, Vec3d, Double> op){
        return new OperatorBinary(MultiPreds.all(VEC3),
            (i, j) -> new DoubleIota(op.apply(Operator.downcast(i, VEC3).getVec3(), Operator.downcast(j, VEC3).getVec3()))
        );
    }
}
