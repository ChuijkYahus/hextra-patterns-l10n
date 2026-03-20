package com.meepoffaith.hextrapats.casting.iota;

import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.utils.HexUtils;
import com.meepoffaith.hextrapats.util.HextraUtils;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class SetIota extends Iota{
    public SetIota(@NotNull Set<String> payload){
        super(TYPE, payload);
    }

    public Set<String> getSet(){
        return (Set<String>)payload;
    }

    @Override
    public boolean isTruthy(){
        return !getSet().isEmpty();
    }

    public int size(){
        return getSet().size();
    }

    public boolean contains(Iota key){
        return getSet().contains(IotaType.serialize(key).toString());
    }

    public boolean add(Iota key){
        return getSet().add(IotaType.serialize(key).toString());
    }

    public boolean remove(Iota key){
        return getSet().remove(IotaType.serialize(key).toString());
    }

    @Override
    protected boolean toleratesOther(Iota that){
        return that instanceof SetIota s && s.getSet().equals(getSet());
    }

    @Override
    public @NotNull NbtElement serialize(){
        NbtList list = new NbtList();
        for(String s : getSet()){
            list.add(NbtString.of(s));
        }
        return list;
    }

    public static IotaType<SetIota> TYPE = new IotaType<>(){
        @Override
        public SetIota deserialize(NbtElement tag, ServerWorld world) throws IllegalArgumentException{
            NbtList list = HexUtils.downcast(tag, NbtList.TYPE);
            Set<String> set = new HashSet<>();
            for(int i = 0; i < list.size(); ++i){
                set.add(list.getString(i));
            }
            return new SetIota(set);
        }

        @Override
        public Text display(NbtElement tag){
            MutableText comp = HexUtils.getGold("{");
            NbtList list = HexUtils.downcast(tag, NbtList.TYPE);
            for(int i = 0; i < list.size(); i++){
                String key = HexUtils.downcast(list.get(i), NbtString.TYPE).asString();
                comp.append(HextraUtils.getDisplay(key));
                if(i + 1 < list.size()){
                    comp.append(HexUtils.getGold(" | "));
                }
            }
            comp.append(HexUtils.getGold("}"));
            return comp;
        }

        @Override
        public int color(){
            return 0xded228;
        }
    };
}
