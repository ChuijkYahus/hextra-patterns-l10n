package com.meepoffaith.hextrapats.casting.iota;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.casting.iota.Vec3Iota;
import at.petrak.hexcasting.api.utils.HexUtils;
import com.meepoffaith.hextrapats.util.MathUtils;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtLongArray;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class VecSetIota extends Iota{
    public VecSetIota(Set<Vec3d> payload){
        super(TYPE, payload);
    }

    public Set<Vec3d> getSet(){
        return (Set<Vec3d>)payload;
    }

    @Override
    public boolean isTruthy(){
        return !getSet().isEmpty();
    }

    @Override
    public int size(){
        return getSet().size();
    }

    public boolean contains(Vec3d key){
        return getSet().contains(MathUtils.roundToTolerance(key));
    }

    public boolean add(Vec3d key){
        return getSet().add(MathUtils.roundToTolerance(key));
    }

    public boolean remove(Vec3d key){
        return getSet().remove(MathUtils.roundToTolerance(key));
    }

    @Override
    protected boolean toleratesOther(Iota that){
        return that instanceof VecSetIota vs && vs.getSet().equals(getSet());
    }

    @Override
    public @NotNull NbtElement serialize(){
        NbtList list = new NbtList();
        for(Vec3d key : getSet()){
            list.add(HexUtils.serializeToNBT(key));
        }
        return list;
    }

    public static IotaType<VecSetIota> TYPE = new IotaType<>(){
        @Override
        public VecSetIota deserialize(NbtElement tag, ServerWorld world) throws IllegalArgumentException{
            NbtList list = HexUtils.downcast(tag, NbtList.TYPE);
            Set<Vec3d> set = new HashSet<>();
            for(NbtElement nbtElement : list){ //Taken from Vec3Iota
                set.add(deserializeVec(nbtElement));
            }
            return new VecSetIota(set);
        }

        @Override
        public Text display(NbtElement tag){
            MutableText comp = HexUtils.getDarkRed("{vecs: ");
            NbtList list = HexUtils.downcast(tag, NbtList.TYPE);
            for(int i = 0; i < list.size(); i++){
                comp.append(Vec3Iota.display(deserializeVec(list.get(i))));
                if(i + 1 < list.size()){
                    comp.append(HexUtils.getDarkRed(" | "));
                }
            }
            comp.append(HexUtils.getDarkRed("}"));

            return comp;
        }

        @Override
        public int color(){
            return 0xAA0000;
        }
    };

    public static Vec3d deserializeVec(NbtElement tag){
        Vec3d vec;
        if(tag.getNbtType() == NbtLongArray.TYPE){
            var lat = HexUtils.downcast(tag, NbtLongArray.TYPE);
            vec = HexUtils.vecFromNBT(lat.getLongArray());
        }else
            vec = HexUtils.vecFromNBT(HexUtils.downcast(tag, NbtCompound.TYPE));
        return vec;
    }
}
