package com.meepoffaith.hextrapats.casting.actions.lists;

import at.petrak.hexcasting.api.casting.eval.CastingEnvironment;
import at.petrak.hexcasting.api.casting.iota.DoubleIota;
import at.petrak.hexcasting.api.casting.iota.Iota;
import at.petrak.hexcasting.api.casting.iota.ListIota;
import com.meepoffaith.hextrapats.casting.bases.ConstMediaActionBase;
import com.meepoffaith.hextrapats.casting.bases.HexIotaStack;

import java.util.List;

public class OpDelAllElement extends ConstMediaActionBase{
    public int argc = 2;
    public long mediaCost = 0L;

    @Override
    public List<? extends Iota> execute(HexIotaStack stack, CastingEnvironment ctx){
        List<Iota> list = stack.getJUSTAList(0);
        Iota element = stack.get(1);

        int count = 0;
        int i = 0;
        while(i < list.size()){
            if(Iota.tolerates(list.get(i), element)){
                list.remove(i);
                count++;
            }else{
                i++;
            }
        }

        return List.of(new ListIota(list), new DoubleIota(count));
    }
}
