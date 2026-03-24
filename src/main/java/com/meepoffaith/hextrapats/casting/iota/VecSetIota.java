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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VecSetIota extends Iota{
    public VecSetIota(VecSet payload){
        super(TYPE, payload);
    }

    public VecSet getSet(){
        return (VecSet)payload;
    }

    public VecSet getMutableSet(){
        return new VecSet(getSet());
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
        return getSet().contains(key);
    }

    public boolean add(Vec3d key){
        return getSet().add(key);
    }

    public boolean remove(Vec3d key){
        return getSet().remove(key);
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

    /* Likely unnecessary, handled by size()
    @Override
    public @Nullable Iterable<Iota> subIotas(){
        List<Iota> list = new ArrayList<>();
        for(Vec3d key : getSet()){
            list.add(new Vec3Iota(key));
        }
        return list;
    }
     */

    public static IotaType<VecSetIota> TYPE = new IotaType<>(){
        @Override
        public VecSetIota deserialize(NbtElement tag, ServerWorld world) throws IllegalArgumentException{
            NbtList list = HexUtils.downcast(tag, NbtList.TYPE);
            VecSet set = new VecSet();
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

    /** HashSet of Vec3ds with automatic tolerance handling. */
    public static class VecSet extends HashSet<Vec3d>{
        public VecSet(){
            super();
        }

        public VecSet(VecSet set){
            super(set);
        }

        @Override
        public boolean add(Vec3d vec3d){
            return super.add(MathUtils.roundToTolerance(vec3d));
        }

        @Override
        public boolean contains(Object o){
            return o instanceof Vec3d v && super.contains(MathUtils.roundToTolerance(v));
        }

        @Override
        public boolean remove(Object o){
            return o instanceof Vec3d v && super.remove(MathUtils.roundToTolerance(v));
        }
    }
}
