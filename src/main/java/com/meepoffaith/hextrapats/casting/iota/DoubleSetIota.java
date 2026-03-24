package com.meepoffaith.hextrapats.casting.iota;

import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.utils.HexUtils;
import com.meepoffaith.hextrapats.util.MathUtils;
import it.unimi.dsi.fastutil.doubles.DoubleSet;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class DoubleSetIota extends Iota{
    public DoubleSetIota(@NotNull DoubleSet payload){
        super(TYPE, payload);
    }

    public DoubleSet getSet(){
        return (DoubleSet)payload;
    }

    public DoubleSet getMutableSet(){
        return new DoubleSet(getSet());
    }

    @Override
    public boolean isTruthy(){
        return !getSet().isEmpty();
    }

    @Override
    public int size(){
        return getSet().size();
    }

    public boolean contains(double key){
        return getSet().contains(key);
    }

    public boolean add(double key){
        return getSet().add(key);
    }

    public boolean remove(double key){
        return getSet().remove(key);
    }

    @Override
    protected boolean toleratesOther(Iota that){
        return that instanceof DoubleSetIota ds && ds.getSet().equals(getSet());
    }

    @Override
    public @NotNull NbtElement serialize(){
        NbtList list = new NbtList();
        for(double key : getSet()){
            list.add(NbtDouble.of(key));
        }
        return list;
    }

    @Override
    public @Nullable Iterable<Iota> subIotas(){
        List<Iota> list = new ArrayList<>();
        for(double key : getSet()){
            list.add(new DoubleIota(key));
        }
        return list;
    }

    public static IotaType<DoubleSetIota> TYPE = new IotaType<>(){
        @Override
        public DoubleSetIota deserialize(NbtElement tag, ServerWorld world) throws IllegalArgumentException{
            NbtList list = HexUtils.downcast(tag, NbtList.TYPE);
            DoubleSet set = new DoubleSet();
            for(int i = 0; i < list.size(); i++){
                set.add(list.getDouble(i));
            }
            return new DoubleSetIota(set);
        }

        @Override
        public Text display(NbtElement tag){
            MutableText comp = HexUtils.getDarkGreen("{nums: ");
            NbtList list = HexUtils.downcast(tag, NbtList.TYPE);
            for(int i = 0; i < list.size(); i++){
                String val = String.valueOf(list.getDouble(i));
                comp.append(HexUtils.getGreen(val));
                if(i + 1 < list.size()){
                    comp.append(HexUtils.getDarkGreen(" | "));
                }
            }
            comp.append(HexUtils.getDarkGreen("}"));

            return comp;
        }

        @Override
        public int color(){
            return 0x00AA00;
        }
    };

    /** HashSet of Doubles with automatic tolerance handling. */
    public static class DoubleSet extends HashSet<Double>{
        public DoubleSet(){
            super();
        }

        public DoubleSet(DoubleSet set){
            super(set);
        }

        @Override
        public boolean add(Double aDouble){
            return super.add(MathUtils.roundToTolerance(aDouble));
        }

        @Override
        public boolean contains(Object o){
            return o instanceof Double d && super.contains(MathUtils.roundToTolerance(d));
        }

        @Override
        public boolean remove(Object o){
            return o instanceof Double d && super.remove(MathUtils.roundToTolerance(d));
        }
    }
}
