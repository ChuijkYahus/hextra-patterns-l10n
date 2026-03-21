package com.meepoffaith.hextrapats.casting.iota;

import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.utils.HexUtils;
import com.meepoffaith.hextrapats.util.MathUtils;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class DoubleSetIota extends Iota{
    public DoubleSetIota(@NotNull Set<Double> payload){
        super(TYPE, payload);
    }

    public Set<Double> getSet(){
        return (Set<Double>)payload;
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
        return getSet().contains(MathUtils.roundToTolerance(key));
    }

    public boolean add(double key){
        return getSet().add(MathUtils.roundToTolerance(key));
    }

    public boolean remove(double key){
        return getSet().remove(MathUtils.roundToTolerance(key));
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

    public static IotaType<DoubleSetIota> TYPE = new IotaType<DoubleSetIota>(){
        @Override
        public DoubleSetIota deserialize(NbtElement tag, ServerWorld world) throws IllegalArgumentException{
            NbtList list = HexUtils.downcast(tag, NbtList.TYPE);
            Set<Double> set = new HashSet<>();
            for(int i = 0; i < list.size(); i++){
                set.add(list.getDouble(i));
            }
            return new DoubleSetIota(set);
        }

        @Override
        public Text display(NbtElement tag){
            MutableText comp = Text.of("{nums: ").copy();
            NbtList list = HexUtils.downcast(tag, NbtList.TYPE);
            for(int i = 0; i < list.size(); i++){
                String val = String.valueOf(list.getDouble(i));
                comp.append(val);
                if(i + 1 < list.size()){
                    comp.append(" | ");
                }
            }
            comp.append("}");
            comp = HexUtils.getGreen(comp);

            return comp;
        }

        @Override
        public int color(){
            return DoubleIota.TYPE.color();
        }
    };
}
