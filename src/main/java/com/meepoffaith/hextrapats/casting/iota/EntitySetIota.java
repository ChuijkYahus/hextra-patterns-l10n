package com.meepoffaith.hextrapats.casting.iota;

import at.petrak.hexcasting.api.casting.SpellList.LList;
import at.petrak.hexcasting.api.casting.iota.EntityIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.IotaType;
import at.petrak.hexcasting.api.utils.HexUtils;
import com.samsthenerd.inline.api.InlineAPI;
import com.samsthenerd.inline.api.data.EntityInlineData;
import com.samsthenerd.inline.api.data.PlayerHeadData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntitySetIota extends Iota{
    public EntitySetIota(Set<Entity> payload){
        super(TYPE, payload);
    }

    public Set<Entity> getSet(){
        return (Set<Entity>)payload;
    }

    public Set<Entity> getMutableSet(){
        return new HashSet<>(getSet());
    }

    @Override
    public boolean isTruthy(){
        return !getSet().isEmpty();
    }

    @Override
    public int size(){
        return getSet().size();
    }

    public boolean contains(Entity key){
        return getSet().contains(key);
    }

    public boolean add(Entity key){
        return getSet().add(key);
    }

    public boolean remove(Entity key){
        return getSet().remove(key);
    }

    @Override
    protected boolean toleratesOther(Iota that){
        return that instanceof EntitySetIota es && es.getSet().equals(getSet());
    }

    @Override
    public @NotNull NbtElement serialize(){
        NbtList list = new NbtList();
        for(Entity key : getSet()){
            var out = new NbtCompound();
            out.putUuid("uuid", key.getUuid());
            out.putString("name", Text.Serializer.toJson(getEntityNameWithInline(key, true)));
            list.add(out);
        }
        return list;
    }

    @Override
    public @Nullable Iterable<Iota> subIotas(){
        List<Iota> list = new ArrayList<>();
        for(Entity key : getSet()){
            list.add(new EntityIota(key));
        }
        return list;
    }

    public static IotaType<EntitySetIota> TYPE = new IotaType<>(){
        @Override
        public EntitySetIota deserialize(NbtElement tag, ServerWorld world) throws IllegalArgumentException{
            NbtList list = HexUtils.downcast(tag, NbtList.TYPE);
            Set<Entity> set = new HashSet<>();
            for(int i = 0; i < list.size(); i++){
                var ctag = list.getCompound(i);
                NbtElement uuidTag = ctag.get("uuid");
                if(uuidTag == null) continue;
                var uuid = NbtHelper.toUuid(uuidTag);
                var entity = world.getEntity(uuid);
                if(entity == null) continue;
                set.add(entity);
            }
            return new EntitySetIota(set);
        }

        @Override
        public Text display(NbtElement tag){
            MutableText comp = HexUtils.getDarkAqua("{entities: ");
            NbtList list = HexUtils.downcast(tag, NbtList.TYPE);
            for(int i = 0; i < list.size(); i++){
                var ctag = list.getCompound(i);
                var nameJson = ctag.getString("name");
                comp.append(HexUtils.getAqua(Text.Serializer.fromLenientJson(nameJson)));
                if(i + 1 < list.size()){
                    comp.append(HexUtils.getDarkAqua(" | "));
                }
            }
            comp.append(HexUtils.getDarkAqua("}"));

            return comp;
        }

        @Override
        public int color(){
            return 0x00AAAA;
        }
    };

    public static Text getEntityNameWithInline(Entity entity, boolean fearSerializer){
        MutableText baseName = entity.getName().copy();
        Text inlineEnt;
        if(entity instanceof PlayerEntity player){
            inlineEnt = new PlayerHeadData(player.getGameProfile()).asText(!fearSerializer);
            inlineEnt = inlineEnt.copyContentOnly().fillStyle(InlineAPI.INSTANCE.withSizeModifier(inlineEnt.getStyle(), 1.5));
        } else{
            if(fearSerializer){ // we don't want to have to serialize an entity just to display it
                inlineEnt = EntityInlineData.fromType(entity.getType()).asText(!fearSerializer);
            } else {
                inlineEnt = EntityInlineData.fromEntity(entity).asText(!fearSerializer);
            }
        }
        return baseName.append(Text.literal(": ")).append(inlineEnt);
    }
}
